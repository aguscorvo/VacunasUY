import 'package:flutter/material.dart';

class AgendaVacunador extends StatefulWidget {
  @override
  _AgendaVacunadorState createState() => _AgendaVacunadorState();
}

class _AgendaVacunadorState extends State<AgendaVacunador> {
  Widget agendaToLoad;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Text("Agenda Vacunador"),
    );
  }
}
