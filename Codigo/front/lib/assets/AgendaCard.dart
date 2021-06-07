import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/subPaginas/AgendaForm.dart';

class AgendaCard extends StatelessWidget {
  final Agenda agenda;
  final Usuario usuario;
  final Row body;
  final Color color;
  const AgendaCard({
    Key key,
    this.agenda,
    this.usuario,
    this.body,
    this.color = Colors.blue,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Widget> buttons = [];

    buttons.add(
      TextButton(
        child: Row(children: [
          Icon(Icons.info),
          Text('Informacion Vacuna'),
        ]),
        onPressed: () {
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AgendaForm(
                agenda: agenda,
                color: color,
                tipoForm: "Informacion",
              );
            },
          );
        },
      ),
    );
    buttons.add(
      TextButton(
        child: Row(children: [
          Icon(Icons.delete),
          Text('Eliminar'),
        ]),
        onPressed: () {
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return AgendaForm(
                agenda: agenda,
                usuario: usuario,
                color: color,
                tipoForm: "Eliminar",
              );
            },
          );
        },
      ),
    );

    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15.0),
      ),
      elevation: 5,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: <Widget>[
          Container(
            height: 30,
            decoration: BoxDecoration(
              color: Colors.blueAccent,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(8.0),
                topRight: Radius.circular(8.0),
              ),
            ),
          ),
          ListTile(
            leading: Icon(Icons.list_alt),
            title: Text("Fecha y Hora: " + formatDate(agenda.fecha) + " - " + formatTime(agenda.fecha)),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text("Vacuna: " + agenda.planVacunacion.vacuna.nombre),
                Text("Vacunatorio: " + agenda.puesto.vacunatorio.nombre),
                Text("   Departamento: " + agenda.puesto.vacunatorio.departamento.nombre),
                Text("   Direccion: " + agenda.puesto.vacunatorio.direccion),
                Text("Puesto: " + agenda.puesto.numero.toString()),
              ],
            ),
          ),
          Container(
            padding: const EdgeInsets.fromLTRB(0, 0, 20, 0),
            child: Row(
              mainAxisAlignment: buttons.length > 1 ? MainAxisAlignment.spaceEvenly : MainAxisAlignment.end,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: buttons,
            ),
          )
        ],
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
}
