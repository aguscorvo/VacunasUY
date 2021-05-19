import 'dart:convert';
import 'dart:html';

import 'package:VacunasUY/assets/CustomNavBar.dart';
import 'package:VacunasUY/tools/BackendConnection.dart';
import 'package:VacunasUY/tools/UserCredentials.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'assets/CustomAppBar.dart';
import 'objects/GubUY.dart';
import 'paginas/VacunatoriosTab.dart';

void main() async {
  await cookiesLoad();
  await specialURL();
  runApp(MyApp());
}

void cookiesLoad() async {
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
}

void specialURL() async {
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
}

class MyApp extends StatelessWidget {
  final MaterialColor colorCustom = MaterialColor(0xFF174378, {
    050: Color.fromRGBO(0, 0, 250, .1),
    100: Color.fromRGBO(0, 0, 250, .2),
    200: Color.fromRGBO(0, 0, 250, .3),
    300: Color.fromRGBO(0, 0, 250, .4),
    400: Color.fromRGBO(0, 0, 250, .5),
    500: Color.fromRGBO(0, 0, 250, .6),
    600: Color.fromRGBO(0, 0, 250, .7),
    700: Color.fromRGBO(0, 0, 250, .8),
    800: Color.fromRGBO(0, 0, 250, .9),
    900: Color.fromRGBO(0, 0, 250, 1),
  });

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Vacunas UY',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: colorCustom,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      initialRoute: "/",
      home: MyHomePage(title: 'Vacunas UY'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Widget _body = VacunatoriosTab();

  _setBody(Widget val) {
    setState(() {
      _body = val;
    });
  }

  @override
  Widget build(BuildContext context) {
    CustomNavBar navBar = CustomNavBar(
      title: widget.title,
      onElementSelected: (Widget val) => _setBody(val),
    );
    CustomAppBar appBar = CustomAppBar(
      title: widget.title,
      onElementSelected: (Widget val) => _setBody(val),
    );

    return Scaffold(
      appBar: appBar,
      body: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: _body,
          ),
        ],
      ),
      bottomNavigationBar: navBar,
    );
  }
}
