import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import 'Rol.dart';

class Usuario {
  int id;
  String documento;
  String nombre;
  String apellido;
  String correo;
  String fechaNacimiento;
  List<Rol> roles;

  Usuario({this.documento, this.nombre, this.apellido, this.id, this.correo, this.fechaNacimiento, this.roles});

  Usuario.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    documento = json['documento'] != null ? json['documento'] : "";
    nombre = json['nombre'];
    apellido = json['apellido'];
    correo = json['correo'];
    fechaNacimiento = json['fechaNacimiento'];
    roles = [];
    json['roles'].forEach((rol) => {roles.add(Rol.fromJson(rol))});
  }

  Map<String, dynamic> toJson() => {'documento': documento ?? "", 'nombre': nombre ?? "", 'apellido': apellido ?? "", 'correo': correo ?? "", 'fechaNacimiento': fechaNacimiento ?? ""};
  Map<String, dynamic> toNestedJson() => {
        "Usuario": {'documento': documento ?? "", 'nombre': nombre ?? "", 'apellido': apellido ?? "", 'correo': correo ?? "", 'fechaNacimiento': fechaNacimiento ?? ""},
      };

  Map<String, dynamic> toNestedValidatedJson(String pass) => {
        'documento': documento ?? "",
        'nombre': nombre ?? "",
        'apellido': apellido ?? "",
        'correo': correo ?? "",
        'fechaNacimiento': fechaNacimiento ?? "",
        'password': pass ?? "",
      };
}
