import 'dart:convert';
import 'dart:io';

import 'package:vacunas_uy/AppConfig.dart';
import 'package:vacunas_uy/objects/Agenda.dart';
import 'package:vacunas_uy/objects/Atiende.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/objects/GubUY.dart';
import 'package:vacunas_uy/objects/Localidad.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorEnfermedad.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorPlan.dart';
import 'package:vacunas_uy/objects/Monitor/MonitorVacuna.dart';
import 'package:vacunas_uy/objects/PlanVacunacion.dart';
import 'package:vacunas_uy/objects/Proveedor.dart';
import 'package:vacunas_uy/objects/Puesto.dart';
import 'package:vacunas_uy/objects/Rol.dart';
import 'package:vacunas_uy/objects/Sector.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/objects/Departamento.dart';
import 'package:vacunas_uy/objects/ActoVacunal.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:http/http.dart' as http;

const String baseUrl = AppConfig.componenteCentalURL;

var authHeader = {HttpHeaders.authorizationHeader: "Bearer ${storedUserCredentials!.token}", "Content-Type": "application/json"};

class BackendConnection {
  static final BackendConnection _singleton = BackendConnection._internal();

  factory BackendConnection() {
    return _singleton;
  }

  BackendConnection._internal();

  static setAuthHeader() {
    authHeader = {HttpHeaders.authorizationHeader: "Bearer ${storedUserCredentials!.token}", "Content-Type": "application/json"};
  }

  bool theSameDay(DateTime date1, DateTime date2) {
    if (date1.year == date2.year && date1.month == date2.month && date1.day == date2.day) {
      return true;
    } else {
      return false;
    }
  }

  Future<bool> login({required String correo, required String password}) async {
    var response = await http.post(
      Uri.parse('$baseUrl/usuarios/loginbackoffice'),
      body: jsonEncode(<String, String>{
        "correo": correo,
        "password": password,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    );

    if (response.statusCode == 200) {
      try {
        Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
        if (json["mensaje"] == "Inicio de sesión correcto.") {
          storedUserCredentials = emptyUser;
          storedUserCredentials!.token = json["cuerpo"]["token"];
          storedUserCredentials!.persistirLogin = false;
          storedUserCredentials!.loginDateTime = DateTime.now();
          storedUserCredentials!.userData = Usuario.fromJson(json["cuerpo"] ?? "");
          saveUserCredentials();
          setAuthHeader();
          return true;
        } else {
          return false;
        }
      } catch (err) {
        String withoutNulls = utf8.decode(response.body.codeUnits);
        Map<dynamic, dynamic> json = jsonDecode(withoutNulls)["cuerpo"];

        storedUserCredentials = emptyUser;
        storedUserCredentials!.token = json["token"] == null ? "" : json["token"];
        storedUserCredentials!.persistirLogin = false;
        storedUserCredentials!.loginDateTime = DateTime.now();
        storedUserCredentials!.userData!.apellido = json["apellido"] != null ? json["apellido"] : "";
        storedUserCredentials!.userData!.nombre = json["nombre"] != null ? json["nombre"] : "";
        storedUserCredentials!.userData!.correo = json["correo"] != null ? json["correo"] : "";
        storedUserCredentials!.userData!.documento = json["documento"] != null ? json["documento"] : "";
        storedUserCredentials!.userData!.fechaNacimiento = json["fechaNacimiento"] != null
            ? json["fechaNacimiento"] == ""
                ? DateTime.now()
                : DateTime.tryParse(json["fechaNacimiento"].toString())!
            : DateTime.now();
        storedUserCredentials!.userData!.id = json["id"] != null ? json["id"] : -1;
        List<Rol> roles = [];
        if (json['roles'] != null) {
          json['roles'].forEach((rol) => {roles.add(Rol.fromJson(rol))});
        }
        storedUserCredentials!.userData!.roles = roles;
        storedUserCredentials!.userData!.sectorLaboral = json["sectorLaboral"] != null ? json["sectorLaboral"] : Sector();
        saveUserCredentials();
        setAuthHeader();

        return true;
      }
    } else {
      return false;
    }
  }

  Future<bool> recoverPassword({required String email}) async {
    var response = await http.post(
      Uri.parse('$baseUrl/usuarios/recuperarContra/$email'),
      headers: authHeader,
    );
    return response.statusCode == 200;
  }

  //USUARIOS
  Future<List<Usuario>> getUsuarios() async {
    var response = await http.get(
      Uri.parse('$baseUrl/usuarios'),
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

  Future<Usuario> getUsuarioToken(String token) async {
    var response = await http.get(
      Uri.parse('$baseUrl/usuarios/token/$token'),
      headers: authHeader,
    );

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
      if (jsonResponse.length > 0) {
        Usuario usuario;
        usuario = Usuario.fromJson(jsonResponse);
        return usuario;
      }
    }

    return Usuario();
  }

  Future<Usuario> getUsuarioPorId(int id) async {
    var response = await http.get(Uri.parse('$baseUrl/usuarios/listar/$id'));

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
      if (jsonResponse.length > 0) {
        Usuario usuario;
        usuario = Usuario.fromJson(jsonResponse);
        return usuario;
      }
    }

    return Usuario();
  }

  Future<bool> actualizarDatosUsuario(Usuario u) async {
    if (u.id == -1) {
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
        "sectorLaboral": u.sectorLaboral.id == -1 ? 7 : u.sectorLaboral.id,
      });

      var response = await http.put(
        Uri.parse('$baseUrl/usuarios/editar/${u.id}'),
        headers: authHeader,
        body: editBody,
      );

      if (response.statusCode == 200) {
        Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
        if (json["mensaje"] == "Usuario editado con éxito." && storedUserCredentials!.userData!.id == u.id) {
          storedUserCredentials!.userData = Usuario.fromJson(json["cuerpo"] ?? "");

          saveUserCredentials();
          setAuthHeader();
        }
      } else {}
      return response.statusCode == 200;
    }
  }

  //VACUNATORIOS
  Future<List<Vacunatorio>> getVacunatorios() async {
    var response = await http.get(
      Uri.parse('$baseUrl/vacunatorios'),
      headers: authHeader,
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
      Uri.parse('$baseUrl/vacunatorios/listarVacunatoriosDadoPlan/$id'),
      headers: authHeader,
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

  Future<List<Puesto>> getPuestosDeVacunatorio({required int idVacunatorio}) async {
    var response = await http.get(
      Uri.parse('$baseUrl/vacunatorios/$idVacunatorio'),
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
    var response = await http.get(
      Uri.parse('$baseUrl/departamentos'),
      headers: authHeader,
    );
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
      Uri.parse('$baseUrl/localidades'),
      headers: authHeader,
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
      Uri.parse('$baseUrl/enfermedades'),
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
      Uri.parse('$baseUrl/proveedores'),
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
      Uri.parse('$baseUrl/vacunas'),
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

  Future<List<ActoVacunal>> getActosVacunalesPorEnfermedadLogged(int idEnf) async {
    return getActosVacunalesUsuarioPorEnfermedad(storedUserCredentials!.userData!.id, idEnf);
  }

  Future<List<ActoVacunal>> getActosVacunalesUsuarioPorEnfermedad(int idUsu, int idEnf) async {
    var response = await http.get(
      Uri.parse('$baseUrl/actosVacunales/listarActosVacunalesPorUsuarioEnfermedad/$idUsu/$idEnf'),
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<ActoVacunal> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        contentList.add(ActoVacunal.fromJson(jsonResponse[i]));
      }
      return contentList;
    }
    return [];
  }

  //PLANES DE VACUNACION
  Future<List<PlanVacunacion>> getPlanesVacunacions() async {
    var response = await http.get(
      Uri.parse('$baseUrl/planesVacunacion'),
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

  Future<List<PlanVacunacion>> getPlanesVacunacionVigentes() async {
    var response = await http.get(
      Uri.parse('$baseUrl/planesVacunacion/listarVigentes'),
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

  //AGENDAS
  Future<List<Agenda>> getAgendas() async {
    var response = await http.get(
      Uri.parse('$baseUrl/agendas'),
      headers: authHeader,
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

  Future<List<Agenda>> getAgendasCiudadanoTodas(int id) async {
    return getAgendasCiudadano(id, true);
  }

  Future<List<Agenda>> getAgendasCiudadanoNuevas(int id) async {
    return getAgendasCiudadano(id, false);
  }

  Future<List<Agenda>> getAgendasCiudadano(int id, bool todas) async {
    var response = await http.get(
      Uri.parse('$baseUrl/usuarios/listarAgendasCiudadano/$id'),
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];

      List<Agenda> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        Agenda a = Agenda.fromJson(jsonResponse[i]);
        if (todas) {
          contentList.add(a);
        } else {
          if (a.fecha.isAfter(DateTime.now()) || theSameDay(a.fecha, DateTime.now())) {
            contentList.add(a);
          }
        }
      }
      return contentList;
    }
    return [];
  }

  Future<List<Atiende>> getAgendasVacunadorLogged() async {
    return getAgendasVacunador(storedUserCredentials!.userData!.id);
  }

  Future<List<Atiende>> getAgendasVacunador(int id) async {
    var response = await http.get(
      Uri.parse('$baseUrl/usuarios/listarAtiendeVacunador/$id'),
      headers: authHeader,
    );
    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
      List<Atiende> contentList = [];
      for (var i = 0; i < jsonResponse.length; i++) {
        Atiende a = Atiende.fromJson(jsonResponse[i]);
        contentList.add(a);
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
      Uri.parse('$baseUrl/agendas'),
      headers: authHeader,
      body: editBody,
    );

    if (response.statusCode == 200) {
      //Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
    }

    return response.statusCode == 200;
  }

  Future<bool> borrarAgenda(int usuarioId, int agendaId) async {
    var response = await http.put(
      Uri.parse('$baseUrl/agendas/cancelarAgenda/$usuarioId/$agendaId'),
      headers: authHeader,
    );

    if (response.statusCode == 200) {
      //Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
    }

    return response.statusCode == 200;
  }

  //MONITOR
  Future<MonitorEnfermedad> getMonitorEnfermedad(int id) async {
    var response = await http.get(
      Uri.parse('$baseUrl/monitor/enfermedad/$id'),
    );

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
      return MonitorEnfermedad.fromJson(jsonResponse);
    } else {
      return MonitorEnfermedad();
    }
  }

  Future<MonitorVacuna> getMonitorVacuna(int id) async {
    var response = await http.get(
      Uri.parse('$baseUrl/monitor/vacuna/$id'),
    );

    if (response.statusCode == 200) {
      var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
      return MonitorVacuna.fromJson(jsonResponse);
    } else {
      return MonitorVacuna();
    }
  }

  Future<MonitorPlan> getMonitorPlan(int id) async {
    var response = await http.get(
      Uri.parse('$baseUrl/monitor/plan/$id'),
    );

    if (response.statusCode == 200) {
      try {
        var jsonResponse = jsonDecode(utf8.decode(response.body.codeUnits))["cuerpo"];
        return MonitorPlan.fromJson(jsonResponse);
      } catch (err) {
        print(err);
        return MonitorPlan();
      }
    } else {
      return MonitorPlan();
    }
  }

  //OTROS
  Future<int> getPoblacionTotal() async {
    var response = await http.post(
      Uri.parse('https://monitor.uruguaysevacuna.gub.uy/plugin/cda/api/doQuery?'),
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

  Future<bool> exitoLoginGubUY(GubUY gubAuth) async {
    try {
      String url = '$baseUrl/autenticaciongubuy/procesarTokens?code=' + gubAuth.code + '&state=' + gubAuth.state;
      var response = await http.get(Uri.parse(url));

      if (response.statusCode == 200) {
        Map<String, dynamic> json = jsonDecode(utf8.decode(response.body.codeUnits));
        if (json["mensaje"] == "Usuario logueado con éxito.") {
          storedUserCredentials = emptyUser;
          storedUserCredentials!.token = json["cuerpo"]["token"];
          getUsuarioToken(storedUserCredentials!.token!);
          storedUserCredentials!.persistirLogin = false;
          storedUserCredentials!.loginDateTime = DateTime.now();
          storedUserCredentials!.userData = Usuario.fromJson(json["cuerpo"] ?? "");
        }
        saveUserCredentials();
        setAuthHeader();
      }
    } catch (err) {}
    return Future<bool>.sync(() => true);
  }
}
