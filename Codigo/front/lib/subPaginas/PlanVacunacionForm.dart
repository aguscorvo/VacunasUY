import 'package:date_field/date_field.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Puesto.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class PlanVacunacionForm extends StatelessWidget {
  final PlanVacunacion planvacunacion;
  final Usuario usuario;
  final Color color;
  final String tipoForm; //Si es Editar o si es Alta
  const PlanVacunacionForm({
    Key key,
    this.planvacunacion,
    this.usuario,
    this.color = Colors.blue,
    this.tipoForm = "",
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (tipoForm == "Editar") {
      return editar(context);
    } else if (tipoForm == "Agendar") {
      return agendar(context);
    } else if (tipoForm == "Informacion") {
      return informacion(context);
    } else {
      return null;
    }
  }

  AlertDialog editar(BuildContext context) {
    var nombreController = new TextEditingController();
    return AlertDialog(
      title: Text("Editar Vacunatorio"),
      content: Stack(
        clipBehavior: Clip.none,
        children: <Widget>[
          Positioned(
            right: -60.0,
            top: -100.0,
            child: InkResponse(
              onTap: () {
                Navigator.of(context).pop();
              },
              child: CircleAvatar(
                child: Icon(Icons.close),
                backgroundColor: Colors.red,
              ),
            ),
          ),
          Positioned(
            right: 300.0,
            top: 10.0,
            child: Text("Editar Vacunatorio"),
          ),
          Container(
            width: MediaQuery.of(context).size.width * 0.8,
            height: MediaQuery.of(context).size.height * 0.8,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: Colors.grey[350],
            ),
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: TextFormField(
              controller: nombreController..text = "", //to set text
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Nombre',
                hintText: 'Ingresar nombre del Vacunatorio',
              ),
            ),
          ),
        ],
      ),
    );
  }

  AlertDialog informacion(BuildContext context) {
    List<Widget> sectores = [];
    List<Widget> enfermedades = [];

    planvacunacion.sectores.forEach((sector) {
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
        child: Text(planvacunacion.vacuna.enfermedad.nombre),
      ),
    );

    return AlertDialog(
      title: Text("Informacion"),
      content: Stack(
        clipBehavior: Clip.none,
        children: <Widget>[
          Positioned(
            right: -60.0,
            top: -100.0,
            child: InkResponse(
              onTap: () {
                Navigator.of(context).pop();
              },
              child: CircleAvatar(
                child: Icon(Icons.close),
                backgroundColor: Colors.red,
              ),
            ),
          ),
          Positioned(
            right: 300.0,
            top: 10.0,
            child: Text("Informacion"),
          ),
          Container(
            width: MediaQuery.of(context).size.width * 0.5,
            height: MediaQuery.of(context).size.height * 0.5,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: Colors.grey[350],
            ),
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Container(
                  child: Text(planvacunacion.vacuna.nombre),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Edad Minima"),
                            Text(planvacunacion.edadMinima.toString()),
                          ],
                        ),
                      ),
                    ),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Edad Maxima"),
                            Text(planvacunacion.edadMaxima.toString()),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Fecha Inicio"),
                            Text(formatDate(planvacunacion.fechaInicio)),
                          ],
                        ),
                      ),
                    ),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Fecha Fin"),
                            Text(formatDate(planvacunacion.fechaFin)),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.4275,
                        padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                        child: Column(
                          children: [
                            Container(
                              padding: EdgeInsets.fromLTRB(0, 0, 0, 5),
                              child: Text("Sectores Cubiertos"),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: sectores,
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.4275,
                        padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                        child: Column(
                          children: [
                            Container(
                              child: Text("Vacuna"),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Nombre"),
                            Text(planvacunacion.vacuna.nombre),
                          ],
                        ),
                      ),
                    ),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Cantidad de Dosis"),
                            Text(planvacunacion.vacuna.cantDosis.toString()),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Periodo"),
                            Text(planvacunacion.vacuna.periodo.toString()),
                          ],
                        ),
                      ),
                    ),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.2,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        child: Column(
                          children: [
                            Text("Inmunidad"),
                            Text(planvacunacion.vacuna.inmunidad.toString()),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width * 0.4275,
                        padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                        child: Column(
                          children: [
                            Container(
                              padding: EdgeInsets.fromLTRB(0, 0, 0, 5),
                              child: Text("Afeccion"),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: enfermedades,
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  AlertDialog agendar(BuildContext context) {
    BackendConnection client = BackendConnection();

    List<InkWell> vacunatoriosInkWell = [];

    Vacunatorio selectedVacunatorio;

    return AlertDialog(
      title: Text("Agendarme"),
      content: Stack(
        clipBehavior: Clip.none,
        children: <Widget>[
          Positioned(
            right: -60.0,
            top: -100.0,
            child: InkResponse(
              onTap: () {
                Navigator.of(context).pop();
              },
              child: CircleAvatar(
                child: Icon(Icons.close),
                backgroundColor: Colors.red,
              ),
            ),
          ),
          Positioned(
            right: 300.0,
            top: 10.0,
            child: Text("Agendarme"),
          ),
          Container(
            width: MediaQuery.of(context).size.width * 0.5,
            height: MediaQuery.of(context).size.height * 0.5,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: Colors.grey[350],
            ),
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: FutureBuilder(
              future: client.getVacunatoriosDadoPlan(planvacunacion.id),
              builder: (context, snapshot) {
                if (snapshot.connectionState != ConnectionState.done) {
                  return CircularProgressIndicator();
                } else {
                  if (snapshot.data == null) {
                    return CircularProgressIndicator();
                  } else {
                    List<Vacunatorio> vacunatorios = [];
                    List<Vacunatorio> vacunatoriosTemp = snapshot.data;
                    vacunatoriosTemp.forEach((Vacunatorio element) {
                      vacunatorios.add(element);
                    });

                    vacunatorios.forEach((vacunatorio) {
                      vacunatoriosInkWell.add(
                        InkWell(
                          child: ListTile(
                            leading: Icon(Icons.apartment_sharp),
                            title: Text("Nombre: " + vacunatorio.nombre),
                            subtitle: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: <Widget>[
                                Text("Departamento: " + vacunatorio.departamento.nombre),
                              ],
                            ),
                          ),
                          onTap: () => {
                            selectedVacunatorio = vacunatorio,
                            PuestosList.state.vacunatorioSelected(vacunatorio),
                          },
                        ),
                      );
                    });

                    return Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Container(
                          child: Text(planvacunacion.vacuna.nombre),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.2,
                                height: MediaQuery.of(context).size.height * 0.4,
                                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                child: SingleChildScrollView(
                                  child: Column(
                                    children: vacunatoriosInkWell,
                                  ),
                                ),
                              ),
                            ),
                            Material(
                              elevation: 10,
                              child: PuestosList(
                                selectedVacunatorio: selectedVacunatorio,
                                planvacunacion: planvacunacion,
                                usuario: usuario,
                              ),
                            ),
                          ],
                        ),
                      ],
                    );
                  }
                }
              },
            ),
          ),
        ],
      ),
    );
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

class PuestosList extends StatefulWidget {
  final Vacunatorio selectedVacunatorio;
  final PlanVacunacion planvacunacion;
  final Usuario usuario;
  const PuestosList({Key key, this.selectedVacunatorio, this.planvacunacion, this.usuario}) : super(key: key);
  static _PuestosListState state;
  @override
  _PuestosListState createState() => state = _PuestosListState(selectedVacunatorio, planvacunacion, usuario);
}

class _PuestosListState extends State<PuestosList> {
  Vacunatorio selectedVacunatorio;
  PlanVacunacion planvacunacion;
  Usuario usuario;
  BackendConnection client = BackendConnection();

  Puesto selectedPuesto;
  DateTimeFormField datetimeform;
  DateTime fechaPreferencial;

  bool selectedDate = false;

  bool enableButton = true;

  List<InkWell> puestosInkWell = [];

  _PuestosListState(this.selectedVacunatorio, this.planvacunacion, this.usuario);

  void vacunatorioSelected(Vacunatorio vacunatorio) {
    setState(() {
      puestosInkWell = [];
      selectedVacunatorio = vacunatorio;
      selectedDate = false;
      enableButton = false;
      selectedPuesto = null;
    });
  }

  void puestoSelected() {
    setState(() {
      puestosInkWell = [];
      if (selectedDate == true) {
        enableButton = true;
      }
    });
  }

  void dateSelected() {
    setState(() {
      puestosInkWell = [];
      if (selectedPuesto != null) {
        enableButton = true;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    if (selectedVacunatorio != null) {
      selectedVacunatorio.puestos.forEach((puesto) {
        puestosInkWell.add(
          InkWell(
            child: ListTile(
              leading: Icon(Icons.apartment_sharp),
              title: Text("Puesto " + puesto.numero.toString()),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  //Text("Departamento: " + vacunatorio.departamento.nombre),
                ],
              ),
            ),
            onTap: () => {
              selectedPuesto = puesto,
              puestoSelected(),
            },
          ),
        );
      });
    }

    return Container(
      width: MediaQuery.of(context).size.width * 0.2,
      height: MediaQuery.of(context).size.height * 0.4,
      child: Column(
        children: [
          Material(
            elevation: 10,
            child: Container(
              width: MediaQuery.of(context).size.width * 0.2,
              height: MediaQuery.of(context).size.height * 0.325,
              child: SingleChildScrollView(
                child: Column(
                  children: puestosInkWell,
                ),
              ),
            ),
          ),
          Material(
            elevation: 10,
            child: Container(
              width: MediaQuery.of(context).size.width * 0.2,
              height: MediaQuery.of(context).size.height * 0.075,
              child: Padding(
                  padding: EdgeInsets.only(left: 25.0, right: 10.0, top: 2.0),
                  child: new Row(
                    mainAxisSize: MainAxisSize.max,
                    children: <Widget>[
                      new Flexible(
                        child: datetimeform = DateTimeFormField(
                          mode: DateTimeFieldPickerMode.date,
                          autovalidateMode: AutovalidateMode.always,
                          decoration: const InputDecoration(hintText: "Fecha Preferencial"),
                          onDateSelected: (value) => {fechaPreferencial = value, selectedDate = true, dateSelected()},
                        ),
                      ),
                      Container(
                        height: 40,
                        width: 150,
                        decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                        child: TextButton(
                          onPressed: agendar(),
                          child: Text(
                            'Agendar',
                            style: TextStyle(color: Colors.white, fontSize: 25),
                          ),
                        ),
                      ),
                    ],
                  )),
            ),
          ),
        ],
      ),
    );
  }

  Function agendar() {
    if (enableButton) {
      return () {
        client.agendarUsuario(fechaPreferencial, selectedPuesto.id, usuario.id, planvacunacion.id);
        Navigator.of(context).pop();
      };
    } else {
      return null;
    }
  }
}
