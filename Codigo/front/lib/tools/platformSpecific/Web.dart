import 'dart:convert';
// ignore: avoid_web_libraries_in_flutter
import 'dart:html' as html;
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:vacunas_uy/AppConfig.dart';
import 'package:vacunas_uy/objects/BackOfficeUser.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/CertificadoInfo.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:vacunas_uy/objects/GubUY.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:pdf/pdf.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:printing/printing.dart';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';

Future<bool> autoLogIn(context) async {
  await cookiesLoad();
  await specialURL(context);
  await checkToken();
  return Future<bool>.sync(() => true);
}

Future<bool> cookiesLoad() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  if (prefs.getString("vacunasUY") != null) {
    try {
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

Future<bool> specialURL(context) async {
  String url = html.window.location.href.toString();
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
  if (url.contains("idUsuario=") && url.contains("idEnfermedad=")) {
    String tokens = "";
    List<String> urls = url.split("/");
    urls.forEach((element) {
      if (element.contains("idUsuario=") && element.contains("idEnfermedad=")) {
        tokens = element.replaceAll(new RegExp(r'#'), '');
      }
    });
    CertificadoInfo.hayCertificado = true;
    CertificadoInfo.idUsu = int.parse(tokens.split("&")[0].split("=")[1]);
    CertificadoInfo.idEnf = int.parse(tokens.split("&")[1].split("=")[1]);
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
  try {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    var usercredentials = jsonEncode(storedUserCredentials);
    prefs.setString("vacunasUY", usercredentials);

    if (!AppConfig.flutterBackOffice) {
      BackOfficeUser user = BackOfficeUser();
      user.nombre = storedUserCredentials!.userData!.nombre + " " + storedUserCredentials!.userData!.apellido;
      user.token = storedUserCredentials!.token;
      final html.Storage sesionStorage = html.window.localStorage;
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
    final html.Storage sesionStorage = html.window.localStorage;
    prefs.clear();
    sesionStorage.clear();
  } catch (err) {}
  return Future<bool>.sync(() => true);
}

Future<bool> appReload() async {
  if (html.window.location.toString().contains("/?code")) {
    html.window.location.replace(html.window.location.toString().split("/?code")[0]);
  } else {
    html.window.location.reload();
  }
  return Future<bool>.sync(() => true);
}

void urlReplace(String url) {
  html.window.location.replace(url);
}

void shareTwitter(String text) {
  String url = "https://twitter.com/intent/tweet?text=" + text.replaceAll(" ", "+");
  launch(url);
}

void shareFacebook(String text) {
  String url = "http://www.facebook.com/sharer.php?s=100&p[title]=Me+Vacune&p[url]=https://vacunasuy.web.elasticloud.uy&p[summary]=" + text.replaceAll(" ", "+");
  launch(url);
}

Future<void> printPDF(String fileName, Uint8List image) async {
  final pdf = pw.Document();
  final pdfImage = PdfImage.file(
    pdf.document,
    bytes: image,
  );
  pdf.addPage(
    pw.Page(
      pageFormat: PdfPageFormat.a4,
      margin: pw.EdgeInsets.all(32),
      build: (pw.Context context) {
        return pw.Expanded(
          child: pw.Container(
            child: pw.Center(
              child: pw.Image(
                pw.MemoryImage(image),
                fit: pw.BoxFit.contain,
              ),
            ),
          ),
        );
      },
    ),
  );

  Uint8List pdfInBytes = await pdf.save();

//Create blob and link from bytes
  final blob = html.Blob([pdfInBytes], 'application/pdf');
  final url = html.Url.createObjectUrlFromBlob(blob);
  final anchor = html.document.createElement('a') as html.AnchorElement
    ..href = url
    ..style.display = 'none'
    ..download = '$fileName.pdf';
  html.document.body!.children.add(anchor);

// download
  anchor.click();

// cleanup
  html.document.body!.children.remove(anchor);
  html.Url.revokeObjectUrl(url);
}
