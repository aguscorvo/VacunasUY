import 'package:flutter/material.dart';

class ChatTab extends StatefulWidget {
  @override
  _ChatTabState createState() => _ChatTabState();
}

class _ChatTabState extends State<ChatTab> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Row(
        children: [
          //ChatHeaderWidget(users: users),
          //CahtBodyWidget(users: users),
        ],
      ),
    );
  }
}
