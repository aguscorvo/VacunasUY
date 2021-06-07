import 'package:vacunas_uy/objects/Rol.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:encrypt/encrypt.dart';
import 'package:vacunas_uy/objects/Usuario.dart';

//'0a9fd8885934e251e2a07b56b73086b4'

final userKey = Key.fromUtf8('0a9fd8885934e251e2a07b56b73086b4');
final iv = IV.fromLength(16);
final encrypter = Encrypter(AES(userKey));

UserCredentials storedUserCredentials;

final UserCredentials emptyUser = UserCredentials(
  userData: Usuario(),
  token: "", //encrypter.encrypt("empty", iv: iv).base64,
);

final UserCredentials logedOffUser = UserCredentials(
  userData: Usuario(correo: ""),
  token: "", //encrypter.encrypt("empty", iv: iv).base64,
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

  setToken(String newToken) {
    token = encrypter.encrypt(newToken, iv: iv).base64;
    saveUserCredentials();
  }

  String getToken() {
    return encrypter.decrypt64(token, iv: iv);
  }

  Usuario getUserData() {
    return userData;
  }

  UserCredentials.fromJson(Map<String, dynamic> json) {
    token = json['token'];

    Map<String, dynamic> jsonUserData = json['userData'];

    userData = Usuario();

    userData.id = jsonUserData['id'];
    userData.nombre = jsonUserData['nombre'];
    userData.apellido = jsonUserData['apellido'];
    userData.correo = jsonUserData['correo'];
    userData.documento = jsonUserData['documento'];
    userData.fechaNacimiento = DateTime.tryParse(jsonUserData['fechaNacimiento'].toString());
    if (userData.fechaNacimiento.toString() == "null") {
      int year = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[0]);
      int month = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[1]);
      int day = int.parse(jsonUserData['fechaNacimiento'].toString().split("-")[2]);
      userData.fechaNacimiento = new DateTime(year, month, day);
    }
    userData.roles = [];

    if (jsonUserData['roles'] != "null") {
      String roles = jsonUserData['roles'].toString().replaceAll("]", ",]");
      List<String> rolesParsed = roles.split("},");

      for (int i = 0; i < rolesParsed.length; i++) {
        rolesParsed[i] = rolesParsed[i].replaceAll("{", "").replaceAll("}", "").replaceAll("[", "").replaceAll("]", "");
      }

      for (int i = 0; i < rolesParsed.length; i++) {
        if (rolesParsed[i] != "") {
          int id = int.parse(rolesParsed[i].split(",")[0].split(":")[1]);
          String nombre = rolesParsed[i].split(",")[1].split(":")[1].replaceAll("\"", "");
          userData.roles.add(Rol(id: id, nombre: nombre));
        }
      }
    } else {
      userData.roles = [];
    }
  }

  Map<String, dynamic> toJson() => {'token': token, 'userData': userData.toJson()};
}
