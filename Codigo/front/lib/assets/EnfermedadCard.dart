import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/subPaginas/EnfermedadForm.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class EnfermedadCard extends StatelessWidget {
  final Enfermedad enfermedad;
  final Row body;
  final Color color;
  const EnfermedadCard({
    Key key,
    this.enfermedad,
    this.body,
    this.color = Colors.blue,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Widget> buttons = [];

    if (isUserAdmin()) {
      buttons.addAll([
        TextButton(
          child: Row(children: [
            Icon(Icons.edit),
            Text('Editar'),
          ]),
          onPressed: () {
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return EnfermedadForm(
                  enfermedad: enfermedad,
                  color: color,
                  tipoForm: "Editar",
                );
              },
            );
          },
        ),
        const SizedBox(width: 8),
        TextButton(
          child: Row(children: [
            Icon(Icons.delete),
            Text('Eliminar'),
          ]),
          onPressed: () {
            //Eliminar Vacunatorio
          },
        ),
        const SizedBox(width: 8),
      ]);
    }

    return Card(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(15.0),
      ),
      elevation: 5,
      child: Column(
        mainAxisSize: MainAxisSize.min,
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
            title: Text("Nombre: " + enfermedad.nombre),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                //Text("Período: "),
                //Text("Nombre: " + enfermedad.nombre),
                //Text("Coordenadas: " + vacunatorio.latitud.toString() + ", " + vacunatorio.longitud.toString()),
              ],
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: buttons,
          ),
        ],
      ),
    );
  }

  String formatDate(DateTime date) {
    String toReturn = "";

    if (date.month < 10) {
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
