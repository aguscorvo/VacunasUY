import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorEnfermedad.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class MonEnfermedad extends StatefulWidget {
  @override
  _MonEnfermedadState createState() => _MonEnfermedadState();
}

class _MonEnfermedadState extends State<MonEnfermedad> {
  BackendConnection client = BackendConnection();
  @override
  Widget build(BuildContext context) {
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
                        return Text("No se encuentran enfermedades, reintente!");
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
                                        "Nombre: " + enfermedades[index].nombre,
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

              return Container(
                child: Column(
                  children: [
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
                                      child: Center(child: Text("Cantidad Agendados Hoy")),
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
                                      child: Center(child: Text("Vacunados Hoy")),
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
                              child: Row(
                                children: [
                                  Expanded(
                                    flex: 5,
                                    child: Material(
                                      elevation: 10,
                                      child: Container(
                                        padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Text(
                                              "Por Tipo de Vacuna",
                                              style: TextStyle(fontSize: 20),
                                            ),
                                            porTipoVacuna(),
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
                                        padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Text(
                                              "Planes para la Enfermedad",
                                              style: TextStyle(fontSize: 20),
                                            ),
                                            porPlanVacunacion(),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        )),
                  ],
                ),
              );
            }
          }
        },
      );
    }
  }

  Container porTipoVacuna() {
    List<Card> porVacunas = [];
    monitorDeEnfermedad.vacunas.forEach((element) {
      porVacunas.add(
        Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
          elevation: 10,
          child: Container(
            padding: const EdgeInsets.fromLTRB(40, 10, 40, 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Nombre: " + element.nombre),
                Text("Cantidad: " + element.cantidad.toString()),
              ],
            ),
          ),
        ),
      );
    });

    return Container(
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: SingleChildScrollView(
        child: Column(
          children: porVacunas,
        ),
      ),
    );
  }

  Container porPlanVacunacion() {
    List<Card> porPlanes = [];
    monitorDeEnfermedad.planes.forEach((element) {
      porPlanes.add(
        Card(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8.0),
          ),
          elevation: 10,
          child: Container(
            padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text("Nombre: " + element.nombre),
                Text("Período: " + reformateDate(element.rangoFecha)),
              ],
            ),
          ),
        ),
      );
    });

    return Container(
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: SingleChildScrollView(
        child: Column(
          children: porPlanes,
        ),
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
