import 'dart:convert';
// ignore: avoid_web_libraries_in_flutter
import 'dart:html';
import 'package:url_launcher/url_launcher.dart';
import 'package:vacunas_uy/AppConfig.dart';
import 'package:vacunas_uy/objects/BackOfficeUser.dart';
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
  if (prefs.getString("vacunasUY") != null) {
    try {
      final String savedPreferencesString = prefs.getString("vacunasUY")!;
      if (savedPreferencesString.toString() == "null" ||
          savedPreferencesString == '') {
        storedUserCredentials = emptyUser;
      } else {
        Map savedPreferences = jsonDecode(savedPreferencesString);
        storedUserCredentials =
            UserCredentials.fromJson(savedPreferences as Map<String, dynamic>);
        if (storedUserCredentials!.userData == null) {
          storedUserCredentials = emptyUser;
        } else if (storedUserCredentials!.userData!.correo == '') {
          storedUserCredentials = emptyUser;
        } else if (isSesionExpired()) {
          storedUserCredentials = emptyUser;
        }
      }
    } catch (err) {}
    if (!AppConfig.flutterBackOffice) {
      if (isUserAdmin()) {
        urlReplace(AppConfig.adminBackOfficeURL);
      } else if (isUserAutoridad()) {
        urlReplace(AppConfig.autoridadBackOfficeURL);
      }
    }
  } else {
    storedUserCredentials = emptyUser;
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
    appReload();
  }
  return Future<bool>.sync(() => true);
}

Future<bool> checkToken() async {
  var client = BackendConnection();
  var valid;

  if (storedUserCredentials != null) {
    if (storedUserCredentials!.token != null &&
        storedUserCredentials!.token != "") {
      valid = await client.getUsuarios();
      if (valid.length == 0) {
        valid = false;
      } else {
        valid = true;
      }
    } else {
      valid = true;
    }
  } else {
    valid = true;
  }

  if (!valid) {
    storedUserCredentials = emptyUser;
  }

  return Future<bool>.sync(() => true);
}

Future<bool> saveUserCredentials() async {
  try {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    var usercredentials = jsonEncode(storedUserCredentials);
    prefs.setString("vacunasUY", usercredentials);

    if (!AppConfig.flutterBackOffice) {
      BackOfficeUser user = BackOfficeUser();
      user.nombre = storedUserCredentials!.userData!.nombre +
          " " +
          storedUserCredentials!.userData!.apellido;
      user.token = storedUserCredentials!.token;
      final Storage sesionStorage = window.localStorage;
      sesionStorage['USERLOGIN'] = usercredentials;
      if (isUserAdmin()) {
        user.rol = 1;
        prefs.setString("vacunasUYUser", jsonEncode(user));
        urlReplace(AppConfig.adminBackOfficeURL);
      } else if (isUserAutoridad()) {
        user.rol = 2;
        prefs.setString("vacunasUYUser", jsonEncode(user));
        urlReplace(AppConfig.autoridadBackOfficeURL);
      }
    }
  } catch (err) {}
  return Future<bool>.sync(() => true);
}

Future<bool> deleteUserCredentials() async {
  try {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    final Storage sesionStorage = window.localStorage;
    prefs.clear();
    sesionStorage.clear();
  } catch (err) {}
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

void shareTwitter(String text) {
  String url =
      "https://twitter.com/intent/tweet?text=" + text.replaceAll(" ", "+");
  launch(url);
}

void shareFacebook(String text) {
  String url =
      "http://www.facebook.com/sharer.php?s=100&p[title]=Me+Vacune&p[url]=https://vacunasuy.web.elasticloud.uy&p[summary]=" +
          text.replaceAll(" ", "+");
  launch(url);
}
