import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/chat/ChatBox.dart';
import 'package:vacunas_uy/paginas/chat/UserList.dart';

class ChatTab extends StatefulWidget {
  @override
  _ChatTabState createState() => _ChatTabState();
}

class _ChatTabState extends State<ChatTab> {
  late String correoUsuario = "";
  late String nombre = "";

  _setChatBox(ChatBox val) {
    this.correoUsuario = val.correoUsuario!;
    this.nombre = val.nombre!;
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    UserList? userList;
    ChatBox? chatBox;
    if (userList == null) {
      userList = UserList(
        onUserSelected: (ChatBox val) => _setChatBox(val),
      );
    }
    if (chatBox == null) {
      chatBox = ChatBox(
        correoUsuario: correoUsuario,
        nombre: nombre,
      );
    }
    return Container(
      child: Row(
        children: [
          Material(
            elevation: 10,
            child: Container(
              width: 400,
              child: userList,
            ),
          ),
          Container(
            width: MediaQuery.of(context).size.width - 400,
            child: chatBox,
          ),
        ],
      ),
    );
  }
}
