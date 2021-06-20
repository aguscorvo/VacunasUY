import 'dart:convert';

import 'Localidad.dart';
import 'Departamento.dart';
import 'Puesto.dart';

class Vacunatorio {
  int id = -1;
  String nombre = "";
  double latitud = 0.0;
  double longitud = 0.0;
  String direccion = "";
  Localidad localidad = Localidad();
  Departamento departamento = Departamento();
  List<Puesto> puestos = [];

  Vacunatorio();

  Vacunatorio.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    latitud = json['latitud'];
    longitud = json['longitud'];
    direccion = json['direccion'];
    localidad = Localidad.fromJson(json['localidad']);
    departamento = Departamento.fromJsonShort(json['departamento']);

    puestos = [];
    if (json['puestos'] != null) {
      json['puestos'].forEach((puesto) => {puestos.add(Puesto.fromJson(puesto))});
    }
  }
  Map<String, dynamic> toJson() => {
        'id': id,
        'nombre': nombre,
        'latitud': latitud,
        'longitud': longitud,
        'direccion': direccion,
        'localidad': jsonEncode(localidad),
        'departamento': jsonEncode(departamento),
        'puestos': puestos.length > 0 ? jsonEncode(puestos) : [],
      };
}
