import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorVacuna.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class MonVacuna extends StatefulWidget {
  @override
  _MonVacunaState createState() => _MonVacunaState();
}

class _MonVacunaState extends State<MonVacuna> {
  BackendConnection client = BackendConnection();
  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: client.getVacunas(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<Vacuna> vacunas = [];
            List<Vacuna> vacunasTemp = snapshot.data as List<Vacuna>;
            vacunasTemp.forEach((Vacuna element) {
              vacunas.add(element);
            });

            if (vacunas.length == 0) {
              return Text("No se encuentran enfermedades. Vuelva a intentarlo.");
            }

            return Container(
              padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
              child: GridView.builder(
                gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                  childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                  maxCrossAxisExtent: 400,
                  mainAxisExtent: 180,
                  crossAxisSpacing: 10,
                  mainAxisSpacing: 10,
                ),
                itemCount: vacunas.length,
                itemBuilder: (context, index) {
                  Widget cantPorDosis = FutureBuilder(
                    future: client.getMonitorVacuna(vacunas[index].id),
                    builder: (context, snapshot) {
                      if (snapshot.connectionState != ConnectionState.done) {
                        return Center(child: CircularProgressIndicator());
                      } else {
                        if (snapshot.data == null) {
                          return Center(child: CircularProgressIndicator());
                        } else {
                          MonitorVacuna monVac = snapshot.data as MonitorVacuna;

                          List<Widget> agendasHoy = [];
                          List<Widget> vacunadosHoy = [];

                          for (int i = 0; i < vacunas[index].cantDosis; i++) {
                            int cantAgenda = 0;
                            int cantVacunados = 0;

                            if (monVac.agendas.length > 0) {
                              monVac.agendas.forEach((key, value) {
                                if ((i + 1) == key) {
                                  cantAgenda = value;
                                }
                              });
                            }

                            if (monVac.vacunas.length > 0) {
                              monVac.vacunas.forEach((key, value) {
                                if ((i + 1) == key) {
                                  cantVacunados = value;
                                }
                              });
                            }

                            agendasHoy.add(Row(
                              mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: [
                                Text("Dosis " + (i + 1).toString()),
                                Text(cantAgenda.toString()),
                              ],
                            ));
                            vacunadosHoy.add(Row(
                              mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: [
                                Text("Dosis " + (i + 1).toString()),
                                Text(cantVacunados.toString()),
                              ],
                            ));
                          }

                          return Expanded(
                            child: Container(
                              padding: const EdgeInsets.fromLTRB(0, 5, 0, 5),
                              child: Container(
                                //decoration: BoxDecoration(border: Border.all(color: Colors.blueAccent, width: 3)),
                                child: Row(
                                  children: [
                                    Expanded(
                                      flex: 48,
                                      child: Container(
                                        decoration: BoxDecoration(border: Border.all(color: Colors.blueAccent, width: 3)),
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          children: [
                                            Text("Agendas para hoy"),
                                            Column(
                                              crossAxisAlignment: CrossAxisAlignment.center,
                                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                              children: agendasHoy,
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      flex: 4,
                                      child: Container(),
                                    ),
                                    Expanded(
                                      flex: 48,
                                      child: Container(
                                        decoration: BoxDecoration(border: Border.all(color: Colors.blueAccent, width: 3)),
                                        child: Column(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          children: [
                                            Text("Vacunados hoy"),
                                            Column(
                                              crossAxisAlignment: CrossAxisAlignment.center,
                                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                              children: vacunadosHoy,
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          );
                        }
                      }
                    },
                  );

                  return InkWell(
                    hoverColor: Colors.transparent,
                    //onTap: () => {MonPlanSelected.state.vacunaSeleccionada(vacunas[index])},
                    child: Card(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                      elevation: 10,
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Container(
                            decoration: BoxDecoration(
                              color: Colors.blueAccent,
                              borderRadius: BorderRadius.only(
                                topLeft: Radius.circular(8.0),
                                topRight: Radius.circular(8.0),
                              ),
                            ),
                            child: Container(
                              padding: const EdgeInsets.fromLTRB(0, 5, 0, 5),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Text("Nombre vacuna: ", style: TextStyle(fontSize: 15)),
                                  Text(vacunas[index].nombre, style: TextStyle(fontSize: 15)),
                                ],
                              ),
                            ),
                          ),
                          Expanded(
                            child: Container(
                              padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text("Nombre enfermedad: ", style: TextStyle(fontSize: 15)),
                                      Text(vacunas[index].enfermedad.nombre, style: TextStyle(fontSize: 15)),
                                    ],
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text("Cantidad de dosis: ", style: TextStyle(fontSize: 15)),
                                      Text(vacunas[index].cantDosis.toString(), style: TextStyle(fontSize: 15)),
                                    ],
                                  ),
                                  cantPorDosis,
                                ],
                              ),
                            ),
                          )
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
