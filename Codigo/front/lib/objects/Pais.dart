import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Pais {
  int id;
  String nombre;

  Pais({this.id, this.nombre});

  Pais.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
}
