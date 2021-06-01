import 'package:flutter/material.dart';

class BienvenidaTab extends StatefulWidget {
  @override
  _BienvenidaTabState createState() => _BienvenidaTabState();
}

class _BienvenidaTabState extends State<BienvenidaTab> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        //mainAxisSize: MainAxisSize.max,
        //crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: new Container(
              padding: const EdgeInsets.fromLTRB(50.0, 0, 50.0, 50.0),
              alignment: Alignment.center,
              child: Text(
                "Bienvenido a Vacunas UY",
                style: TextStyle(fontSize: 25),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
