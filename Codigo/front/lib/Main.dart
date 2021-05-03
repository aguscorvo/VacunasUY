import 'package:flutter/material.dart';

import 'assets/CustomAppBar.dart';

void main() {
  runApp(MyApp());
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
              'Che bo ñery, has presionado el + esta cantidad de veces:',
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
