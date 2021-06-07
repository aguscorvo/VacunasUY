import 'PlanVacunacion.dart';
import 'Puesto.dart';

class Agenda {
  int id;
  DateTime fecha;
  Puesto puesto;
  PlanVacunacion planVacunacion;

  Agenda({this.id, this.fecha, this.planVacunacion, this.puesto});

  Agenda.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    fecha = calculateDate(json['fecha']);
    if (json['puesto'] != null) {
      puesto = Puesto.fromJson(json['puesto']);
    } else {
      puesto = null;
    }
    if (json['planVacunacion'] != null) {
      planVacunacion = PlanVacunacion.fromJson(json['planVacunacion']);
    } else {
      planVacunacion = null;
    }
  }

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
