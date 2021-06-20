import 'Enfermedad.dart';

class Vacuna {
  int id = -1;
  String nombre = "";
  int cantDosis = -1;
  int periodo = -1;
  int inmunidad = -1;
  Enfermedad enfermedad = Enfermedad();

  Vacuna();

  Vacuna.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    cantDosis = json['cant_dosis'];
    periodo = json['periodo'];
    inmunidad = json['inmunidad'];
    enfermedad = Enfermedad.fromJson(json['enfermedad']);
  }
}
