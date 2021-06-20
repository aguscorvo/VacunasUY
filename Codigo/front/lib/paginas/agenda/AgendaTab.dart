import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaCiudadano.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaPublico.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaVacunador.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';

final MaterialColor colorCustom = MaterialColor(0xFF174378, {
  050: Color.fromRGBO(0, 0, 250, .1),
  100: Color.fromRGBO(0, 0, 250, .2),
  200: Color.fromRGBO(0, 0, 250, .3),
  300: Color.fromRGBO(0, 0, 250, .4),
  400: Color.fromRGBO(0, 0, 250, .5),
  500: Color.fromRGBO(0, 0, 250, .6),
  600: Color.fromRGBO(0, 0, 250, .7),
  700: Color.fromRGBO(0, 0, 250, .8),
  800: Color.fromRGBO(0, 0, 250, .9),
  900: Color.fromRGBO(0, 0, 250, 1),
});

class AgendaTab extends StatefulWidget {
  @override
  _AgendaTabState createState() => _AgendaTabState();
}

class _AgendaTabState extends State<AgendaTab> {
  late Widget agendaToLoad;

  TabBar get _tabsVacunador => TabBar(
        tabs: [
          Tab(text: "Mi Agenda Como Ciudadano"),
          Tab(text: "Mi Agenda Como Vacunador"),
        ],
      );

  @override
  Widget build(BuildContext context) {
    if (isUserLogedOn()) {
      if (isUserVacunador()) {
        agendaToLoad = DefaultTabController(
            length: 2,
            child: Scaffold(
                appBar: PreferredSize(
                  preferredSize: _tabsVacunador.preferredSize,
                  child: ColoredBox(
                    color: colorCustom,
                    child: _tabsVacunador,
                  ),
                ),
                body: TabBarView(
                  children: [
                    AgendaCiudadano(),
                    AgendaVacunador(),
                  ],
                )));
      } else if (isUserCiudadano()) {
        agendaToLoad = AgendaCiudadano();
      }
    } else {
      agendaToLoad = AgendaPublico();
    }
    return Scaffold(
      body: agendaToLoad,
    );
  }
}
