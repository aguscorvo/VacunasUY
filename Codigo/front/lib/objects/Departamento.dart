import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'Localidad.dart';

class Departamento {
  int id;
  String nombre;
  List<Localidad> localidades;

  Departamento({this.id, this.nombre, this.localidades});

  Departamento.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    localidades = [];
    json['localidades'].forEach((localidad) => {localidades.add(Localidad.fromJson(localidad))});
  }

  Departamento.fromJsonShort(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    localidades = null;
  }
}
