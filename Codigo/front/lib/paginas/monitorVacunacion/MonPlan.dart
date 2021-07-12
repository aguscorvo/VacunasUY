import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorPlan.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

late bool planSelected = false;
PlanVacunacion? portraitPlanSelected;

class MonPlan extends StatefulWidget {
  static _MonPlanState? state;
  @override
  _MonPlanState createState() => state = _MonPlanState();
}

class _MonPlanState extends State<MonPlan> {
  BackendConnection client = BackendConnection();

  void landscapePlanSelected(PlanVacunacion? plan) {
    setState(() {
      planSelected = true;
      portraitPlanSelected = plan;
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
      if (planSelected) {
        return MonPlanSelected(plan: portraitPlanSelected);
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
            flex: 30,
            child: Material(
              elevation: 10,
              child: FutureBuilder(
                future: client.getPlanesVacunacions(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      List<PlanVacunacion> planVacunacion = [];
                      List<PlanVacunacion> planVacunacionTemp = snapshot.data as List<PlanVacunacion>;
                      planVacunacionTemp.forEach((PlanVacunacion element) {
                        planVacunacion.add(element);
                      });

                      if (planVacunacion.length == 0) {
                        return Text("No se encuentran enfermedades. Vuelva a intentarlo.");
                      }

                      return Container(
                        padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
                        child: GridView.builder(
                          gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                            childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                            maxCrossAxisExtent: 700,
                            mainAxisExtent: 60,
                            crossAxisSpacing: 10,
                            mainAxisSpacing: 10,
                          ),
                          itemCount: planVacunacion.length,
                          itemBuilder: (context, index) {
                            return InkWell(
                              hoverColor: Colors.transparent,
                              onTap: () => {MonPlanSelected.state.planSeleccionado(planVacunacion[index])},
                              child: Card(
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(8.0),
                                ),
                                elevation: 10,
                                child: Container(
                                  padding: const EdgeInsets.fromLTRB(15, 5, 15, 0),
                                  child: Center(
                                    child: Column(
                                      children: [
                                        Row(
                                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                          children: [
                                            Text("Enfermedad: ", style: TextStyle(fontSize: 15)),
                                            Text(planVacunacion[index].vacuna.enfermedad.nombre, style: TextStyle(fontSize: 15)),
                                          ],
                                        ),
                                        Row(
                                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                          children: [
                                            Text("Vacuna: ", style: TextStyle(fontSize: 15)),
                                            Text(planVacunacion[index].vacuna.nombre, style: TextStyle(fontSize: 15)),
                                          ],
                                        ),
                                      ],
                                    ),
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
            flex: 70,
            child: MonPlanSelected(),
          ),
        ],
      ),
    );
  }

  Widget portrait() {
    new MonPlanSelected();
    return FutureBuilder(
      future: client.getPlanesVacunacions(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<PlanVacunacion> planVacunacion = [];
            List<PlanVacunacion> planVacunacionTemp = snapshot.data as List<PlanVacunacion>;
            planVacunacionTemp.forEach((PlanVacunacion element) {
              planVacunacion.add(element);
            });

            if (planVacunacion.length == 0) {
              return Text("No se encuentran enfermedades. Vuelva a intentarlo.");
            }

            return Container(
              padding: const EdgeInsets.fromLTRB(20, 20, 20, 20),
              child: GridView.builder(
                gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                  childAspectRatio: MediaQuery.of(context).size.width / MediaQuery.of(context).size.height,
                  maxCrossAxisExtent: 700,
                  mainAxisExtent: 70,
                  crossAxisSpacing: 10,
                  mainAxisSpacing: 10,
                ),
                itemCount: planVacunacion.length,
                itemBuilder: (context, index) {
                  return InkWell(
                    hoverColor: Colors.transparent,
                    onTap: () => {
                      landscapePlanSelected(planVacunacion[index]),
                    },
                    child: Card(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8.0),
                      ),
                      elevation: 10,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(25, 5, 25, 5),
                        child: Center(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Expanded(
                                flex: 50,
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Text("Enfermedad: ", style: TextStyle(fontSize: 15)),
                                    Text(planVacunacion[index].vacuna.enfermedad.nombre, style: TextStyle(fontSize: 15)),
                                  ],
                                ),
                              ),
                              Expanded(
                                flex: 50,
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Text("Vacuna: ", style: TextStyle(fontSize: 15)),
                                    Text(planVacunacion[index].vacuna.nombre, style: TextStyle(fontSize: 15)),
                                  ],
                                ),
                              ),
                            ],
                          ),
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
    );
  }
}

class MonPlanSelected extends StatefulWidget {
  static late _MonPlanSelectedState state;
  final PlanVacunacion? plan;
  const MonPlanSelected({this.plan});

  @override
  _MonPlanSelectedState createState() => state = _MonPlanSelectedState(plan);
}

class _MonPlanSelectedState extends State<MonPlanSelected> {
  _MonPlanSelectedState(this.plan);
  BackendConnection client = BackendConnection();
  late MonitorPlan monitorDePlan;
  PlanVacunacion? plan;

  void planSeleccionado(PlanVacunacion? plan) {
    setState(() {
      this.plan = plan;
    });
  }

  @override
  Widget build(BuildContext context) {
    bool portrait = false;
    if (MediaQuery.of(context).size.width < MediaQuery.of(context).size.height) {
      portrait = true;
    }
    if (plan == null) {
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
        future: client.getMonitorPlan(plan!.id),
        builder: (context, snapshot) {
          if (snapshot.connectionState != ConnectionState.done) {
            return Center(child: CircularProgressIndicator());
          } else {
            if (snapshot.data == null) {
              return Center(child: CircularProgressIndicator());
            } else {
              monitorDePlan = snapshot.data as MonitorPlan;

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
                                      portraitPlanSelected = null;
                                      planSelected = false;
                                      MonPlan.state!.reload();
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
                      flex: portrait ? 180 : 120,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(0, 10, 0, 10),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.2,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      height: portrait ? 50 : 25,
                                      color: Colors.blueAccent,
                                      child: portrait
                                          ? Column(
                                              mainAxisAlignment: MainAxisAlignment.center,
                                              children: [
                                                Text("Agendados"),
                                                Text("hoy"),
                                              ],
                                            )
                                          : Center(
                                              child: Text("Agendados hoy"),
                                            ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        child: Center(
                                          child: Text(
                                            monitorDePlan.cantidadAgendasHoy == -1 ? "0" : monitorDePlan.cantidadAgendasHoy.toString(),
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
                                width: MediaQuery.of(context).size.width * 0.2,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      height: portrait ? 50 : 25,
                                      color: Colors.blueAccent,
                                      child: portrait
                                          ? Column(
                                              mainAxisAlignment: MainAxisAlignment.center,
                                              children: [
                                                Text("Vacunados"),
                                                Text("hoy"),
                                              ],
                                            )
                                          : Center(
                                              child: Text("Vacunados hoy"),
                                            ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        child: Center(
                                          child: Text(
                                            monitorDePlan.cantidadVacunadosHoy == -1 ? "0" : monitorDePlan.cantidadVacunadosHoy.toString(),
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
                                width: MediaQuery.of(context).size.width * 0.2,
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  crossAxisAlignment: CrossAxisAlignment.center,
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      height: portrait ? 50 : 25,
                                      color: Colors.blueAccent,
                                      child: portrait
                                          ? Column(
                                              mainAxisAlignment: MainAxisAlignment.center,
                                              children: [
                                                Text("Vacunados"),
                                                Text("en total"),
                                              ],
                                            )
                                          : Center(
                                              child: Text("Vacunados en total"),
                                            ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        padding: const EdgeInsets.fromLTRB(0, 0, 0, 1),
                                        child: Center(
                                            child: Text(
                                          monitorDePlan.cantidadTotalVacunados == -1 ? "0" : monitorDePlan.cantidadTotalVacunados.toString(),
                                          style: TextStyle(fontSize: ((MediaQuery.of(context).size.height * 0.030 * 0.8)) + 0),
                                        )),
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
                      flex: 880,
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
                        child: Material(
                          elevation: 10,
                          child: Container(
                            child: Row(
                              children: [
                                Expanded(
                                  child: Material(
                                    elevation: 10,
                                    child: Container(
                                      padding: const EdgeInsets.fromLTRB(0, 10, 0, 0),
                                      child: Column(
                                        mainAxisAlignment: MainAxisAlignment.start,
                                        crossAxisAlignment: CrossAxisAlignment.center,
                                        children: [
                                          Text(
                                            "Datos del plan",
                                            style: TextStyle(fontSize: 20),
                                          ),
                                          datosPlan(portrait),
                                        ],
                                      ),
                                    ),
                                  ),
                                )
                              ],
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

  Container datosPlan(bool portrait) {
    List<Widget> sectores = [];
    List<Widget> enfermedades = [];

    plan!.sectores.forEach((sector) {
      sectores.add(
        Container(
          padding: EdgeInsets.fromLTRB(5, 0, 5, 0),
          child: Text(sector.nombre),
        ),
      );
    });

    enfermedades.add(
      Container(
        padding: EdgeInsets.fromLTRB(5, 0, 5, 0),
        child: Text(plan!.vacuna.enfermedad.nombre),
      ),
    );

    return Container(
      padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
      child: SingleChildScrollView(
        child: Container(
          width: portrait ? MediaQuery.of(context).size.width * 0.70 : MediaQuery.of(context).size.width * 0.45,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
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
                          width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                          child: Column(
                            children: [
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                color: Colors.blueAccent,
                                child: Center(
                                  child: Text(
                                    "Edad mínima",
                                    style: TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                ),
                              ),
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                child: Center(
                                  child: Text(plan!.edadMinima.toString()),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Material(
                        elevation: 10,
                        child: Container(
                          width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                          child: Column(
                            children: [
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                color: Colors.blueAccent,
                                child: Center(
                                  child: Text(
                                    "Edad máxima",
                                    style: TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                ),
                              ),
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                child: Center(
                                  child: Text(plan!.edadMaxima.toString()),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 5),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Material(
                        elevation: 10,
                        child: Container(
                          width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                          child: Column(
                            children: [
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                color: Colors.blueAccent,
                                child: Center(
                                  child: Text(
                                    "Fecha inicio",
                                    style: TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                ),
                              ),
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                child: Center(
                                  child: Text(formatDate(plan!.fechaInicio)),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Material(
                        elevation: 10,
                        child: Container(
                          width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                          child: Column(
                            children: [
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                color: Colors.blueAccent,
                                child: Center(
                                  child: Text(
                                    "Fecha fin",
                                    style: TextStyle(fontWeight: FontWeight.bold),
                                  ),
                                ),
                              ),
                              Container(
                                width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                child: Center(
                                  child: Text(formatDate(plan!.fechaFin)),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  )
                ],
              ),
              SizedBox(height: 5),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.45,
                      child: Column(
                        children: [
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.45,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            color: Colors.blueAccent,
                            child: Center(
                              child: Text(
                                "Sectores cubiertos",
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                          Container(
                            //width: MediaQuery.of(context).size.width * 0.2,
                            height: 30,
                            padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                            child: Center(
                              child: ListView(
                                scrollDirection: Axis.horizontal,
                                children: sectores,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 5),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.45,
                      padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                      color: Colors.blueAccent,
                      child: Column(
                        children: [
                          Container(
                            child: Text(
                              "Vacuna",
                              style: TextStyle(fontWeight: FontWeight.bold),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 5),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                      child: Column(
                        children: [
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            color: Colors.blueAccent,
                            child: Center(
                              child: Text(
                                "Nombre",
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            child: Center(
                              child: Text(plan!.vacuna.nombre),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                      child: Column(
                        children: [
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            color: Colors.blueAccent,
                            child: Center(
                              child: Text(
                                "Cantidad de dosis",
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            child: Center(
                              child: Text(plan!.vacuna.cantDosis.toString()),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 5),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                      child: Column(
                        children: [
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            color: Colors.blueAccent,
                            child: Center(
                              child: Text(
                                "Período",
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            child: Center(
                              child: Text(plan!.vacuna.periodo.toString()),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                      child: Column(
                        children: [
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            color: Colors.blueAccent,
                            child: Center(
                              child: Text(
                                "Inmunidad",
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            child: Center(
                              child: Text(plan!.vacuna.inmunidad.toString()),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 5),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Material(
                    elevation: 10,
                    child: Container(
                      width: portrait ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.45,
                      child: Column(
                        children: [
                          Container(
                            padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                            width: portrait ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.45,
                            color: Colors.blueAccent,
                            child: Center(
                                child: Text(
                              "Afección",
                              style: TextStyle(fontWeight: FontWeight.bold),
                            )),
                          ),
                          Container(
                            width: portrait ? MediaQuery.of(context).size.width * 0.32 : MediaQuery.of(context).size.width * 0.2,
                            padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                            child: Center(
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: enfermedades,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              )
            ],
          ),
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
}
