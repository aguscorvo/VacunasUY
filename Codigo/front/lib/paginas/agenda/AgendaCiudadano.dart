import 'package:flutter/material.dart';
import 'package:vacunas_uy/assets/AgendaCard.dart';
import 'package:vacunas_uy/assets/PlanVacCard.dart';
import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';

class AgendaCiudadano extends StatefulWidget {
  @override
  _AgendaCiudadanoState createState() => _AgendaCiudadanoState();
}

class _AgendaCiudadanoState extends State<AgendaCiudadano> {
  BackendConnection client = BackendConnection();
  Widget agendaToLoad;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: const EdgeInsets.fromLTRB(25.0, 25, 25.0, 25.0),
        alignment: Alignment.center,
        child: Row(children: [
          Expanded(
            child: Column(
              children: [
                Expanded(
                  child: Material(
                    elevation: 20,
                    child: Container(
                      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
                      alignment: Alignment.center,
                      child: Column(
                        children: [
                          Center(
                            child: Text(
                              "Angedas Abiertas",
                              style: TextStyle(fontSize: 15),
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
                ),
                Expanded(
                  child: Material(
                    elevation: 20,
                    child: Container(
                      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
                      alignment: Alignment.center,
                      child: Column(
                        children: [
                          Center(
                            child: Text(
                              "Agendas a Abrir",
                              style: TextStyle(fontSize: 15),
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
                ),
              ],
            ),
          ),
          Expanded(
            child: Material(
              elevation: 20,
              child: Container(
                padding: const EdgeInsets.fromLTRB(25, 0, 0, 0),
                alignment: Alignment.center,
                child: Column(
                  children: [
                    Center(
                      child: Text(
                        "Mis Agendas",
                        style: TextStyle(fontSize: 15),
                      ),
                    ),
                    Container(
                      child: Expanded(
                        child: misAgendas(),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ]),
      ),
    );
  }

  FutureBuilder agendasAbiertas() {
    DateTime now = new DateTime.now();
    DateTime date = new DateTime(now.year, now.month, now.day);

    return FutureBuilder(
      future: client.getPlanesVacunacion(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return CircularProgressIndicator();
        } else {
          if (snapshot.data == null) {
            return CircularProgressIndicator();
          } else {
            List<PlanVacunacion> agenda = [];
            List<PlanVacunacion> agendasTemp = snapshot.data;
            agendasTemp.forEach((PlanVacunacion element) {
              if (element.fechaInicio.isBefore(date)) {
                agenda.add(element);
              }
            });

            if (agenda.length == 0) {
              return Text("No se encuentran agendas abiertas, reintente!");
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 176,
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
      future: client.getPlanesVacunacion(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return CircularProgressIndicator();
        } else {
          if (snapshot.data == null) {
            return CircularProgressIndicator();
          } else {
            List<PlanVacunacion> agenda = [];
            List<PlanVacunacion> agendasTemp = snapshot.data;
            agendasTemp.forEach((PlanVacunacion element) {
              if (element.fechaInicio.isAfter(date)) {
                agenda.add(element);
              }
            });

            if (agenda.length == 0) {
              return Text("No se encuentran agendas abiertas, reintente!");
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 176,
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

  FutureBuilder misAgendas() {
    return FutureBuilder(
      future: client.getAgendasCiudadano(storedUserCredentials.userData.id),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return CircularProgressIndicator();
        } else {
          if (snapshot.data == null) {
            return CircularProgressIndicator();
          } else {
            List<Agenda> agenda = [];
            List<Agenda> agendasTemp = snapshot.data;
            agendasTemp.forEach((Agenda element) {
              agenda.add(element);
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se encuentra agendado en ningun plan!",
                  style: TextStyle(fontSize: 25),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 176,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: agenda.length,
              itemBuilder: (context, index) {
                return new InkWell(
                  hoverColor: Colors.transparent,
                  //onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                  child: new Container(
                    child: new AgendaCard(
                      agenda: agenda[index],
                      usuario: storedUserCredentials.userData,
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
