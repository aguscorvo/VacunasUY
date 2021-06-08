import 'dart:convert';
import 'Sector.dart';
import 'Rol.dart';

class Usuario {
  int id;
  String documento;
  String nombre;
  String apellido;
  String correo;
  DateTime fechaNacimiento;
  List<Rol> roles;
  Sector sectorLaboral;

  Usuario({this.documento, this.nombre, this.apellido, this.id, this.correo, this.fechaNacimiento, this.roles, this.sectorLaboral});

  Usuario.fromJson(Map<String, dynamic> json) {
    id = json['id'] != null ? json['id'] : "";
    documento = json['documento'] != null ? json['documento'] : "";
    nombre = json['nombre'];
    apellido = json['apellido'];
    correo = json['correo'];
    fechaNacimiento = DateTime.parse(json['fechaNacimiento']);
    roles = [];
    if (json['roles'] != null) {
      json['roles'].forEach((rol) => {roles.add(Rol.fromJson(rol))});
    }
    if (json['sectorLaboral'] != null) {
      sectorLaboral = Sector.fromJson(json['sectorLaboral']);
    } else {
      sectorLaboral = Sector(id: 7, nombre: "No tiene");
    }
  }

  Map<String, dynamic> toJson() {
    try {
      Map<String, dynamic> toReturn = {
        'id': id ?? "",
        'documento': documento ?? "",
        'nombre': nombre ?? "",
        'apellido': apellido ?? "",
        'correo': correo ?? "",
        'fechaNacimiento': fechaNacimiento != null ? (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()) : "",
        'roles': roles != null ? jsonEncode(roles) : "[]",
        'sectorLaboral': sectorLaboral != null ? jsonEncode(sectorLaboral) : Sector(id: 6, nombre: "Otra ocupación"),
      };
      return toReturn;
    } catch (err) {}
  }

  Map<String, dynamic> toNestedJson() => {
        "Usuario": {
          'id': id ?? "",
          'documento': documento ?? "",
          'nombre': nombre ?? "",
          'apellido': apellido ?? "",
          'correo': correo ?? "",
          'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()) ?? "",
          'roles': jsonEncode(roles) ?? "",
          'sectorLaboral': jsonEncode(sectorLaboral) ?? Sector(id: 6, nombre: "Otra ocupación"),
        },
      };

  Map<String, dynamic> toNestedValidatedJson(String pass) => {
        'id': id ?? "",
        'documento': documento ?? "",
        'nombre': nombre ?? "",
        'apellido': apellido ?? "",
        'correo': correo ?? "",
        'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()) ?? "",
        'password': pass ?? "",
        'roles': jsonEncode(roles) ?? "",
        'sectorLaboral': jsonEncode(sectorLaboral) ?? Sector(id: 6, nombre: "Otra ocupación"),
      };

  Map<String, dynamic> toUpdateUserJson() => {
        'documento': documento ?? "",
        'nombre': nombre ?? "",
        'apellido': apellido ?? "",
        'correo': correo ?? "",
        'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()) ?? "",
        'roles': jsonEncode(roles) ?? "",
        'sectorLaboral': sectorLaboral == null ? 6 : sectorLaboral.id,
      };

  List<int> rolesLongArray() {
    List<int> rolesInt = [];

    roles.forEach((element) {
      rolesInt.add(element.id);
    });

    return rolesInt;
  }
}
