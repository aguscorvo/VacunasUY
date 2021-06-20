import 'dart:convert';

import 'package:vacunas_uy/objects/Sector.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';

class PlanVacunacion {
  int id = -1;
  int edadMinima = -1;
  int edadMaxima = -1;
  DateTime fechaInicio = DateTime.now();
  DateTime fechaFin = DateTime.now();
  Vacuna vacuna = Vacuna();
  List<Sector> sectores = [];

  PlanVacunacion();

  PlanVacunacion.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    edadMinima = json['edadMinima'];
    edadMaxima = json['edadMaxima'];

    fechaInicio = calculateDate(json['fechaInicio']);
    fechaFin = calculateDate(json['fechaFin']);

    vacuna = Vacuna.fromJson(json['vacuna']);
    sectores = [];
    json['sectores'].forEach((sector) => {sectores.add(Sector.fromJson(sector))});
  }

  PlanVacunacion.fromJsonShort(Map<String, dynamic> json) {
    id = json['id'];
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'edadMinima': edadMinima,
        'edadMaxima': edadMaxima,
        'fechaInicio': fechaInicio.toString(),
        'fechaFin': fechaFin.toString(),
        'vacuna': jsonEncode(vacuna),
        'sectores': sectores.length > 0 ? jsonEncode(sectores) : [],
      };

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
