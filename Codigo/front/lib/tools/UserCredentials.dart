import 'dart:convert';

import 'package:vacunas_uy/objects/Rol.dart';
import 'package:vacunas_uy/objects/Sector.dart';
import 'package:encrypt/encrypt.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';

import 'BackendConnection.dart';

//'0a9fd8885934e251e2a07b56b73086b4'

final userKey = Key.fromUtf8('0a9fd8885934e251e2a07b56b73086b4');
final iv = IV.fromLength(16);
final encrypter = Encrypter(AES(userKey));

UserCredentials? storedUserCredentials;

final UserCredentials emptyUser = UserCredentials(
  token: "",
  loginDateTime: new DateTime.now(),
  persistirLogin: false,
  userData: Usuario(),
);

final UserCredentials logedOffUser = UserCredentials(
  token: "",
  loginDateTime: new DateTime.now(),
  persistirLogin: false,
  userData: Usuario(),
);

bool isUserLogedOn() {
  bool toReturn = false;

  if (storedUserCredentials!.toString() != "null" && storedUserCredentials!.toString() != '') {
    if (storedUserCredentials!.token != "") {
      if (storedUserCredentials!.userData.toString() != "null") {
        if (storedUserCredentials!.userData!.correo != "") {
          toReturn = true;
        }
      }
    }
  }

  return toReturn;
}

bool isSesionExpired() {
  if (isUserLogedOn()) {
    if (storedUserCredentials!.persistirLogin == true) {
      return false;
    } else {
      if (DateTime.now().isAfter(addDays(storedUserCredentials!.loginDateTime!, 1))) {
        return true;
      } else {
        return false;
      }
    }
  } else {
    return true;
  }
}

DateTime addDays(DateTime originalDate, int cantDays) {
  return originalDate.add(Duration(days: cantDays));
}

bool isUserType(String type) {
  bool toReturn = false;

  if (isUserLogedOn()) {
    if (storedUserCredentials!.userData!.roles.toString() != "null") {
      storedUserCredentials!.userData!.roles.forEach((element) {
        if (element.nombre == type) {
          toReturn = true;
        }
      });
    }
  }

  return toReturn;
}

bool isUserAdmin() {
  bool toReturn = false;

  if (isUserType("Administrador")) {
    toReturn = true;
  }

  return toReturn;
}

bool isUserAutoridad() {
  bool toReturn = false;

  if (isUserType("Autoridad")) {
    toReturn = true;
  }

  return toReturn;
}

bool isUserVacunador() {
  bool toReturn = false;

  if (isUserType("Vacunador")) {
    toReturn = true;
  }

  return toReturn;
}

bool isUserCiudadano() {
  bool toReturn = false;

  if (isUserType("Ciudadano")) {
    toReturn = true;
  }

  return toReturn;
}

class UserCredentials {
  String? token;
  DateTime? loginDateTime;
  bool? persistirLogin;
  Usuario? userData;

  DateTime? userDataTempTime;

  UserCredentials({
    this.userData,
    this.loginDateTime,
    this.persistirLogin,
    this.token,
  });

  void updateUserData() async {
    try {
      bool doGetUser = true;
      if (userDataTempTime != null) {
        if (DateTime.now().isBefore(userDataTempTime!.add(const Duration(seconds: 10)))) {
          doGetUser = false;
        }
      }
      if (token == "") {
        doGetUser = false;
      }
      if (doGetUser) {
        BackendConnection client = BackendConnection();

        Future<Usuario> us = client.getUsuarioToken(token!);

        await us.then((value) => {
              if (value.id != -1)
                {
                  userDataTempTime = DateTime.now(),
                  userData = value,
                  saveUserCredentials(),
                }
            });
      }
    } catch (err) {
      print("updateUserData()" + err.toString());
    }
  }

  Map<String, dynamic> toJson() {
    Map<String, dynamic> toReturn = {};
    try {
      toReturn = {
        'token': token ?? "",
        'loginDateTime': loginDateTime != null ? formatDateTime(loginDateTime!) : "",
        'persistirLogin': persistirLogin,
        'userData': userData != null ? jsonEncode(userData) : Usuario(),
      };
    } catch (err) {
      print("UserCredentials.toJson()" + err.toString());
    }
    return toReturn;
  }

  UserCredentials.fromJson(Map<String, dynamic> json) {
    try {
      token = json['token'];
      loginDateTime = DateTime.tryParse(json['loginDateTime'].toString());
      persistirLogin = json['persistirLogin'];

      Map<String, dynamic>? jsonUserData = jsonDecode(json['userData']) != null ? jsonDecode(json['userData']) : null;

      if (jsonUserData != null) {
        userData = Usuario();

        int anio = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[0]);
        int mes = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[1]);
        int dia = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[2]);
        DateTime dt = DateTime(anio, mes, dia);

        userData!.id = jsonUserData['id'];
        userData!.nombre = jsonUserData['nombre'];
        userData!.apellido = jsonUserData['apellido'];
        userData!.correo = jsonUserData['correo'];
        userData!.documento = jsonUserData['documento'];
        userData!.fechaNacimiento = dt;

        if (userData!.fechaNacimiento.toString() == "null") {
          int year = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[0]);
          int month = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[1]);
          int day = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[2]);
          userData!.fechaNacimiento = new DateTime(year, month, day);
        }
        userData!.roles = [];

        List<dynamic> roles = jsonDecode(jsonUserData['roles']);
        roles.forEach((rol) {
          userData!.roles.add(Rol.fromJson(rol));
        });
        if (jsonUserData['sectorLaboral'] != null) {
          userData!.sectorLaboral = Sector.fromJson(jsonDecode(jsonUserData['sectorLaboral']));
        }
      } else {
        userData = null;
      }
    } catch (err) {
      print("UserCredentials.fromJson() " + err.toString());
    }
  }

  String formatDateTime(DateTime date) {
    String toReturn = "";
    toReturn = formatDate(date) + " " + formatTime(date);
    return toReturn;
  }

  String formatDate(DateTime date) {
    String toReturn = "";

    if (date.day < 10) {
      toReturn = "0" + date.day.toString();
    } else {
      toReturn = date.day.toString();
    }

    if (date.month < 10) {
      toReturn = toReturn + "/0" + date.month.toString();
    } else {
      toReturn = toReturn + "/" + date.month.toString();
    }

    toReturn = toReturn + "/" + date.year.toString();

    return toReturn;
  }

  String formatTime(DateTime date) {
    String toReturn = "";

    if (date.hour < 10) {
      toReturn = "0" + date.hour.toString();
    } else {
      toReturn = date.hour.toString();
    }

    if (date.minute < 10) {
      toReturn = toReturn + ":0" + date.minute.toString();
    } else {
      toReturn = toReturn + ":" + date.minute.toString();
    }

    if (date.second < 10) {
      toReturn = toReturn + ":0" + date.second.toString();
    } else {
      toReturn = toReturn + ":" + date.second.toString();
    }

    return toReturn;
  }
}
