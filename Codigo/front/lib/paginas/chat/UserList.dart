import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/tools/FirebaseApi.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';

import 'ChatBox.dart';

class UserList extends StatefulWidget {
  final Function(ChatBox) onUserSelected;

  const UserList({Key? key, required this.onUserSelected}) : super(key: key);
  @override
  _UserListState createState() => _UserListState(onUserSelected);
}

class _UserListState extends State<UserList> {
  final Function(ChatBox) onUserSelected;
  bool buscando = false;
  Stream? usersStream, chatRoomsStream;

  TextEditingController buscandoController = TextEditingController();

  _UserListState(this.onUserSelected);

  onSearchBtnClick() async {
    buscando = true;
    usersStream = await FirebaseApi().getUserByCorreo(buscandoController.text);
    setState(() {});
  }

  getchats() async {
    chatRoomsStream = await FirebaseApi().getChatRooms(storedUserCredentials!.userData!.correo);
    setState(() {});
  }

  Widget searchUsersList() {
    try {
      return StreamBuilder(
        stream: usersStream,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            QuerySnapshot data = snapshot.data! as QuerySnapshot;
            return ListView.builder(
              itemCount: data.docs.length,
              shrinkWrap: true,
              itemBuilder: (context, index) {
                DocumentSnapshot ds = data.docs[index];
                return GestureDetector(
                  onTap: () {
                    onUserSelected(
                      ChatBox(
                        correoUsuario: ds["email"],
                        nombre: ds["nombre"],
                      ),
                    );
                  },
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(24),
                      border: Border.all(
                        color: Colors.grey,
                        width: 2,
                        style: BorderStyle.solid,
                      ),
                    ),
                    margin: EdgeInsets.symmetric(horizontal: 25),
                    padding: EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(ds["nombre"]),
                        Text(ds["email"]),
                      ],
                    ),
                  ),
                );
              },
            );
          } else {
            return Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      );
    } catch (err) {
      print(err);
      return Center(
        child: Text("Hubo un error al carchar los usuarios"),
      );
    }
  }

  Widget chatRoomListTile(String correo, String lastMessage) {
    return ChatRoomListTile(
      correo: correo,
      lastMessage: lastMessage,
      onUserSelected: onUserSelected,
    );
  }

  Widget lastChats() {
    return StreamBuilder(
      stream: chatRoomsStream,
      builder: (context, snapshot) {
        if (!snapshot.hasData) {
          return Center(child: CircularProgressIndicator());
        } else {
          QuerySnapshot data = snapshot.data! as QuerySnapshot;
          return ListView.builder(
            itemCount: data.docs.length,
            //reverse: true,
            shrinkWrap: true,
            itemBuilder: (context, index) {
              DocumentSnapshot ds = data.docs[index];
              return chatRoomListTile(ds.id.replaceFirst(storedUserCredentials!.userData!.correo, "").replaceAll("_", ""), ds["lastMessage"]);
              //return Text(ds.id.replaceFirst(storedUserCredentials!.userData!.correo, "").replaceAll("_", ""));
            },
          );
        }
      },
    );
  }

  @override
  void initState() {
    getchats();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    //buscandoController..text = "jo.cs1998@hotmail.com";
    return Container(
      child: Column(
        children: [
          Row(
            children: [
              buscando
                  ? Padding(
                      padding: EdgeInsets.only(right: 12, left: 16),
                      child: GestureDetector(
                        onTap: () {
                          setState(() {
                            buscando = false;
                            buscandoController.text = "";
                          });
                        },
                        child: Icon(Icons.arrow_back),
                      ),
                    )
                  : Container(),
              Expanded(
                child: Container(
                  padding: EdgeInsets.symmetric(horizontal: 12),
                  margin: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(24),
                    border: Border.all(
                      color: Colors.grey,
                      width: 2,
                      style: BorderStyle.solid,
                    ),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: buscandoController,
                          decoration: InputDecoration(border: InputBorder.none, hintText: "Correo"),
                        ),
                      ),
                      GestureDetector(
                        onTap: () {
                          if (buscandoController.text != "") {
                            onSearchBtnClick();
                          }
                        },
                        child: Icon(Icons.search),
                      ),
                    ],
                  ),
                ),
              )
            ],
          ),
          buscando ? searchUsersList() : lastChats(),
        ],
      ),
    );
  }
}

class ChatRoomListTile extends StatefulWidget {
  final String correo, lastMessage;
  final Function(ChatBox) onUserSelected;

  const ChatRoomListTile({Key? key, required this.correo, required this.lastMessage, required this.onUserSelected}) : super(key: key);

  @override
  _ChatRoomListTileState createState() => _ChatRoomListTileState();
}

class _ChatRoomListTileState extends State<ChatRoomListTile> {
  late String name = "";

  getThisUserInfo() async {
    QuerySnapshot qs = await FirebaseApi().getUserInfo(widget.correo);
    name = qs.docs[0]["nombre"];
    setState(() {});
  }

  void initState() {
    getThisUserInfo();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        widget.onUserSelected(
          ChatBox(
            correoUsuario: widget.correo,
            nombre: name,
          ),
        );
      },
      child: Container(
        decoration: BoxDecoration(
          color: Colors.blueAccent,
          borderRadius: BorderRadius.circular(20),
        ),
        margin: EdgeInsets.symmetric(horizontal: 30, vertical: 5),
        padding: EdgeInsets.symmetric(horizontal: 12, vertical: 10),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              name,
              style: TextStyle(
                fontSize: 15,
                color: Colors.white,
              ),
            ),
            SizedBox(
              height: 3,
            ),
            Text(
              widget.lastMessage,
              style: TextStyle(
                fontSize: 10,
                color: Colors.grey.shade900,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
