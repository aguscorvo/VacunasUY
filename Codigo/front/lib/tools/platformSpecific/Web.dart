import 'dart:convert';
// ignore: avoid_web_libraries_in_flutter
import 'dart:html';
import 'package:vacunas_uy/AppConfig.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:vacunas_uy/objects/GubUY.dart';
import 'package:shared_preferences/shared_preferences.dart';

Future<bool> autoLogIn() async {
  await cookiesLoad();
  await specialURL();
  await checkToken();
  return Future<bool>.sync(() => true);
}

Future<bool> cookiesLoad() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  final String savedPreferencesString = prefs.getString("vacunasUY");
  if (savedPreferencesString.toString() == "null" || savedPreferencesString == '') {
    storedUserCredentials = emptyUser;
  } else {
    Map savedPreferences = jsonDecode(savedPreferencesString);
    storedUserCredentials = UserCredentials.fromJson(savedPreferences);
    if (storedUserCredentials.getUserData() == null) {
      storedUserCredentials = emptyUser;
    } else if (storedUserCredentials.getUserData().correo == '') {
      storedUserCredentials = emptyUser;
    }
  }

  if (!AppConfig.flutterBackOffice) {
    if (isUserAdmin()) {
      urlReplace(AppConfig.adminBackOfficeURL);
    } else if (isUserAutoridad()) {
      urlReplace(AppConfig.autoridadBackOfficeURL);
    }
  }
  return Future<bool>.sync(() => true);
}

Future<bool> specialURL() async {
  String url = window.location.href.toString();
  if (url.contains("code=") && url.contains("state=")) {
    String tokens = "";
    List<String> urls = url.split("/");
    urls.forEach((element) {
      if (element.contains("code=") && element.contains("state=")) {
        tokens = element.replaceAll(new RegExp(r'#'), '');
      }
    });
    String code = tokens.split("&")[0].split("=")[1];
    String state = tokens.split("&")[1].split("=")[1];

    GubUY gubAuth = new GubUY();
    gubAuth.code = code;
    gubAuth.state = state;

    BackendConnection bc = new BackendConnection();
    await bc.exitoLoginGubUY(gubAuth);
  }
  return Future<bool>.sync(() => true);
}

Future<bool> checkToken() async {
  var client = BackendConnection();
  var valid = await client.getUsuarios();
  if (valid.length == 0) {
    storedUserCredentials = emptyUser;
  }
  return Future<bool>.sync(() => true);
}

Future<bool> saveUserCredentials() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  final Storage sesionStorage = window.localStorage;
  var usercredentials = jsonEncode(storedUserCredentials);
  if (!AppConfig.flutterBackOffice) {
    sesionStorage['USERLOGIN'] = usercredentials;
    if (isUserAdmin()) {
      urlReplace(AppConfig.adminBackOfficeURL);
    } else if (isUserAutoridad()) {
      urlReplace(AppConfig.autoridadBackOfficeURL);
    } else {
      prefs.setString("vacunasUY", usercredentials);
    }
  } else {
    prefs.setString("vacunasUY", usercredentials);
  }
  return Future<bool>.sync(() => true);
}

Future<bool> appReload() async {
  if (window.location.toString().contains("/?code")) {
    window.location.replace(window.location.toString().split("/?code")[0]);
  } else {
    window.location.reload();
  }
  return Future<bool>.sync(() => true);
}

void urlReplace(String url) {
  window.location.replace(url);
}
