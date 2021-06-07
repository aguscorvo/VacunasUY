import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';

class AgendaForm extends StatelessWidget {
  final Agenda agenda;
  final Usuario usuario;
  final Color color;
  final String tipoForm; //Si es Editar o si es Alta
  const AgendaForm({
    Key key,
    this.agenda,
    this.usuario,
    this.color = Colors.blue,
    this.tipoForm = "",
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (tipoForm == "Eliminar") {
      return eliminar(context);
    } else if (tipoForm == "Informacion") {
      return informacion(context);
    } else {
      return null;
    }
  }

  AlertDialog eliminar(BuildContext context) {
    BackendConnection client = BackendConnection();
    return AlertDialog(
      title: Text('Alerta!!!'),
      content: Text("Esta seguro que desea eleminar esta Agenda? (Irreversible)"),
      actions: <Widget>[
        TextButton(
          child: Text("Si, estoy seguro!"),
          onPressed: () {
            client.borrarAgenda(usuario.id, agenda.id);
            Navigator.of(context).pop();
          },
        ),
        TextButton(
          child: Text("Cancelar"),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ],
    );
  }

  AlertDialog informacion(BuildContext context) {
    List<Widget> sectores = [];
    List<Widget> enfermedades = [];

    agenda.planVacunacion.sectores.forEach((sector) {
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
        child: Text(agenda.planVacunacion.vacuna.enfermedad.nombre),
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
                  child: Text(agenda.planVacunacion.vacuna.nombre),
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
                            Text(agenda.planVacunacion.edadMinima.toString()),
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
                            Text(agenda.planVacunacion.edadMaxima.toString()),
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
                            Text(formatDate(agenda.planVacunacion.fechaInicio)),
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
                            Text(formatDate(agenda.planVacunacion.fechaFin)),
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
                            Text(agenda.planVacunacion.vacuna.nombre),
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
                            Text(agenda.planVacunacion.vacuna.cantDosis.toString()),
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
                            Text(agenda.planVacunacion.vacuna.periodo.toString()),
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
                            Text(agenda.planVacunacion.vacuna.inmunidad.toString()),
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
