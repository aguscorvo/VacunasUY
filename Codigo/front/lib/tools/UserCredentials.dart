import 'dart:convert';

import 'package:vacunas_uy/objects/Rol.dart';
import 'package:vacunas_uy/objects/Sector.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:encrypt/encrypt.dart';
import 'package:vacunas_uy/objects/Usuario.dart';

//'0a9fd8885934e251e2a07b56b73086b4'

final userKey = Key.fromUtf8('0a9fd8885934e251e2a07b56b73086b4');
final iv = IV.fromLength(16);
final encrypter = Encrypter(AES(userKey));

UserCredentials storedUserCredentials;

final UserCredentials emptyUser = UserCredentials(
  userData: Usuario(correo: ""),
  token: "",
);

final UserCredentials logedOffUser = UserCredentials(
  userData: Usuario(correo: ""),
  token: "",
);

bool isUserLogedOn() {
  bool toReturn = false;

  if (storedUserCredentials.toString() != "null" && storedUserCredentials.toString() != '') {
    if (storedUserCredentials.token != "") {
      if (storedUserCredentials.userData.toString() != "null") {
        if (storedUserCredentials.userData.correo != "") {
          toReturn = true;
        }
      }
    }
  }

  return toReturn;
}

bool isUserType(String type) {
  bool toReturn = false;

  if (isUserLogedOn()) {
    if (storedUserCredentials.userData.roles.toString() != "null") {
      storedUserCredentials.userData.roles.forEach((element) {
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
  String token;
  Usuario userData;

  UserCredentials({
    this.userData,
    this.token,
  });

  Map<String, dynamic> toJson() {
    try {
      Map<String, dynamic> toReturn = {
        'token': token ?? "",
        'userData': userData != null ? jsonEncode(userData) : Usuario(),
      };
      return toReturn;
    } catch (err) {}
  }

  UserCredentials.fromJson(Map<String, dynamic> json) {
    try {
      token = json['token'];

      Map<String, dynamic> jsonUserData = jsonDecode(json['userData']) != null ? jsonDecode(json['userData']) : null;

      if (jsonUserData != null) {
        userData = Usuario();

        DateTime dt = DateTime.tryParse(jsonUserData['fechaNacimiento'].toString());

        userData.id = jsonUserData['id'];
        userData.nombre = jsonUserData['nombre'];
        userData.apellido = jsonUserData['apellido'];
        userData.correo = jsonUserData['correo'];
        userData.documento = jsonUserData['documento'];
        userData.fechaNacimiento = dt;
        if (userData.fechaNacimiento.toString() == "null") {
          int year = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[0]);
          int month = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[1]);
          int day = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[2]);
          userData.fechaNacimiento = new DateTime(year, month, day);
        }
        userData.roles = [];

        List<dynamic> roles = jsonDecode(jsonUserData['roles']);
        roles.forEach((rol) {
          userData.roles.add(Rol.fromJson(rol));
        });
        if (jsonUserData['sectorLaboral'] != null) {
          userData.sectorLaboral = Sector.fromJson(jsonDecode(jsonUserData['sectorLaboral']));
        }
      } else {
        userData = null;
      }
    } catch (err) {}
  }
}
