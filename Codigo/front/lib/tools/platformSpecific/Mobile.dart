import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:pdf/pdf.dart';
import 'package:pdf/widgets.dart' as pw;
import 'package:printing/printing.dart';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';

Future<bool> autoLogIn(context) async {
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

void shareTwitter(String text) {}

void shareFacebook(String text) {}

Future<void> printPDF(String fileName, Uint8List image) async {
  final pdf = pw.Document();
  pdf.addPage(
    pw.MultiPage(
      pageFormat: PdfPageFormat.a4,
      margin: pw.EdgeInsets.all(32),
      build: (pw.Context context) {
        return <pw.Widget>[
          pw.Header(
            level: 0,
            child: pw.Text("Testing pdf document"),
          ),
        ];
      },
    ),
  );

  Directory docDirec = await getApplicationDocumentsDirectory();
  String docPath = docDirec.path;
  File file = File('$docPath/$fileName.pdf');
  file.writeAsBytesSync(List.from(await pdf.save()));
}
