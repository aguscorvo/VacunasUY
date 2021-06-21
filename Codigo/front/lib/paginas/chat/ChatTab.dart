import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/chat/ChatBox.dart';
import 'package:vacunas_uy/paginas/chat/UserList.dart';

class ChatTab extends StatefulWidget {
  @override
  _ChatTabState createState() => _ChatTabState();
}

class _ChatTabState extends State<ChatTab> {
  UserList? userList;
  ChatBox chatBox = ChatBox(
    correoUsuario: "",
    nombre: "",
  );

  _setChatBox(ChatBox val) {
    setState(() {
      chatBox = val;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (userList == null) {
      userList = UserList(
        onUserSelected: (ChatBox val) => _setChatBox(val),
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
