import 'dart:convert';
import 'dart:io';

import 'package:VacunasUY/objects/Enfermedad.dart';
import 'package:VacunasUY/objects/GubUY.dart';
import 'package:VacunasUY/objects/Localidad.dart';
import 'package:VacunasUY/objects/Proveedor.dart';
import 'package:VacunasUY/objects/Puesto.dart';
import 'package:VacunasUY/objects/Usuario.dart';
import 'package:VacunasUY/objects/Vacunatorio.dart';
import 'package:VacunasUY/objects/Departamento.dart';
import 'package:http/http.dart' as http;
import 'package:VacunasUY/tools/UserCredentials.dart';

const String baseUrl = "https://vacunasuy.web.elasticloud.uy/rest";
//const String baseUrl = "http://localhost:8080/rest";

var authHeader = {HttpHeaders.authorizationHeader: "Bearer ${storedUserCredentials.getToken()}", "Content-Type": "application/json"};

class BackendConnection {
  static final BackendConnection _singleton = BackendConnection._internal();

  factory BackendConnection() {
    return _singleton;
  }

  BackendConnection._internal();

  static setAuthHeader() {
    authHeader = {HttpHeaders.authorizationHeader: "Bearer ${storedUserCredentials.getToken()}", "Content-Type": "application/json"};
  }

  Future<bool> login({String correo, String password}) async {
    var response = await http.post(
      '$baseUrl/usuarios/loginbackoffice',
      body: jsonEncode(<String, String>{
        "correo": correo,
        "password": password,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    );

    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      print(json.toString());
      if (json["mensaje"] == "Inicio de sesión correcto.") {
        storedUserCredentials = emptyUser;
        storedUserCredentials.setToken(json["cuerpo"]["token"]);
        storedUserCredentials.setName(json["cuerpo"]["nombre"]);
        storedUserCredentials.userData = Usuario.fromJson(json["cuerpo"] ?? "");
        return true;
      } else if (json["mensaje"] == "Usuario o contraseña incorrectos.") {
        return false;
      }
      saveUserCredentials();
      setAuthHeader();
    }
    return false;
  }

  Future<bool> recoverPassword({String email}) async {
    var response = await http.post(
      '$baseUrl/usuarios/recuperarContra/$email',
      headers: {"Content-Type": "application/json"},
    );
    return response.statusCode == 200;
  }

  //USUARIOS
  Future<List<Usuario>> getUsuarios() async {
    var response = await http.get(
      '$baseUrl/usuarios',
      headers: authHeader,
    );

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Usuario> contentList = new List<Usuario>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Usuario.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return new List<Usuario>();
  }

  //VACUNATORIOS
  Future<List<Vacunatorio>> getVacunatorios() async {
    var response = await http.get(
      '$baseUrl/vacunatorios',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Vacunatorio> contentList = new List<Vacunatorio>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Vacunatorio.fromJson(jsonResponse[i]));
      }
      return contentList;
    } else {
      return new List<Vacunatorio>();
    }
  }

  Future<List<Puesto>> getPuestosDeVacunatorio({int idVacunatorio}) async {
    var response = await http.get(
      '$baseUrl/vacunatorios/$idVacunatorio',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"]["puestos"];

      List<Puesto> contentList = new List<Puesto>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Puesto.fromJson(jsonResponse[i]));
      }
      print(contentList.toString());
      return contentList;
    }
    return new List<Puesto>();
  }

  //DEPARTAMENTOS
  Future<List<Departamento>> getDepartamentos() async {
    var response = await http.get('$baseUrl/departamentos');
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Departamento> contentList = new List<Departamento>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Departamento.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return new List<Departamento>();
  }

  //LOCALIDADES
  Future<List<Localidad>> getLocalidades() async {
    var response = await http.get(
      '$baseUrl/localidades',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Localidad> contentList = new List<Localidad>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Localidad.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return new List<Localidad>();
  }

  //ENFERMEDADES
  Future<List<Enfermedad>> getEnfermedades() async {
    var response = await http.get(
      '$baseUrl/enfermedades',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Enfermedad> contentList = new List<Enfermedad>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Enfermedad.fromJson(jsonResponse[i]));
      }
      print(contentList.toString());
      return contentList;
    }
    return new List<Enfermedad>();
  }

  //PROVEEDIORES
  Future<List<Proveedor>> getProveedores() async {
    var response = await http.get(
      '$baseUrl/proveedores',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Proveedor> contentList = new List<Proveedor>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Proveedor.fromJson(jsonResponse[i]));
      }
      print(contentList.toString());
      return contentList;
    }
    return new List<Proveedor>();
  }

  //PROVEEDIORES
  Future<List<Proveedor>> getVacunas() async {
    var response = await http.get(
      '$baseUrl/proveedores',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Proveedor> contentList = new List<Proveedor>();
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Proveedor.fromJson(jsonResponse[i]));
      }
      print(contentList.toString());
      return contentList;
    }
    return new List<Proveedor>();
  }

  void exitoLoginGubUY(GubUY gubAuth) async {
    String url = '$baseUrl/autenticaciongubuy/procesarTokens?code=' + gubAuth.code + '&state=' + gubAuth.state;
    var response = await http.get(url);

    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      if (json["mensaje"] == "Usuario logueado con éxito.") {
        storedUserCredentials = emptyUser;
        storedUserCredentials.setToken(json["cuerpo"]["token"]);
        storedUserCredentials.setName(json["cuerpo"]["nombre"]);
        storedUserCredentials.userData = Usuario.fromJson(json["cuerpo"] ?? "");
      }
      saveUserCredentials();
      setAuthHeader();
    }
  }
}
