import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:flutter/material.dart';

class VacunatorioSelected extends StatefulWidget {
  final Vacunatorio vacun;
  final String mode;
  VacunatorioSelected({@required this.vacun, @required this.mode});

  static _VacunatorioSelectedState state;
  @override
  _VacunatorioSelectedState createState() => state = _VacunatorioSelectedState(vacun, mode);
}

class _VacunatorioSelectedState extends State<VacunatorioSelected> {
  _VacunatorioSelectedState(this.vacun, this.mode);

  Vacunatorio vacun;
  String mode;

  void changeVacunatorio(Vacunatorio newVacun) {
    setState(() {
      vacun = newVacun;
    });
  }

  @override
  Widget build(BuildContext context) {
    String nombre = "Seleccione un Vacunatorio";

    if (this.vacun != null) {
      nombre = this.vacun.nombre;
    }

    return Expanded(
      flex: 3,
      child: Container(
        padding: const EdgeInsets.fromLTRB(0.0, 100, 25.0, 25.0),
        child: Column(
          children: [
            Text(nombre),
          ],
        ),
      ),
    );
  }
}
