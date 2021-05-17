import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Localidad {
  int id;
  String nombre;

  Localidad({this.id, this.nombre});

  Localidad.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
}
