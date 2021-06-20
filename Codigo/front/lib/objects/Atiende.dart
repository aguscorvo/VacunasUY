import 'Puesto.dart';

class Atiende {
  DateTime fecha = DateTime.now();
  Puesto puesto = Puesto();

  Atiende();

  Atiende.fromJson(Map<String, dynamic> json) {
    fecha = calculateDate(json['fecha']);
    if (json['puesto'] != null) {
      puesto = Puesto.fromJson(json['puesto']);
    } else {
      puesto = Puesto();
    }
  }

  DateTime calculateDate(String date) {
    DateTime toReturn = DateTime.parse(date);
    return toReturn;
  }
}
