import 'dart:convert';
import 'PlanVacunacion.dart';
import 'Puesto.dart';

class Agenda {
  int id = -1;
  DateTime fecha = DateTime.now();
  Puesto puesto = Puesto();
  PlanVacunacion planVacunacion = PlanVacunacion();

  Agenda();

  Agenda.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    fecha = calculateDate(json['fecha']);
    if (json['puesto'] != null) {
      puesto = Puesto.fromJson(json['puesto']);
    } else {
      puesto = Puesto();
    }
    if (json['planVacunacion'] != null) {
      planVacunacion = PlanVacunacion.fromJson(json['planVacunacion']);
    } else {
      planVacunacion = PlanVacunacion();
    }
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'fecha': fecha.toString(),
        'puesto': jsonEncode(puesto),
        'planVacunacion': jsonEncode(planVacunacion),
      };

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
