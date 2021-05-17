import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Enfermedad {
  int id;
  String nombre;

  Enfermedad({this.id, this.nombre});

  Enfermedad.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
}
