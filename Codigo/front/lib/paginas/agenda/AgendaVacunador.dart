import 'package:flutter/material.dart';
import 'package:vacunas_uy/assets/AtiendeCard.dart';
import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:vacunas_uy/objects/Atiende.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class AgendaVacunador extends StatefulWidget {
  @override
  _AgendaVacunadorState createState() => _AgendaVacunadorState();
}

class _AgendaVacunadorState extends State<AgendaVacunador> {
  late Widget agendaToLoad;
  BackendConnection client = BackendConnection();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        padding: const EdgeInsets.fromLTRB(25.0, 25, 25.0, 25.0),
        alignment: Alignment.center,
        child: Row(children: [
          Expanded(
            child: Row(
              children: [
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
                              "Mis agendas como Vacunador",
                              style: TextStyle(fontSize: 15),
                            ),
                          ),
                          Container(
                            //padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
                            child: Expanded(
                              child: misAgendas(),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
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
                              "Datos de agenda seleccionada",
                              style: TextStyle(fontSize: 15),
                            ),
                          ),
                          Container(
                            child: Expanded(
                              child: AgendaSeleccionada(),
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
        ]),
      ),
    );
  }

  List<Atiende> ordenarAtiende(List<Atiende> aOrdenar) {
    List<Atiende> toReturn = aOrdenar;

    toReturn.sort((a, b) {
      var adate = a.fecha;
      var bdate = b.fecha;
      return adate.compareTo(bdate);
    });

    return toReturn;
  }

  FutureBuilder misAgendas() {
    return FutureBuilder(
      future: client.getAgendasVacunadorLogged(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<Atiende> atiende = [];
            List<Atiende> atiendeTemp = snapshot.data;
            atiendeTemp.forEach((Atiende plan) {
              if (plan.fecha.isAfter(DateTime.now()) || plan.fecha.isAtSameMomentAs(DateTime.now())) {
                atiende.add(plan);
              }
            });

            atiende = ordenarAtiende(atiende);

            if (atiende.length == 0) {
              return Center(
                child: Text(
                  "No se han encontrado agendas pendientes.",
                  style: TextStyle(fontSize: 18),
                ),
              );
            }
            return GridView.builder(
              gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                maxCrossAxisExtent: 600,
                mainAxisExtent: 115,
                crossAxisSpacing: 10,
                mainAxisSpacing: 10,
              ),
              itemCount: atiende.length,
              itemBuilder: (context, index) {
                return new InkWell(
                  hoverColor: Colors.transparent,
                  onTap: () => {
                    AgendaSeleccionada.state!.cambiarAgenda(atiende[index]),
                  },
                  child: new Container(
                    child: new AtiendeCard(
                      atiende: atiende[index],
                      //habilitados: true,
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

class AgendaSeleccionada extends StatefulWidget {
  static _AgendaSeleccionadaState? state;
  final Atiende? agenda;

  const AgendaSeleccionada({this.agenda});
  @override
  _AgendaSeleccionadaState createState() => state = _AgendaSeleccionadaState(agenda);
}

class _AgendaSeleccionadaState extends State<AgendaSeleccionada> {
  BackendConnection client = BackendConnection();
  Atiende? agenda;
  bool listAll = false;

  _AgendaSeleccionadaState(this.agenda);

  void cambiarAgenda(Atiende newAgenda) {
    setState(() {
      agenda = newAgenda;
    });
  }

  @override
  Widget build(BuildContext context) {
    return agenda == null ? agendaNotSelected() : agendaSelected();
  }

  Container agendaSelected() {
    return Container(
      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
      alignment: Alignment.topCenter,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            flex: 100,
            child: Container(
              width: MediaQuery.of(context).size.width * 0.45,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  Column(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Material(
                            elevation: 10,
                            child: Container(
                              width: MediaQuery.of(context).size.width * 0.1,
                              child: Column(
                                children: [
                                  Container(
                                    width: MediaQuery.of(context).size.width * 0.1,
                                    padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                    color: Colors.blueAccent,
                                    child: Center(
                                      child: Text(
                                        "Fecha",
                                        style: TextStyle(fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                  Container(
                                    //width: MediaQuery.of(context).size.width * 0.2,
                                    height: 30,
                                    padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                                    child: Center(
                                      child: Text(formatDate(agenda!.fecha)),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Material(
                            elevation: 10,
                            child: Container(
                              width: MediaQuery.of(context).size.width * 0.25,
                              child: Column(
                                children: [
                                  Container(
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                    color: Colors.blueAccent,
                                    child: Center(
                                      child: Text(
                                        "Vacunatorio",
                                        style: TextStyle(fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                  Container(
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                    child: Center(
                                      child: Text(agenda!.puesto.vacunatorio.nombre),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Material(
                            elevation: 10,
                            child: Container(
                              width: MediaQuery.of(context).size.width * 0.1,
                              child: Column(
                                children: [
                                  Container(
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                    color: Colors.blueAccent,
                                    child: Center(
                                      child: Text(
                                        "Puesto",
                                        style: TextStyle(fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                  Container(
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                    child: Center(
                                      child: Text(agenda!.puesto.numero.toString()),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                      SizedBox(height: 10),
                      Column(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Material(
                                elevation: 10,
                                child: Container(
                                  width: MediaQuery.of(context).size.width * 0.45,
                                  child: Column(
                                    children: [
                                      Container(
                                        width: MediaQuery.of(context).size.width * 0.45,
                                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                        color: Colors.blueAccent,
                                        child: Center(
                                          child: Text(
                                            "Dirección",
                                            style: TextStyle(fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ),
                                      Container(
                                        //width: MediaQuery.of(context).size.width * 0.2,
                                        height: 30,
                                        padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                                        child: Center(
                                          child: Text(agenda!.puesto.vacunatorio.direccion),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ],
                  ),
                  SizedBox(height: 15),
                  Text("Proximos a vacunar", style: TextStyle(fontSize: 20)),
                  cargarCiudadanosDelDia(),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  List<Atiende> ordenarAgendasDelDia(List<Atiende> aOrdenar) {
    List<Atiende> toReturn = aOrdenar;

    toReturn.sort((a, b) {
      var adate = a.fecha;
      var bdate = b.fecha;
      return adate.compareTo(bdate);
    });

    return toReturn;
  }

  Container cargarCiudadanosDelDia() {
    List<Material> agendados = [];

    List<Agenda> agendas = agenda!.puesto.agendas;

    agendas.sort((a, b) {
      var adate = a.fecha;
      var bdate = b.fecha;
      return adate.compareTo(bdate);
    });

    agendas.forEach((element) {
      if (theSameDay(agenda!.fecha, element.fecha)) {
        agendados.add(
          Material(
            elevation: 10,
            child: Container(
              padding: const EdgeInsets.fromLTRB(5, 5, 5, 5),
              decoration: BoxDecoration(
                borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(8.0),
                  topRight: Radius.circular(8.0),
                  bottomLeft: Radius.circular(8.0),
                  bottomRight: Radius.circular(8.0),
                ),
              ),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  Text("Hora: " + element.fecha.toString().split(" ")[1].split(".")[0]),
                  Text("Vacuna: " + element.planVacunacion.vacuna.nombre),
                  Text("Dosis: ?"),
                  /*theSameDay(element.fecha, DateTime.now() /*.add(const Duration(days: 1))*/)
                      ? Container(
                          padding: const EdgeInsets.fromLTRB(5, 0, 5, 0),
                          decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                          child: TextButton(
                            onPressed: () async {},
                            child: Text(
                              'Vacunar',
                              style: TextStyle(color: Colors.white, fontSize: 15),
                            ),
                          ),
                        )
                      : Text(""),*/
                ],
              ),
            ),
          ),
        );
      }
    });

    return Container(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: agendados,
      ),
    );
  }

  Container agendaNotSelected() {
    return Container(
      padding: const EdgeInsets.fromLTRB(25, 0, 25, 0),
      alignment: Alignment.center,
      child: Center(
        child: Text(
          "Seleccione una agenda",
          style: TextStyle(fontSize: 15),
        ),
      ),
    );
  }

  String formatTime(DateTime date) {
    String toReturn = "";

    if (date.hour < 10) {
      toReturn = "0" + date.hour.toString();
    } else {
      toReturn = date.hour.toString();
    }

    if (date.minute < 10) {
      toReturn = toReturn + ":0" + date.minute.toString();
    } else {
      toReturn = toReturn + ":" + date.minute.toString();
    }

    if (date.second < 10) {
      toReturn = toReturn + ":0" + date.second.toString();
    } else {
      toReturn = toReturn + ":" + date.second.toString();
    }

    return toReturn;
  }

  String formatDate(DateTime date) {
    String toReturn = "";

    if (date.day < 10) {
      toReturn = "0" + date.day.toString();
    } else {
      toReturn = date.day.toString();
    }

    if (date.month < 10) {
      toReturn = toReturn + "/0" + date.month.toString();
    } else {
      toReturn = toReturn + "/" + date.month.toString();
    }

    toReturn = toReturn + "/" + date.year.toString();

    return toReturn;
  }

  bool theSameDay(DateTime date1, DateTime date2) {
    if (date1.year == date2.year && date1.month == date2.month && date1.day == date2.day) {
      return true;
    } else {
      return false;
    }
  }
}
