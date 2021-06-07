import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/subPaginas/PlanVacunacionForm.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class PlanVacCard extends StatelessWidget {
  final PlanVacunacion planvacun;
  final Row body;
  final Color color;
  const PlanVacCard({
    Key key,
    this.planvacun,
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
          Text('Informacion'),
        ]),
        onPressed: () {
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return PlanVacunacionForm(
                planvacunacion: planvacun,
                color: color,
                tipoForm: "Informacion",
              );
            },
          );
        },
      ),
    );
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
                return PlanVacunacionForm(
                  planvacunacion: planvacun,
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
    if (isUserCiudadano()) {
      buttons.addAll([
        TextButton(
          child: Row(children: [
            Icon(Icons.note_add),
            Text('Agendarme'),
          ]),
          onPressed: () {
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return PlanVacunacionForm(
                  planvacunacion: planvacun,
                  usuario: storedUserCredentials.userData,
                  color: color,
                  tipoForm: "Agendar",
                );
              },
            );
          },
        ),
      ]);
    }

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
            title: Text("Nombre: " + planvacun.vacuna.nombre),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Text("Período: "),
                Text("Del: " + formatDate(planvacun.fechaInicio) + " al " + formatDate(planvacun.fechaFin)),
                //Text("Coordenadas: " + vacunatorio.latitud.toString() + ", " + vacunatorio.longitud.toString()),
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
