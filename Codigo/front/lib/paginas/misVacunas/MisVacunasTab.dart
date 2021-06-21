import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/ActoVacunal.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';

class MisVacunasTab extends StatefulWidget {
  @override
  _MisVacunasTabState createState() => _MisVacunasTabState();
}

class _MisVacunasTabState extends State<MisVacunasTab> {
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
  List<ActoVacunal> actosVacunales = [];
  Enfermedad? enf;

  void enfermedadSeleccionada(Enfermedad enf) {
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
        future: client.getActosVacunalesPorEnfermedadLogged(enf!.id),
        builder: (context, snapshot) {
          if (snapshot.connectionState != ConnectionState.done) {
            return Center(child: CircularProgressIndicator());
          } else {
            if (snapshot.data == null) {
              return Center(child: CircularProgressIndicator());
            } else {
              actosVacunales = snapshot.data as List<ActoVacunal>;

              if (actosVacunales.length == 0) {
                return Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        "No se encontraron vacunas para la enfermedad ",
                        style: TextStyle(fontSize: 25),
                      ),
                      Text(
                        enf!.nombre,
                        style: TextStyle(fontSize: 25),
                      )
                    ],
                  ),
                );
              }

              return Container(
                padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
                child: GridView.builder(
                  gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                    childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                    maxCrossAxisExtent: 2560,
                    mainAxisExtent: 50,
                    crossAxisSpacing: 10,
                    mainAxisSpacing: 10,
                  ),
                  itemCount: actosVacunales.length,
                  itemBuilder: (context, index) {
                    return Card(
                      elevation: 10,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(25, 5, 25, 5),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(actosVacunales[index].nombre),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.end,
                              children: [
                                Container(
                                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                                  decoration: BoxDecoration(color: Colors.blueAccent, borderRadius: BorderRadius.circular(10)),
                                  child: TextButton(
                                    onPressed: () {
                                      shareTweeter(
                                          "El " + actosVacunales[index].fecha.toString() + " me vacune contra la enfermedad " + enf!.nombre + " con la vacuna " + actosVacunales[index].nombre + "!!!");
                                    },
                                    child: Text(
                                      "Compartir en Tweeter",
                                      style: TextStyle(color: Colors.black),
                                    ),
                                  ),
                                ),
                                SizedBox(width: 50),
                                Container(
                                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                                  decoration: BoxDecoration(color: Colors.blueAccent, borderRadius: BorderRadius.circular(10)),
                                  child: TextButton(
                                    onPressed: () {
                                      shareFacebook(
                                          "El " + actosVacunales[index].fecha.toString() + " me vacune contra la enfermedad " + enf!.nombre + " con la vacuna " + actosVacunales[index].nombre + "!!!");
                                    },
                                    child: Text(
                                      "Compartir en Facebook",
                                      style: TextStyle(color: Colors.black),
                                    ),
                                  ),
                                ),
                                SizedBox(width: 50),
                                Text(actosVacunales[index].fecha),
                              ],
                            ),
                          ],
                        ),
                      ),
                    );
                  },
                ),
              );
            }
          }
        },
      );
    }
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
