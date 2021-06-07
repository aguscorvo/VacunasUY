import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/subPaginas/VacunatorioForm.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class VacunatorioCard extends StatelessWidget {
  final Vacunatorio vacunatorio;
  final Row body;
  final Color color;
  const VacunatorioCard({
    Key key,
    this.vacunatorio,
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
                return VacunatorioForm(
                  vacunatorio: vacunatorio,
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
            leading: Icon(Icons.apartment_sharp),
            title: Text("Nombre: " + vacunatorio.nombre),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text("Direccion: " + vacunatorio.direccion),
                Text("Localidad: " + vacunatorio.localidad.nombre),
                Text("Departamento: " + vacunatorio.departamento.nombre),
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
}
