import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonEnfermedad.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonPlan.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonVacuna.dart';

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

class MonitorVacunacionTab extends StatelessWidget {
  TabBar get _tabsVacunador => TabBar(
        tabs: [
          Tab(text: "Monitor Enfermedades"),
          Tab(text: "Monitor Vacunas"),
          Tab(text: "Monitor Planes"),
        ],
      );

  @override
  Widget build(BuildContext context) {
    Widget monitores;

    monitores = DefaultTabController(
        length: 3,
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
                MonEnfermedad(),
                MonVacuna(),
                MonPlan(),
              ],
            )));

    return Scaffold(
      body: monitores,
    );
  }
}
