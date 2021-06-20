import 'Localidad.dart';

class Departamento {
  int id = -1;
  String nombre = "";
  List<Localidad> localidades = [];

  Departamento();
  Departamento.all(int id, String nombre, List<Localidad> localidades) {
    this.id = id;
    this.nombre = nombre;
    this.localidades = localidades;
  }

  Departamento.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    localidades = [];
    json['localidades'].forEach((localidad) => {localidades.add(Localidad.fromJson(localidad))});
  }

  Departamento.fromJsonShort(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    localidades = [];
  }
  Map<String, dynamic> toJson() => {
        'id': id,
        'nombre': nombre,
      };
}
