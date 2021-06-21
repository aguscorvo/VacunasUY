import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:vacunas_uy/tools/FirebaseApi.dart';

class ChatMessagesBox extends StatefulWidget {
  final String chatRoomId;
  final String myUserName;

  const ChatMessagesBox({Key? key, required this.chatRoomId, required this.myUserName}) : super(key: key);
  @override
  State<StatefulWidget> createState() => _ChatMessagesBoxState();
}

class _ChatMessagesBoxState extends State<ChatMessagesBox> {
  Stream? messageStream;

  getAndSetMessages() async {
    messageStream = await FirebaseApi().getMensajesSala(widget.chatRoomId);
    setState(() {});
  }

  Widget chatMessageTile(String message, bool sentByMe) {
    BorderRadius border;
    if (sentByMe) {
      border = BorderRadius.only(
        bottomRight: Radius.circular(0),
        bottomLeft: Radius.circular(24),
        topRight: Radius.circular(24),
        topLeft: Radius.circular(24),
      );
    } else {
      border = BorderRadius.only(
        bottomRight: Radius.circular(24),
        bottomLeft: Radius.circular(0),
        topRight: Radius.circular(24),
        topLeft: Radius.circular(24),
      );
    }
    return Row(
      mainAxisAlignment: sentByMe ? MainAxisAlignment.end : MainAxisAlignment.start,
      children: [
        Container(
          decoration: BoxDecoration(
            borderRadius: border,
            color: Colors.blue,
          ),
          margin: EdgeInsets.symmetric(horizontal: 16, vertical: 4),
          padding: EdgeInsets.all(10),
          child: Text(
            message,
            style: TextStyle(color: Colors.white),
          ),
        )
      ],
    );
  }

  @override
  void initState() {
    getAndSetMessages();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
      stream: messageStream,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          QuerySnapshot data = snapshot.data! as QuerySnapshot;
          return ListView.builder(
            itemCount: data.docs.length,
            reverse: true,
            itemBuilder: (context, index) {
              DocumentSnapshot ds = data.docs[index];
              return chatMessageTile(ds["message"], widget.myUserName == ds["sentBy"]);
            },
          );
        } else {
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }
}
