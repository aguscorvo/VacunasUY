import 'package:firebase_core/firebase_core.dart';
import 'package:vacunas_uy/BaseApp.dart';
import 'package:flutter/material.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
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
      home: BaseApp(title: 'Vacunas UY'),
    );
  }
}
