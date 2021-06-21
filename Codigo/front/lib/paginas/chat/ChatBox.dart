import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/chat/ChatMessageBox.dart';
import 'package:vacunas_uy/tools/FirebaseApi.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'dart:math';

// ignore: must_be_immutable
class ChatBox extends StatelessWidget {
  final String correoUsuario, nombre;
  ChatBox({required this.correoUsuario, required this.nombre});

  String chatRoomId = "";
  String messageId = "";
  Stream? messageStream;
  TextEditingController toChat = TextEditingController();
  ChatMessagesBox? chatBox;
  String _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));
  String getChatRoomIdByCorreos(String a, String b) {
    List<String> users = [a, b];
    users.sort();
    return users[0] + "_" + users[1];
  }

  addMessage(bool sendClicked) async {
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

      FirebaseApi().sendMessage(chatRoomId, messageId, messageMap).then((value) async {
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
  }

  toDoOnLaunch() async {
    String miCorreo = storedUserCredentials!.userData!.correo;
    chatRoomId = getChatRoomIdByCorreos(miCorreo, correoUsuario);

    Map<String, dynamic> chatRoomInfoMap = {
      "usuarios": [miCorreo, correoUsuario]
    };
    FirebaseApi().createChatRoom(chatRoomId, chatRoomInfoMap);

    chatBox = ChatMessagesBox(
      chatRoomId: chatRoomId,
      myUserName: miCorreo,
    );
  }

  @override
  Widget build(BuildContext context) {
    if (correoUsuario == "") {
      return Container(
        child: Center(
          child: Text(
            "Seleccione un chat.",
            style: TextStyle(fontSize: 25),
          ),
        ),
      );
    } else {
      toDoOnLaunch();
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
                          hintText: "Escribe un Mensaje",
                          hintStyle: TextStyle(
                            color: Colors.white.withOpacity(0.6),
                          ),
                        ),
                      ),
                    ),
                  ),
                  GestureDetector(
                    onTap: () {
                      addMessage(true);
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
}
