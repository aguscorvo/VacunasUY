import 'dart:convert';

import 'package:vacunas_uy/objects/Sector.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';

class PlanVacunacion {
  int id;
  int edadMinima;
  int edadMaxima;
  DateTime fechaInicio;
  DateTime fechaFin;
  Vacuna vacuna;
  List<Sector> sectores;

  PlanVacunacion({this.id});

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
        'id': id ?? "",
        'edadMinima': edadMinima ?? "",
        'edadMaxima': edadMaxima ?? "",
        'fechaInicio': fechaInicio.toString() ?? "",
        'fechaFin': fechaFin.toString() ?? "",
        'vacuna': vacuna != null ? jsonEncode(vacuna) : null,
        'sectores': sectores.length > 0 ? jsonEncode(sectores) : null,
      };

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
