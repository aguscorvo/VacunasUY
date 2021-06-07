import 'dart:convert';
import 'dart:io';

import 'package:vacunas_uy/AppConfig.dart';
import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/objects/GubUY.dart';
import 'package:vacunas_uy/objects/Localidad.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/objects/Proveedor.dart';
import 'package:vacunas_uy/objects/Puesto.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/objects/Departamento.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:http/http.dart' as http;
import 'package:vacunas_uy/tools/UserCredentials.dart';

const String baseUrl = AppConfig.componenteCentalURL;

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
      if (json["mensaje"] == "Inicio de sesión correcto.") {
        storedUserCredentials = emptyUser;
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

      if (jsonResponse.length > 0) {
        List<Usuario> contentList = [];
        for (var i = 0; i < jsonResponse.length; i++) {
          contentList.add(Usuario.fromJson(jsonResponse[i]));
        }
        return contentList;
      }
    }

    return [];
  }

  Future<bool> actualizarDatosUsuario(Usuario u) async {
    if (u == null) {
      return false;
    } else {
      var editBody = jsonEncode(<String, dynamic>{
        "documento": u.documento,
        "nombre": u.nombre,
        "apellido": u.apellido,
        "correo": u.correo,
        "fechaNacimiento": u.fechaNacimiento.toString().split(" ")[0],
        "password": "",
        "roles": u.rolesLongArray(),
        "sectorLaboral": u.sectorLaboral != null ? u.sectorLaboral.id : 6,
      });

      var response = await http.put(
        '$baseUrl/usuarios/editar/${u.id}',
        headers: authHeader,
        body: editBody,
      );

      if (response.statusCode == 200) {
        Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
        if (json["mensaje"] == "Usuario editado con éxito." && storedUserCredentials.userData.id == u.id) {
          storedUserCredentials.userData = Usuario.fromJson(json["cuerpo"] ?? "");

          saveUserCredentials();
          setAuthHeader();
        }
      } else {
        print(response.statusCode);
        print(response.body);
      }
      return response.statusCode == 200;
    }
  }

  //VACUNATORIOS
  Future<List<Vacunatorio>> getVacunatorios() async {
    var response = await http.get(
      '$baseUrl/vacunatorios',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Vacunatorio> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Vacunatorio.fromJson(jsonResponse[i]));
      }
      return contentList;
    } else {
      return [];
    }
  }

  Future<List<Vacunatorio>> getVacunatoriosDadoPlan(int id) async {
    var response = await http.get(
      '$baseUrl/vacunatorios/listarVacunatoriosDadoPlan/$id',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Vacunatorio> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Vacunatorio.fromJson(jsonResponse[i]));
      }
      return contentList;
    } else {
      return [];
    }
  }

  Future<List<Puesto>> getPuestosDeVacunatorio({int idVacunatorio}) async {
    var response = await http.get(
      '$baseUrl/vacunatorios/$idVacunatorio',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"]["puestos"];

      List<Puesto> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Puesto.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //DEPARTAMENTOS
  Future<List<Departamento>> getDepartamentos() async {
    var response = await http.get('$baseUrl/departamentos');
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Departamento> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Departamento.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //LOCALIDADES
  Future<List<Localidad>> getLocalidades() async {
    var response = await http.get(
      '$baseUrl/localidades',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Localidad> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Localidad.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //ENFERMEDADES
  Future<List<Enfermedad>> getEnfermedades() async {
    var response = await http.get(
      '$baseUrl/enfermedades',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Enfermedad> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Enfermedad.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //PROVEEDIORES
  Future<List<Proveedor>> getProveedores() async {
    var response = await http.get(
      '$baseUrl/proveedores',
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Proveedor> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Proveedor.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //VACUNAS
  Future<List<Vacuna>> getVacunas() async {
    var response = await http.get(
      '$baseUrl/vacunas',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Vacuna> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Vacuna.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //PLANES DE VACUNACION
  Future<List<PlanVacunacion>> getPlanesVacunacion() async {
    var response = await http.get(
      '$baseUrl/planesVacunacion',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<PlanVacunacion> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(PlanVacunacion.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  Future<bool> exitoLoginGubUY(GubUY gubAuth) async {
    String url = '$baseUrl/autenticaciongubuy/procesarTokens?code=' + gubAuth.code + '&state=' + gubAuth.state;
    var response = await http.get(url);

    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      if (json["mensaje"] == "Usuario logueado con éxito.") {
        storedUserCredentials = emptyUser;
        storedUserCredentials.setToken(json["cuerpo"]["token"]);
        storedUserCredentials.userData = Usuario.fromJson(json["cuerpo"] ?? "");
      }
      saveUserCredentials();
      setAuthHeader();
    }
    return Future<bool>.sync(() => true);
  }

  //AGENDAS
  Future<List<Agenda>> getAgendas() async {
    var response = await http.get(
      '$baseUrl/agendas',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Agenda> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Agenda.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  Future<List<Agenda>> getAgendasCiudadano(int id) async {
    var response = await http.get(
      '$baseUrl/usuarios/listarAgendasCiudadano/$id',
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Agenda> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(Agenda.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  Future<bool> agendarUsuario(DateTime date, int puestoId, int usuarioId, int planVacId) async {
    var editBody = jsonEncode(<String, dynamic>{
      "fecha": date.toString().split(" ")[0],
      "puesto": puestoId,
      "usuario": usuarioId,
      "planVacunacion": planVacId,
    });

    var response = await http.post(
      '$baseUrl/agendas',
      headers: authHeader,
      body: editBody,
    );

    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      print(json);
    }

    return response.statusCode == 200;
  }

  Future<bool> borrarAgenda(int usuarioId, int agendaId) async {
    var response = await http.delete(
      '$baseUrl/agendas/cancelarAgenda/$usuarioId/$agendaId',
      headers: authHeader,
    );

    print('$baseUrl/agendas/cancelarAgenda/$usuarioId/$agendaId');
    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      print(json);
    }

    return response.statusCode == 200;
  }

  //OTROS
  Future<int> getPoblacionTotal() async {
    var response = await http.post(
      'https://monitor.uruguaysevacuna.gub.uy/plugin/cda/api/doQuery?',
      body:
          "path=%2Fpublic%2FEpidemiologia%2FVacunas+Covid%2FPaneles%2FVacunas+Covid%2FVacunasCovid.cda&dataAccessId=sql_indicadores_Poblacion_total_objetivo&outputIndexId=1&pageSize=0&pageStart=0&sortBy=&paramsearchBox=",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
        "origin": "https://monitor.uruguaysevacuna.gub.uy",
        "sec-fetch-site": "cross-site",
        "sec-fetch-dest": "empty",
        "sec-fetch-mode": "cors",
        "Referrer-Policy": "unsafe-url",
      },
    );
    if (response.statusCode == 200) {
      Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
      String poblacionTota = json["resultset"][0];
      return int.parse(poblacionTota);
    } else {
      return 0;
    }
  }
}
