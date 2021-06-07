import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:flutter/material.dart';

class VacunatorioForm extends StatelessWidget {
  final Vacunatorio vacunatorio;
  final Color color;
  final String tipoForm; //Si es Editar o si es Alta
  const VacunatorioForm({
    Key key,
    this.vacunatorio,
    this.color = Colors.blue,
    this.tipoForm = "",
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
}
