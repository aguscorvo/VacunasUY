import 'Puesto.dart';

class Atiende {
  DateTime fecha;
  Puesto puesto;

  Atiende({this.fecha, this.puesto});

  Atiende.fromJson(Map<String, dynamic> json) {
    fecha = calculateDate(json['fecha']);
    if (json['puesto'] != null) {
      puesto = Puesto.fromJson(json['puesto']);
    } else {
      puesto = null;
    }
  }

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
