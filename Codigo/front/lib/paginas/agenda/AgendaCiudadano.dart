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
  late Widget agendaToLoad;

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
                  flex: 6,
                  child: Material(
                    elevation: 20,
                    child: Container(
                      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
                      alignment: Alignment.center,
                      child: Column(
                        children: [
                          Center(
                            child: Text(
                              "Agendas Habilitadas para mi",
                              style: TextStyle(fontSize: 15),
                            ),
                          ),
                          Container(
                            child: Expanded(
                              child: agendasHabilitadasParaMi(),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
                Expanded(
                  flex: 4,
                  child: Material(
                    elevation: 20,
                    child: Container(
                      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
                      alignment: Alignment.center,
                      child: Column(
                        children: [
                          Center(
                            child: Text(
                              "Todas las Agendas",
                              style: TextStyle(fontSize: 15),
                            ),
                          ),
                          Container(
                            child: Expanded(
                              child: todasLasAgendas(),
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
              child: MisAgendasCiudadano(),
            ),
          ),
        ]),
      ),
    );
  }

  FutureBuilder agendasHabilitadasParaMi() {
    //DateTime now = new DateTime.now();
    //DateTime date = new DateTime(now.year, now.month, now.day);

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
            agendasTemp.forEach((PlanVacunacion plan) {
              plan.sectores.forEach((sector) {
                if (sector.id == storedUserCredentials!.userData!.sectorLaboral.id && edadEntreRangos(plan)) {
                  agenda.add(plan);
                }
              });
              //if (element.fechaInicio.isBefore(date)) {}
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se han encontrado agendas para las cuales este habilitado.",
                  style: TextStyle(fontSize: 18),
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
                      habilitados: true,
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

  FutureBuilder todasLasAgendas() {
    //DateTime now = new DateTime.now();
    //DateTime date = new DateTime(now.year, now.month, now.day);
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
              if (true) {
                agenda.add(element);
              }
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se han encontrado agendas, intente nuevamente mas tarde.",
                  style: TextStyle(fontSize: 18),
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
                      habilitados: false,
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

  bool edadEntreRangos(PlanVacunacion plan) {
    bool toReturn = false;

    DateTime date = storedUserCredentials!.userData!.fechaNacimiento;
    int days = date.difference(DateTime.now()).inDays;
    int years = (days ~/ 365.2422) * (-1); // el simbolo ~ hace que redonde a int

    if (plan.edadMaxima >= years && plan.edadMinima <= years) {
      toReturn = true;
    }

    return toReturn;
  }
}

class MisAgendasCiudadano extends StatefulWidget {
  static late _MisAgendasCiudadanoState state;
  @override
  _MisAgendasCiudadanoState createState() => state = _MisAgendasCiudadanoState();
}

class _MisAgendasCiudadanoState extends State<MisAgendasCiudadano> {
  BackendConnection client = BackendConnection();
  bool listAll = false;

  void cambiarListarTodo() {
    setState(() {
      listAll = !listAll;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
      alignment: Alignment.center,
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              //Text("                                          "),
              Text(
                "Mis Agendas",
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              /*TextButton(
                child: Text(listAll
                    ? "Listar Solo Nuevas Agendas"
                    : "Listar Todas Mis Agendas"),
                onPressed: () {
                  cambiarListarTodo();
                },
              ),*/
            ],
          ),
          Container(
            child: Expanded(
              child: misAgendas(),
            ),
          ),
        ],
      ),
    );
  }

  FutureBuilder misAgendas() {
    return FutureBuilder(
      future: /*listAll ? client.getAgendasCiudadanoTodas(storedUserCredentials.userData.id) : */ client.getAgendasCiudadanoNuevas(storedUserCredentials!.userData!.id),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
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
                  style: TextStyle(fontSize: 18),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 180,
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
                      usuario: storedUserCredentials!.userData,
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
