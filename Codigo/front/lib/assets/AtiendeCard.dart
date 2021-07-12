import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:vacunas_uy/objects/Atiende.dart';
import 'package:vacunas_uy/objects/Usuario.dart';

class AtiendeCard extends StatelessWidget {
  final Atiende? atiende;
  final Usuario? usuario;
  final Row? body;
  final Color? color;
  AtiendeCard({
    this.atiende,
    this.usuario,
    this.body,
    this.color = Colors.blue,
  });

  @override
  Widget build(BuildContext context) {
    //List<Widget> buttons = [];

    MaterialAccentColor mac;
    if (atiende!.fecha.isAfter(DateTime.now())) {
      mac = Colors.blueAccent;
    } else {
      mac = Colors.greenAccent;
    }

    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15.0),
      ),
      elevation: 5,
      child: Row(
        //mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.start,
        children: <Widget>[
          Container(
            height: 130,
            width: 30,
            decoration: BoxDecoration(
              color: mac,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(8.0),
                bottomLeft: Radius.circular(8.0),
                //topRight: Radius.circular(8.0),
              ),
            ),
            child: Icon(Icons.list_alt),
          ),
          Container(
            width: 300,
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(
                children: [
                  Container(
                    padding: const EdgeInsets.fromLTRB(10.0, 5, 5.0, 5.0),
                    height: 130,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text("Fecha y Hora: " + formatDate(atiende!.fecha)),
                        Text("Vacunatorio: " + atiende!.puesto.vacunatorio.nombre),
                        Text("   Departamento: " + atiende!.puesto.vacunatorio.departamento.nombre),
                        Text("   Direccion: " + atiende!.puesto.vacunatorio.direccion),
                        Text("Puesto: " + atiende!.puesto.numero.toString()),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
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
