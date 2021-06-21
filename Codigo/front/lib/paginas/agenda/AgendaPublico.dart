import 'package:flutter/material.dart';
import 'package:vacunas_uy/assets/PlanVacCard.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class AgendaPublico extends StatefulWidget {
  @override
  _AgendaPublicoState createState() => _AgendaPublicoState();
}

class _AgendaPublicoState extends State<AgendaPublico> {
  BackendConnection client = BackendConnection();
  late Widget agendaToLoad;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: const EdgeInsets.fromLTRB(50.0, 15, 50.0, 50.0),
        alignment: Alignment.center,
        child: Row(
          children: [
            Expanded(
              child: Container(
                padding: const EdgeInsets.fromLTRB(0, 0, 25, 0),
                alignment: Alignment.topCenter,
                child: Column(
                  children: [
                    Container(
                      padding: const EdgeInsets.fromLTRB(0, 0, 0, 15),
                      child: Center(
                        child: Text(
                          "Agendas Abiertas",
                          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                    Container(
                      child: Expanded(
                        child: agendasAbiertas(),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Expanded(
              child: Container(
                padding: const EdgeInsets.fromLTRB(25, 0, 0, 0),
                alignment: Alignment.topCenter,
                child: Column(
                  children: [
                    Container(
                      padding: const EdgeInsets.fromLTRB(0, 0, 0, 15),
                      child: Center(
                        child: Text(
                          "Agendas a Abrir",
                          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                    Container(
                      child: Expanded(
                        child: agendasAAbrir(),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  FutureBuilder agendasAbiertas() {
    DateTime now = new DateTime.now();
    DateTime date = new DateTime(now.year, now.month, now.day);

    return FutureBuilder(
      future: client.getPlanesVacunacionVigentes(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<PlanVacunacion> agenda = [];
            List<PlanVacunacion> agendasTemp = snapshot.data;
            agendasTemp.forEach((PlanVacunacion element) {
              if (element.fechaInicio.isBefore(date)) {
                agenda.add(element);
              }
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se encuentran agendas abiertas!",
                  style: TextStyle(fontSize: 25),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 400,
                mainAxisExtent: 160,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: agenda.length,
              itemBuilder: (context, index) {
                return new InkWell(
                  hoverColor: Colors.transparent,
                  //onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                  child: new Container(
                    child: new PlanVacCard(
                      planvacun: agenda[index],
                    ),
                  ),
                );
              },
            );
          }
        }
      },
    );
  }

  FutureBuilder agendasAAbrir() {
    DateTime now = new DateTime.now();
    DateTime date = new DateTime(now.year, now.month, now.day);
    return FutureBuilder(
      future: client.getPlanesVacunacionVigentes(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<PlanVacunacion> agenda = [];
            List<PlanVacunacion> agendasTemp = snapshot.data;
            agendasTemp.forEach((PlanVacunacion element) {
              if (element.fechaInicio.isAfter(date)) {
                agenda.add(element);
              }
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se encuentran agendas a abrir!",
                  style: TextStyle(fontSize: 25),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 400,
                mainAxisExtent: 160,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: agenda.length,
              itemBuilder: (context, index) {
                return new InkWell(
                  hoverColor: Colors.transparent,
                  //onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                  child: new Container(
                    child: new PlanVacCard(
                      planvacun: agenda[index],
                    ),
                  ),
                );
              },
            );
          }
        }
      },
    );
  }
}
