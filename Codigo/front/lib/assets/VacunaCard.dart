import 'package:vacunas_uy/objects/Vacuna.dart';
import 'package:vacunas_uy/subPaginas/VacunaForm.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';
import 'package:flutter_tabler_icons/flutter_tabler_icons.dart';

class VacunaCard extends StatelessWidget {
  final Vacuna? vacuna;
  final Row? body;
  final Color? color;
  const VacunaCard({
    this.vacuna,
    this.body,
    this.color = Colors.blue,
  });

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
                return VacunaForm(
                  vacuna: vacuna!,
                  color: color!,
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
            leading: Icon(TablerIcons.vaccine),
            title: Text("Nombre: " + vacuna!.nombre),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text("Enfermedad: " + vacuna!.enfermedad.nombre),
                Text("Cantidad Dosis: " + vacuna!.cantDosis.toString()),
                Text("Inmunidad: " + vacuna!.inmunidad.toString()),
                Text("período: " + vacuna!.periodo.toString()),
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
}
