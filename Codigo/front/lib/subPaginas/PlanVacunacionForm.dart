import 'package:date_field/date_field.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Puesto.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class PlanVacunacionForm extends StatelessWidget {
  final PlanVacunacion? planvacunacion;
  final Usuario? usuario;
  final Color? color;
  final String? tipoForm; //Si es Editar o si es Alta
  const PlanVacunacionForm({
    this.planvacunacion,
    this.usuario,
    this.color = Colors.blue,
    this.tipoForm = "",
  });

  @override
  Widget build(BuildContext context) {
    if (tipoForm == "Editar") {
      return editar(context);
    } else if (tipoForm == "Agendar") {
      return agendar(context);
    } else if (tipoForm == "Información") {
      return informacion(context);
    } else {
      return Container(
        child: Center(
          child: Text("Hubo un error"),
        ),
      );
    }
  }

  AlertDialog editar(BuildContext context) {
    var nombreController = new TextEditingController();
    return AlertDialog(
      title: Text("Editar vacunatorio"),
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
            child: Text("Editar vacunatorio"),
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
                hintText: 'Ingresar nombre del vacunatorio',
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

    planvacunacion!.sectores.forEach((sector) {
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
        child: Text(planvacunacion!.vacuna.enfermedad.nombre),
      ),
    );

    return AlertDialog(
      title: Center(child: Text("Información")),
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
            child: Center(child: Text("Información")),
          ),
          Container(
            width: MediaQuery.of(context).size.width < 1900 ? MediaQuery.of(context).size.width * 0.7 : MediaQuery.of(context).size.width * 0.5,
            height: MediaQuery.of(context).size.height < 900 ? MediaQuery.of(context).size.height * 0.7 : MediaQuery.of(context).size.height * 0.5,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: Colors.grey[350],
            ),
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: Column(
              children: [
                Expanded(
                  flex: 5,
                  child: Container(
                    child: Text(planvacunacion!.vacuna.nombre),
                  ),
                ),
                Expanded(
                  flex: 95,
                  child: Container(
                    width: MediaQuery.of(context).size.width * 0.45,
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
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    child: Column(
                                      children: [
                                        Container(
                                          width: MediaQuery.of(context).size.width * 0.2,
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
                                          width: MediaQuery.of(context).size.width * 0.2,
                                          padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                          child: Center(
                                            child: Text(planvacunacion!.edadMinima.toString()),
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
                                      children: [
                                        Container(
                                          width: MediaQuery.of(context).size.width * 0.2,
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
                                          width: MediaQuery.of(context).size.width * 0.2,
                                          padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                          child: Center(
                                            child: Text(planvacunacion!.edadMaxima.toString()),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(height: 10),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Material(
                                  elevation: 10,
                                  child: Container(
                                    width: MediaQuery.of(context).size.width * 0.2,
                                    child: Column(
                                      children: [
                                        Container(
                                          width: MediaQuery.of(context).size.width * 0.2,
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
                                          width: MediaQuery.of(context).size.width * 0.2,
                                          padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                          child: Center(
                                            child: Text(formatDate(planvacunacion!.fechaInicio)),
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
                                      children: [
                                        Container(
                                          width: MediaQuery.of(context).size.width * 0.2,
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
                                          width: MediaQuery.of(context).size.width * 0.2,
                                          padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                          child: Center(
                                            child: Text(formatDate(planvacunacion!.fechaFin)),
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
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
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
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.45,
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
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.2,
                                child: Column(
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
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
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                      child: Center(
                                        child: Text(planvacunacion!.vacuna.nombre),
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
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
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
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                      child: Center(
                                        child: Text(planvacunacion!.vacuna.cantDosis.toString()),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width * 0.2,
                                child: Column(
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
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
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                      child: Center(
                                        child: Text(planvacunacion!.vacuna.periodo.toString()),
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
                                  children: [
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
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
                                      width: MediaQuery.of(context).size.width * 0.2,
                                      padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                                      child: Center(
                                        child: Text(planvacunacion!.vacuna.inmunidad.toString()),
                                      ),
                                    ),
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
                                width: MediaQuery.of(context).size.width * 0.45,
                                child: Column(
                                  children: [
                                    Container(
                                      padding: EdgeInsets.fromLTRB(0, 5, 0, 5),
                                      width: MediaQuery.of(context).size.width * 0.45,
                                      color: Colors.blueAccent,
                                      child: Center(
                                          child: Text(
                                        "Afección",
                                        style: TextStyle(fontWeight: FontWeight.bold),
                                      )),
                                    ),
                                    Container(
                                      width: MediaQuery.of(context).size.width * 0.2,
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

    Vacunatorio? selectedVacunatorio;

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
            width: MediaQuery.of(context).size.width > 1400 ? MediaQuery.of(context).size.width * 0.5 : MediaQuery.of(context).size.width * 0.7,
            height: MediaQuery.of(context).size.height > 900 ? MediaQuery.of(context).size.height * 0.5 : MediaQuery.of(context).size.height * 0.7,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              color: Colors.grey[350],
            ),
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: FutureBuilder(
              future: client.getVacunatoriosDadoPlan(planvacunacion!.id),
              builder: (context, snapshot) {
                if (snapshot.connectionState != ConnectionState.done) {
                  return Center(child: CircularProgressIndicator());
                } else {
                  if (snapshot.data == null) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    List<Vacunatorio> vacunatorios = [];
                    List<Vacunatorio> vacunatoriosTemp = snapshot.data as List<Vacunatorio>;
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
                          child: Text(planvacunacion!.vacuna.nombre),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Material(
                              elevation: 10,
                              child: Container(
                                width: MediaQuery.of(context).size.width > 1400 ? MediaQuery.of(context).size.width * 0.2 : MediaQuery.of(context).size.width * 0.3,
                                height: MediaQuery.of(context).size.height > 900 ? MediaQuery.of(context).size.height * 0.4 : MediaQuery.of(context).size.height * 0.6,
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
                                planvacunacion: planvacunacion!,
                                usuario: usuario!,
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
  final Vacunatorio? selectedVacunatorio;
  final PlanVacunacion? planvacunacion;
  final Usuario? usuario;
  const PuestosList({
    this.selectedVacunatorio,
    this.planvacunacion,
    this.usuario,
  });
  static late _PuestosListState state;
  @override
  _PuestosListState createState() => state = _PuestosListState(selectedVacunatorio, planvacunacion, usuario);
}

class _PuestosListState extends State<PuestosList> {
  late Vacunatorio? selectedVacunatorio;
  late PlanVacunacion? planvacunacion;
  late Usuario? usuario;
  BackendConnection client = BackendConnection();

  late Puesto? selectedPuesto;
  late DateTimeFormField datetimeform;
  late DateTime fechaPreferencial;

  bool selectedDate = false;

  bool enableButton = true;

  List<InkWell> puestosInkWell = [];

  _PuestosListState(Vacunatorio? selectedVacunatorio, PlanVacunacion? planvacunacion, Usuario? usuario) {
    this.selectedVacunatorio = selectedVacunatorio;
    this.planvacunacion = planvacunacion;
    this.usuario = usuario;
  }

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
      selectedVacunatorio!.puestos.forEach((puesto) {
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
      width: MediaQuery.of(context).size.width > 1400 ? MediaQuery.of(context).size.width * 0.2 : MediaQuery.of(context).size.width * 0.3,
      height: MediaQuery.of(context).size.height > 900 ? MediaQuery.of(context).size.height * 0.4 : MediaQuery.of(context).size.height * 0.6,
      child: Column(
        children: [
          Material(
            elevation: 10,
            child: Container(
              width: MediaQuery.of(context).size.width > 1400 ? MediaQuery.of(context).size.width * 0.2 : MediaQuery.of(context).size.width * 0.3,
              height: MediaQuery.of(context).size.height > 900 ? MediaQuery.of(context).size.height * 0.325 : MediaQuery.of(context).size.height * 0.4875,
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
              width: MediaQuery.of(context).size.width > 1400 ? MediaQuery.of(context).size.width * 0.2 : MediaQuery.of(context).size.width * 0.3,
              height: MediaQuery.of(context).size.height > 900 ? MediaQuery.of(context).size.height * 0.075 : MediaQuery.of(context).size.height * 0.1125,
              child: Padding(
                  padding: EdgeInsets.only(left: 25.0, right: 10.0, top: 2.0),
                  child: new Row(
                    mainAxisSize: MainAxisSize.max,
                    children: <Widget>[
                      new Flexible(
                        child: datetimeform = DateTimeFormField(
                          mode: DateTimeFieldPickerMode.date,
                          autovalidateMode: AutovalidateMode.always,
                          validator: (DateTime? dateTime) {
                            if (dateTime == null) {
                              return "Fecha requerida";
                            } else if (dateTime.isBefore(DateTime(DateTime.now().year, DateTime.now().month, DateTime.now().day + 1))) {
                              return "No puede agendarse para el día de hoy.";
                            } else if (dateTime.isAfter(planvacunacion!.fechaFin)) {
                              return "No puede agendarse para después del cierre del plan.";
                            }
                            return null;
                          },
                          decoration: const InputDecoration(hintText: "Fecha preferencial"),
                          onDateSelected: (value) => {
                            fechaPreferencial = value,
                            selectedDate = true,
                            dateSelected(),
                          },
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

  void Function()? agendar() {
    if (enableButton) {
      return () {
        client.agendarUsuario(fechaPreferencial, selectedPuesto!.id, usuario!.id, planvacunacion!.id);
        Navigator.of(context).pop();
      };
    } else {
      return null;
    }
  }
}
