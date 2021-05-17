import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'Agenda.dart';

class Puesto {
  int id;
  String nombre;
  List<Agenda> agenda;

  Puesto({this.id, this.nombre, this.agenda});

  Puesto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    agenda = [];
    json['agendas'].forEach((localidad) => {agenda.add(Agenda.fromJson(localidad))});
  }
}
