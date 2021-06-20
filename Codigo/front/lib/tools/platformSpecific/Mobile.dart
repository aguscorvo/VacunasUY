import 'dart:convert';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:shared_preferences/shared_preferences.dart';

Future<bool> autoLogIn() async {
  await cookiesLoad();
  await checkToken();
  return Future<bool>.sync(() => true);
}

Future<bool> cookiesLoad() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  final String savedPreferencesString = prefs.getString("vacunasUY")!;
  if (savedPreferencesString.toString() == "null" || savedPreferencesString == '') {
    storedUserCredentials = emptyUser;
  } else {
    Map savedPreferences = jsonDecode(savedPreferencesString);
    storedUserCredentials = UserCredentials.fromJson(savedPreferences as Map<String, dynamic>);
    if (storedUserCredentials!.userData == null) {
      storedUserCredentials = emptyUser;
    } else if (storedUserCredentials!.userData!.correo == '') {
      storedUserCredentials = emptyUser;
    } else if (isSesionExpired()) {
      storedUserCredentials = emptyUser;
    }
  }
  return Future<bool>.sync(() => true);
}

Future<bool> checkToken() async {
  var client = BackendConnection();
  var valid;

  if (storedUserCredentials != null) {
    if (storedUserCredentials!.token != null && storedUserCredentials!.token != "") {
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
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  var usercredentials = jsonEncode(storedUserCredentials);
  prefs.setString("vacunasUY", usercredentials);
  return Future<bool>.sync(() => true);
}

Future<bool> deleteUserCredentials() async {
  try {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.clear();
  } catch (err) {}
  return Future<bool>.sync(() => true);
}

Future<bool> appReload() async {
  // TODO: Reload Mobile
  return Future<bool>.sync(() => true);
}

void urlReplace(String url) {
  // TODO: Reload Mobile
}

void shareTweeter(String text) {}

void shareFacebook(String text) {}
