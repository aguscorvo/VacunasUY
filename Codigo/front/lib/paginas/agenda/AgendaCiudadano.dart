import 'package:flutter/material.dart';
import 'package:vacunas_uy/assets/AgendaCard.dart';
import 'package:vacunas_uy/assets/PlanVacCard.dart';
import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
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

class AgendaCiudadano extends StatefulWidget {
  @override
  _AgendaCiudadanoState createState() => _AgendaCiudadanoState();
}

class _AgendaCiudadanoState extends State<AgendaCiudadano> {
  BackendConnection client = BackendConnection();
  late Widget agendaToLoad;

  TabBar get _tabsVacunador => TabBar(
        tabs: [
          Tab(text: "Todas las agendas"),
          Tab(text: "Agendas habilitadas para mí"),
          Tab(text: "Mis agendas"),
        ],
      );

  @override
  Widget build(BuildContext context) {
    return Material(
      elevation: 10,
      child: DefaultTabController(
        length: 3,
        initialIndex: 1,
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
              Container(
                child: todasLasAgendas(),
              ),
              Container(
                child: agendasHabilitadasParaMi(),
              ),
              MisAgendasCiudadano(),
            ],
          ),
        ),
      ),
    );
    /*return Scaffold(
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
                              style: TextStyle(fontSize: 15, fontWeight: FontWeight.bold),
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
                              style: TextStyle(fontSize: 15, fontWeight: FontWeight.bold),
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
    );*/
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
                  "No se encuentra habilitado a ninguna agenda.",
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
                  "No se han encontrado agendas. Vuelva a intentarlo más tarde.",
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

  void refresh() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
      alignment: Alignment.center,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [],
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
      future: client.getAgendasCiudadanoNuevas(storedUserCredentials!.userData!.id),
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

            agenda.sort((a, b) {
              var adate = a.fecha;
              var bdate = b.fecha;
              return adate.compareTo(bdate);
            });

            if (agenda.length == 0) {
              return Center(
                child: Text(
                  "No se encuentra agendado en ningún plan.",
                  style: TextStyle(fontSize: 18),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 190,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: agenda.length,
              itemBuilder: (context, index) {
                return new InkWell(
                  hoverColor: Colors.transparent,
                  child: new Container(
                    child: new AgendaCard(
                      agenda: agenda[index],
                      usuario: storedUserCredentials!.userData,
                      refresh: () => refresh(),
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
