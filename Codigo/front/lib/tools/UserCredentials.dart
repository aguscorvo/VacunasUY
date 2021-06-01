import 'dart:convert';
import 'dart:html';

import 'package:VacunasUY/objects/Rol.dart';
import 'package:encrypt/encrypt.dart';
import 'package:VacunasUY/objects/Usuario.dart';
import 'package:shared_preferences/shared_preferences.dart';

//'0a9fd8885934e251e2a07b56b73086b4'

final user_key = Key.fromUtf8('0a9fd8885934e251e2a07b56b73086b4');
final iv = IV.fromLength(16);
final encrypter = Encrypter(AES(user_key));

UserCredentials storedUserCredentials;

Future<Null> saveUserCredentials() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  final Storage sesionStorage = window.localStorage;
  var usercredentials = jsonEncode(storedUserCredentials);
  print(usercredentials.toString());
  prefs.setString("vacunasUY", usercredentials);
  sesionStorage['USERLOGIN'] = usercredentials;
}

final UserCredentials emptyUser = UserCredentials(
  userData: Usuario(),
  name: "", //encrypter.encrypt("empty", iv: iv).base64,
  token: "", //encrypter.encrypt("empty", iv: iv).base64,
  isNewUser: true,
);

final UserCredentials logedOffUser = UserCredentials(
  userData: Usuario(),
  name: "", //encrypter.encrypt("empty", iv: iv).base64,
  token: "", //encrypter.encrypt("empty", iv: iv).base64,
  isNewUser: false,
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
  String name;
  String token;
  Usuario userData;
  bool isNewUser;

  UserCredentials({
    this.userData,
    this.isNewUser,
    this.name,
    this.token,
  });

  setName(String newNickname) {
    name = encrypter.encrypt(newNickname, iv: iv).base64;
    saveUserCredentials();
  }

  setToken(String newToken) {
    token = encrypter.encrypt(newToken, iv: iv).base64;
    saveUserCredentials();
  }

  String getNickname() {
    try {
      return encrypter.decrypt64(name, iv: iv);
    } catch (e) {
      return "";
    }
  }

  String getToken() {
    return encrypter.decrypt64(token, iv: iv);
  }

  Usuario getUserData() {
    return userData;
  }

  UserCredentials.fromJson(Map<String, dynamic> json) {
    name = json['nickName'];
    token = json['token'];
    isNewUser = json['isNewUser'];

    Map<String, dynamic> jsonUserData = json['userData'];

    userData = Usuario();

    userData.id = jsonUserData['_id'];
    userData.nombre = jsonUserData['nombre'];
    userData.apellido = jsonUserData['apellido'];
    userData.correo = jsonUserData['correo'];
    userData.documento = jsonUserData['documento'];
    userData.fechaNacimiento = jsonUserData['fechaNacimiento'];
    userData.roles = [];

    List<dynamic> jsonRoles = jsonUserData['roles'];

    jsonRoles.forEach((element) {
      userData.roles.add(new Rol(id: element['id'], nombre: element['nombre']));
    });
  }

  Map<String, dynamic> toJson() => {'nickName': name, 'token': token, 'isNewUser': isNewUser, 'userData': userData.toJson()};
}
