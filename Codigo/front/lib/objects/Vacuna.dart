import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'Enfermedad.dart';

class Vacuna {
  int id;
  String nombre;
  int cantDosis;
  int periodo;
  int inmunidad;
  Enfermedad enfermedad;

  Vacuna({this.id, this.nombre, this.cantDosis, this.enfermedad, this.inmunidad, this.periodo});

  Vacuna.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    cantDosis = json['cant_dosis'];
    periodo = json['periodo'];
    inmunidad = json['inmunidad'];
    enfermedad = Enfermedad.fromJson(json['enfermedad']);
  }
}
