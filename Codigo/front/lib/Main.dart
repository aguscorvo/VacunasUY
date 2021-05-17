import 'dart:convert';
import 'dart:html';

import 'package:VacunasUY/tools/BackendConnection.dart';
import 'package:VacunasUY/tools/UserCredentials.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'assets/CustomAppBar.dart';
import 'objects/GubUY.dart';

void main() async {
  await cookiesLoad();

  runApp(MyApp());
}

void cookiesLoad() async {
  final SharedPreferences prefs = await SharedPreferences.getInstance();
  final String savedPreferencesString = prefs.getString("VacunasUY");
  if (savedPreferencesString == null || savedPreferencesString == '') {
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

      print(gubAuth.toJson());

      BackendConnection bc = new BackendConnection();
      bc.exitoLoginGubUY(gubAuth);
    }

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
  int _counter = 0;
  int _selectedIndex = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(title: widget.title),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              ':',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Image(image: AssetImage('assets/icons/vacuna_xs.png')),
            label: 'Vacunas',
            backgroundColor: colorCustom,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.apartment_sharp),
            label: 'Vacunatorios',
            backgroundColor: colorCustom,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.list_alt),
            label: 'Planes de Vacunacion',
            backgroundColor: colorCustom,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.coronavirus),
            label: 'Enfermedades',
            backgroundColor: colorCustom,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.directions_boat),
            label: 'Proveedores',
            backgroundColor: colorCustom,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.menu_book),
            label: 'Agenda',
            backgroundColor: colorCustom,
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
        showUnselectedLabels: true,
        selectedFontSize: 16,
      ),
    );
  }
}
