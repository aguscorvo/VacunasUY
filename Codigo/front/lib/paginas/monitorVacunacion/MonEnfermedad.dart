import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorEnfermedad.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

late bool enfermedadSelected = false;
Enfermedad? portraitEnfermedadSelected;

class MonEnfermedad extends StatefulWidget {
  static _MonEnfermedadState? state;
  @override
  _MonEnfermedadState createState() => state = _MonEnfermedadState();
}

class _MonEnfermedadState extends State<MonEnfermedad> {
  BackendConnection client = BackendConnection();

  void landscapeEnfermedadSelected(Enfermedad? enfermedad) {
    setState(() {
      enfermedadSelected = true;
      portraitEnfermedadSelected = enfermedad;
    });
  }

  void reload() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    if (MediaQuery.of(context).size.width >= MediaQuery.of(context).size.height) {
      return landscape();
    } else {
      if (enfermedadSelected) {
        return MonEnfermedadSelected(enf: portraitEnfermedadSelected);
      } else {
        return portrait();
      }
    }
  }

  Widget landscape() {
    return Container(
      child: Row(
        children: [
          Expanded(
            flex: 25,
            child: Material(
              elevation: 10,
              child: FutureBuilder(
                future: client.getEnfermedades(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      List<Enfermedad> enfermedades = [];
                      List<Enfermedad> enfermedadesTemp = snapshot.data as List<Enfermedad>;
                      enfermedadesTemp.forEach((Enfermedad element) {
                        enfermedades.add(element);
                      });

                      if (enfermedades.length == 0) {
                        return Text("No se encuentran enfermedades. Vuelva a intentarlo.");
                      }

                      return Container(
                        padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
                        child: GridView.builder(
                          gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                            childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                            maxCrossAxisExtent: 600,
                            mainAxisExtent: 40,
                            crossAxisSpacing: 10,
                            mainAxisSpacing: 10,
                          ),
                          itemCount: enfermedades.length,
                          itemBuilder: (context, index) {
                            return InkWell(
                              hoverColor: Colors.transparent,
                              onTap: () => {MonEnfermedadSelected.state.enfermedadSeleccionada(enfermedades[index])},
                              child: Card(
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                elevation: 10,
                                child: Container(
                                  padding: const EdgeInsets.fromLTRB(15, 0, 5, 0),
                                  child: Row(
                                    children: [
                                      Text(
                                        enfermedades[index].nombre,
                                        style: TextStyle(fontSize: 15),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            );
                          },
                        ),
                      );
                    }
                  }
                },
              ),
            ),
          ),
          Expanded(
            flex: 75,
            child: MonEnfermedadSelected(),
          ),
        ],
      ),
    );
  }

  Widget portrait() {
    return Container(
      child: Row(
        children: [
          Expanded(
            flex: 25,
            child: Material(
              elevation: 10,
              child: FutureBuilder(
                future: client.getEnfermedades(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      List<Enfermedad> enfermedades = [];
                      List<Enfermedad> enfermedadesTemp = snapshot.data as List<Enfermedad>;
                      enfermedadesTemp.forEach((Enfermedad element) {
                        enfermedades.add(element);
                      });

                      if (enfermedades.length == 0) {
                        return Text("No se encuentran enfermedades. Vuelva a intentarlo.");
                      }

                      return Container(
                        padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
                        child: GridView.builder(
                          gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                            childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                            maxCrossAxisExtent: 700,
                            mainAxisExtent: 40,
                            crossAxisSpacing: 10,
                            mainAxisSpacing: 10,
                          ),
                          itemCount: enfermedades.length,
                          itemBuilder: (context, index) {
                            return InkWell(
                              hoverColor: Colors.transparent,
                              onTap: () => {landscapeEnfermedadSelected(enfermedades[index])},
                              child: Card(
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                elevation: 10,
                                child: Container(
                                  padding: const EdgeInsets.fromLTRB(15, 0, 5, 0),
                                  child: Row(
                                    children: [
                                      Text(
                                        enfermedades[index].nombre,
                                        style: TextStyle(fontSize: 15),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            );
                          },
                        ),
                      );
                    }
                  }
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class MonEnfermedadSelected extends StatefulWidget {
  static late _MonEnfermedadSelectedState state;
  final Enfermedad? enf;
  const MonEnfermedadSelected({this.enf});

  @override
  _MonEnfermedadSelectedState createState() => state = _MonEnfermedadSelectedState(enf);
}

class _MonEnfermedadSelectedState extends State<MonEnfermedadSelected> {
  _MonEnfermedadSelectedState(this.enf);
  BackendConnection client = BackendConnection();
  late MonitorEnfermedad monitorDeEnfermedad;
  Enfermedad? enf;

  void enfermedadSeleccionada(Enfermedad? enf) {
    setState(() {
      this.enf = enf;
    });
  }

  @override
  Widget build(BuildContext context) {
    bool portrait = false;
    if (MediaQuery.of(context).size.width < MediaQuery.of(context).size.height) {
      portrait = true;
    }
    if (enf == null) {
      return Container(
        child: Center(
          child: Text(
            "No ha seleccionado ninguna enfermedad",
            style: TextStyle(fontSize: 25),
          ),
        ),
      );
    } else {
      return FutureBuilder(
        future: client.getMonitorEnfermedad(enf!.id),
        builder: (context, snapshot) {
          if (snapshot.connectionState != ConnectionState.done) {
            return Center(child: CircularProgressIndicator());
          } else {
            if (snapshot.data == null) {
              return Center(child: CircularProgressIndicator());
            } else {
              monitorDeEnfermedad = snapshot.data as MonitorEnfermedad;

              List<Widget> bottomPart = [
                Expanded(
                  flex: 5,
                  child: Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.9 : null,
                      padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text(
                            "Por tipo de vacuna",
                            style: TextStyle(fontSize: 20),
                          ),
                          porTipoVacuna(portrait),
                        ],
                      ),
                    ),
                  ),
                ),
                Expanded(
                  flex: 5,
                  child: Material(
                    elevation: 20,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.9 : null,
                      padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text(
                            "Planes para la enfermedad",
                            style: TextStyle(fontSize: 20),
                          ),
                          porPlanVacunacion(portrait),
                        ],
                      ),
                    ),
                  ),
                ),
              ];

              return Container(
                child: Column(
                  children: [
                    portrait
                        ? Container(
                            child: Row(
                              children: [
                                Container(
                                  margin: EdgeInsets.only(left: 10, top: 10),
                                  padding: EdgeInsets.symmetric(horizontal: 10),
                                  decoration: BoxDecoration(
                                    color: Colors.blueAccent,
                                    borderRadius: BorderRadius.circular(15),
                                  ),
                                  child: TextButton(
                                    onPressed: () {
                                      portraitEnfermedadSelected = null;
                                      enfermedadSelected = false;
                                      MonEnfermedad.state!.reload();
                                    },
                                    child: Row(
                                      mainAxisAlignment: MainAxisAlignment.start,
                                      children: [
                                        Icon(Icons.arrow_back),
                                        Text("Atras"),
                                      ],
                                    ),
                                  ),
                                )
                              ],
                            ),
                          )
                        : Container(),
                    Expanded(
                      flex: 13,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.3,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.3,
                                      height: 25,
                                      color: Colors.blueAccent,
                                      child: Center(child: Text("Agendados hoy")),
                                    ),
                                    Expanded(
                                      child: Container(
                                        child: Center(
                                          child: Text(
                                            monitorDeEnfermedad.cantidadAgendasHoy == -1 ? "0" : monitorDeEnfermedad.cantidadAgendasHoy.toString(),
                                            style: TextStyle(fontSize: ((MediaQuery.of(context).size.height * 0.030 * 0.8)) + 0),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.3,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.3,
                                      height: 25,
                                      color: Colors.blueAccent,
                                      child: Center(child: Text("Vacunados hoy")),
                                    ),
                                    Expanded(
                                      child: Container(
                                        child: Center(
                                          child: Text(
                                            monitorDeEnfermedad.cantidadVacunadosHoy == -1 ? "0" : monitorDeEnfermedad.cantidadVacunadosHoy.toString(),
                                            style: TextStyle(fontSize: ((MediaQuery.of(context).size.height * 0.030 * 0.8)) + 0),
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    Expanded(
                      flex: 87,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
                        child: Material(
                          elevation: 10,
                          child: Container(
                            child: portrait
                                ? Column(
                                    children: bottomPart,
                                  )
                                : Row(
                                    children: bottomPart,
                                  ),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              );
            }
          }
        },
      );
    }
  }

  Container porTipoVacuna(bool portrait) {
    return Container(
      height: portrait ? MediaQuery.of(context).size.height * 0.25 : MediaQuery.of(context).size.height * 0.6,
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: ListView.builder(
        itemCount: monitorDeEnfermedad.vacunas.length,
        reverse: false,
        itemBuilder: (context, index) {
          VacunaSimp element = monitorDeEnfermedad.vacunas[index];
          return Card(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8.0),
            ),
            elevation: 10,
            child: Container(
              //width: portrait ? MediaQuery.of(context).size.width * 0.35 : MediaQuery.of(context).size.width * 0.3,
              padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
              child: portrait
                  ? Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text("Nombre: " + element.nombre),
                        Text("Cantidad: " + element.cantidad.toString()),
                      ],
                    )
                  : Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Nombre: " + element.nombre),
                        Text("Cantidad: " + element.cantidad.toString()),
                      ],
                    ),
            ),
          );
        },
      ),
    );
  }

  Container porPlanVacunacion(bool portrait) {
    return Container(
      height: portrait ? MediaQuery.of(context).size.height * 0.25 : MediaQuery.of(context).size.height * 0.6,
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: ListView.builder(
        itemCount: monitorDeEnfermedad.planes.length,
        reverse: false,
        itemBuilder: (context, index) {
          PlanSimp element = monitorDeEnfermedad.planes[index];
          return Card(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(8.0),
            ),
            elevation: 10,
            child: Container(
              width: portrait ? MediaQuery.of(context).size.width * 0.8 : MediaQuery.of(context).size.width * 0.4,
              padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
              child: portrait
                  ? Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text("Nombre: " + element.nombre),
                        Text("Período: " + reformateDate(element.rangoFecha)),
                      ],
                    )
                  : Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Nombre: " + element.nombre),
                        Text("Período: " + reformateDate(element.rangoFecha)),
                      ],
                    ),
            ),
          );
        },
      ),
    );
  }

  String reformateDate(String dateRange) {
    String toReturn = "";

    String date1 = dateRange.split(" - ")[0];
    String date2 = dateRange.split(" - ")[1];

    toReturn = date1.split("-")[2] + "/" + date1.split("-")[1] + "/" + date1.split("-")[0];
    toReturn += " - " + date2.split("-")[2] + "/" + date2.split("-")[1] + "/" + date2.split("-")[0];

    return toReturn;
  }
}
