import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/chat/ChatMessageBox.dart';
import 'package:vacunas_uy/tools/FirebaseApi.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'dart:math';

class ChatBox extends StatefulWidget {
  final String? correoUsuario, nombre;
  ChatBox({this.correoUsuario, this.nombre});

  static _ChatBoxState? state;

  @override
  _ChatBoxState createState() => state = new _ChatBoxState();
}

class _ChatBoxState extends State<ChatBox> {
  TextEditingController toChat = TextEditingController();

  ChatMessagesBox? chatBox;
  Stream? messageStream;

  @override
  Widget build(BuildContext context) {
    final String correoUsuario = widget.correoUsuario!;
    final String nombre = widget.nombre!;
    //String messageId = "";
    String chatRoomId = "";

    if (correoUsuario == "") {
      return Container(
        child: Center(
          child: Text(
            "Seleccione un chat",
            style: TextStyle(fontSize: 25),
          ),
        ),
      );
    } else {
      return FutureBuilder(
        future: toDoOnLaunch(correoUsuario),
        builder: (context, snapshot) {
          if (snapshot.connectionState != ConnectionState.done) {
            return Center(child: CircularProgressIndicator());
          } else {
            if (snapshot.data == null) {
              return Center(child: CircularProgressIndicator());
            } else {
              chatRoomId = snapshot.data as String;
              return Container(
                child: Column(
                  children: [
                    Container(
                      padding: EdgeInsets.symmetric(horizontal: 25),
                      width: MediaQuery.of(context).size.width - 400,
                      height: 50,
                      color: Colors.blueAccent,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text(
                            nombre,
                            style: TextStyle(fontSize: 20),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      child: Container(
                        child: chatBox,
                      ),
                    ),
                    Container(
                      padding: EdgeInsets.symmetric(horizontal: 12),
                      width: MediaQuery.of(context).size.width - 400,
                      height: 50,
                      color: Colors.black.withOpacity(0.8),
                      child: Row(
                        children: [
                          Expanded(
                            child: Container(
                              padding: EdgeInsets.symmetric(horizontal: 12),
                              child: TextField(
                                controller: toChat,
                                style: TextStyle(
                                  color: Colors.white,
                                ),
                                onChanged: (value) {
                                  //addMessage(false);
                                },
                                decoration: InputDecoration(
                                  border: InputBorder.none,
                                  hintText: "Escribe un mensaje",
                                  hintStyle: TextStyle(
                                    color: Colors.white.withOpacity(0.6),
                                  ),
                                ),
                              ),
                            ),
                          ),
                          GestureDetector(
                            onTap: () {
                              addMessage(true, "", chatRoomId);
                            },
                            child: Icon(
                              Icons.send,
                              color: Colors.white,
                            ),
                          )
                        ],
                      ),
                    ),
                  ],
                ),
              );
            }
          }
        },
      );
    }
  }

  String _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));
  String getChatRoomIdByCorreos(String a, String b) {
    List<String> users = [a, b];
    users.sort();
    return users[0] + "_" + users[1];
  }

  Future<String> addMessage(bool sendClicked, String lastMId, String chatRoomId) async {
    String messageId = lastMId;

    if (toChat.text != "") {
      String message = toChat.text;

      var messageTimeStamp = DateTime.now();

      Map<String, dynamic> messageMap = {
        "message": message,
        "sentBy": storedUserCredentials!.userData!.correo,
        "timeStamp": messageTimeStamp,
      };

      if (messageId == "") {
        messageId = getRandomString(32);
      }

      await FirebaseApi().sendMessage(chatRoomId, messageId, messageMap).then((value) async {
        Map<String, dynamic> lastMessageInfoMap = {
          "lastMessage": message,
          "timeStamp": messageTimeStamp,
          "sentBy": storedUserCredentials!.userData!.correo,
        };
        await FirebaseApi().updateLastMessage(chatRoomId, lastMessageInfoMap);
        if (sendClicked) {
          toChat.text = "";
          messageId = "";
        }
      });
    }
    return messageId;
  }

  Future<String> toDoOnLaunch(String correoUsuario) async {
    String miCorreo = storedUserCredentials!.userData!.correo;
    String chatRoomId = getChatRoomIdByCorreos(miCorreo, correoUsuario);

    Map<String, dynamic> chatRoomInfoMap = {
      "usuarios": [miCorreo, correoUsuario]
    };
    FirebaseApi().createChatRoom(chatRoomId, chatRoomInfoMap);

    chatBox = ChatMessagesBox(
      chatRoomId: chatRoomId,
      myUserName: miCorreo,
    );

    return chatRoomId;
  }
}
