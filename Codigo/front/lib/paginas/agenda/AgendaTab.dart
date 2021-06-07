import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaCiudadano.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaPublico.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaVacunador.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';

class AgendaTab extends StatefulWidget {
  @override
  _AgendaTabState createState() => _AgendaTabState();
}

class _AgendaTabState extends State<AgendaTab> {
  Widget agendaToLoad;

  @override
  Widget build(BuildContext context) {
    if (isUserLogedOn()) {
      if (isUserCiudadano()) {
        agendaToLoad = AgendaCiudadano();
      } else if (isUserVacunador()) {
        agendaToLoad = AgendaVacunador();
      }
    } else {
      agendaToLoad = AgendaPublico();
    }
    return Scaffold(
      body: agendaToLoad,
    );
  }
}
