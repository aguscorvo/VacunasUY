import 'dart:convert';
import 'Sector.dart';
import 'Rol.dart';

class Usuario {
  int id = -1;
  String documento = "";
  String nombre = "";
  String apellido = "";
  String correo = "";
  DateTime fechaNacimiento = DateTime.now();
  List<Rol> roles = [];
  Sector sectorLaboral = Sector();

  Usuario();
  Usuario.all(int id, String documento, String nombre, String apellido, String correo, DateTime fechaNacimiento, List<Rol> roles, Sector sectorLaboral) {
    this.id = id;
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.fechaNacimiento = fechaNacimiento;
    this.roles = roles;
    this.sectorLaboral = sectorLaboral;
  }
  Usuario.partial(int id, String nombre, String apellido, String correo, String documento, DateTime fechaNacimiento, List<Rol> roles) {
    this.id = id;
    this.documento = documento;
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.fechaNacimiento = fechaNacimiento;
    this.roles = roles;
    this.sectorLaboral = Sector.all(7, "No tiene");
  }

  Usuario.fromJson(Map<String, dynamic> json) {
    id = json['id'] != null ? json['id'] : "";
    documento = json['documento'] != null ? json['documento'] : "";
    nombre = json['nombre'] != null ? json['nombre'] : "";
    apellido = json['apellido'] != null ? json['apellido'] : "";
    correo = json['correo'] != null ? json['correo'] : "";
    fechaNacimiento = json['fechaNacimiento'] != null
        ? json['fechaNacimiento'] != ""
            ? DateTime.parse(json['fechaNacimiento'])
            : DateTime.now()
        : DateTime.now();
    roles = [];
    if (json['roles'] != null) {
      json['roles'].forEach((rol) => {roles.add(Rol.fromJson(rol))});
    }
    if (json['sectorLaboral'] != null) {
      sectorLaboral = Sector.fromJson(json['sectorLaboral']);
    } else {
      sectorLaboral = Sector.all(7, "No tiene");
    }
  }

  Map<String, dynamic> toJson() {
    Map<String, dynamic> toReturn = {};
    try {
      Map<String, dynamic> toReturn = {
        'id': id,
        'documento': documento,
        'nombre': nombre,
        'apellido': apellido,
        'correo': correo,
        'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()),
        'roles': roles.length != 0 ? jsonEncode(roles) : "[]",
        'sectorLaboral': jsonEncode(sectorLaboral),
      };
      return toReturn;
    } catch (err) {
      return toReturn;
    }
  }

  Map<String, dynamic> toNestedJson() => {
        "Usuario": {
          'id': id,
          'documento': documento,
          'nombre': nombre,
          'apellido': apellido,
          'correo': correo,
          'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()),
          'roles': jsonEncode(roles),
          'sectorLaboral': jsonEncode(sectorLaboral),
        },
      };

  Map<String, dynamic> toNestedValidatedJson(String pass) => {
        'id': id,
        'documento': documento,
        'nombre': nombre,
        'apellido': apellido,
        'correo': correo,
        'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()),
        'password': pass,
        'roles': jsonEncode(roles),
        'sectorLaboral': jsonEncode(sectorLaboral),
      };

  Map<String, dynamic> toUpdateUserJson() => {
        'documento': documento,
        'nombre': nombre,
        'apellido': apellido,
        'correo': correo,
        'fechaNacimiento': (fechaNacimiento.year.toString() + "-" + fechaNacimiento.month.toString() + "-" + fechaNacimiento.day.toString()),
        'roles': jsonEncode(roles),
        'sectorLaboral': sectorLaboral.id,
      };

  List<int> rolesLongArray() {
    List<int> rolesInt = [];

    roles.forEach((element) {
      rolesInt.add(element.id);
    });

    return rolesInt;
  }
}
