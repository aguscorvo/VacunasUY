import 'package:vacunas_uy/paginas/agenda/AgendaTab.dart';
import 'package:vacunas_uy/paginas/EnfermedadesTab.dart';
import 'package:vacunas_uy/paginas/ErrorTab.dart';
import 'package:vacunas_uy/paginas/PlanVacunacionTab.dart';
import 'package:vacunas_uy/paginas/ProveedoresTab.dart';
import 'package:vacunas_uy/paginas/VacunasTab.dart';
import 'package:vacunas_uy/paginas/chat/ChatTab.dart';
import 'package:vacunas_uy/paginas/misVacunas/MisVacunasTab.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonitorVacunacionTab.dart';
import 'package:vacunas_uy/paginas/vacunatorio/VacunatorioTab.dart';
import 'package:flutter/material.dart';

import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter_tabler_icons/flutter_tabler_icons.dart';

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

class CustomNavBar extends StatefulWidget implements PreferredSizeWidget {
  final String title;
  final Function(Widget) onElementSelected;

  CustomNavBar({
    required Key key,
    required this.title,
    required this.onElementSelected,
  })   : preferredSize = Size.fromHeight(kToolbarHeight),
        super(key: key);

  @override
  final Size preferredSize; // default is 56.0

  @override
  _CustomNavBarState createState() => _CustomNavBarState(onElementSelected);
}

class _CustomNavBarState extends State<CustomNavBar> {
  final Function(Widget) onElementSelected;
  int _selectedIndex = -1;
  List<BottomNavigationBarItem> nabBarItems = [];

  _CustomNavBarState(this.onElementSelected);

  String getIndexLabel(int index) {
    BottomNavigationBarItem item = nabBarItems[index];
    return item.label!;
  }

  int getLabelIndex(String label) {
    int toReturn = -1;

    int i = 0;
    nabBarItems.forEach((item) {
      if (item.label == label) {
        toReturn = i;
      }
      i++;
    });

    return toReturn;
  }

  @override
  Widget build(BuildContext context) {
    nabBarItems = [];

    if (isUserAdmin()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.coronavirus),
        label: 'Enfermedades',
        backgroundColor: colorCustom,
      ));
    }

    if (isUserLogedOn()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(TablerIcons.vaccine),
        label: 'Mis Vacunas',
        backgroundColor: colorCustom,
      ));
    }

    nabBarItems.add(BottomNavigationBarItem(
      icon: Icon(Icons.info),
      label: 'Monitor Vacunacion',
      backgroundColor: colorCustom,
    ));

    if (isUserAdmin()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.apartment_sharp),
        label: 'Vacunatorios',
        backgroundColor: colorCustom,
      ));
    }

    if (isUserAdmin()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(TablerIcons.vaccine),
        label: 'Vacunas',
        backgroundColor: colorCustom,
      ));
    }

    if (isUserAdmin()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.list_alt),
        label: 'Planes de Vacunacion',
        backgroundColor: colorCustom,
      ));
    }

    if (isUserAdmin()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.directions_boat),
        label: 'Proveedores',
        backgroundColor: colorCustom,
      ));
    }

    if (!(isUserAdmin() || isUserAutoridad())) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.menu_book),
        label: 'Agendas',
        backgroundColor: colorCustom,
      ));
    }

    if (isUserVacunador()) {
      nabBarItems.add(BottomNavigationBarItem(
        icon: Icon(Icons.chat),
        label: 'Chat',
        backgroundColor: colorCustom,
      ));
    }

    if (_selectedIndex == -1) {
      _selectedIndex = getLabelIndex("Monitor Vacunacion");
    }

    return BottomNavigationBar(
      items: nabBarItems,
      currentIndex: _selectedIndex,
      selectedItemColor: Colors.amber[800],
      unselectedItemColor: Colors.white,
      backgroundColor: colorCustom,
      type: BottomNavigationBarType.fixed,
      onTap: _onItemTapped,
      showUnselectedLabels: true,
      selectedFontSize: 16,
    );
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
    onElementSelected(getTab(index));
  }

  Widget getTab(int index) {
    String label = getIndexLabel(index);

    setState(() {
      _selectedIndex = index;
    });

    if (label == 'Monitor Vacunacion') {
      return new MonitorVacunacionTab();
    } else if (label == 'Vacunatorios') {
      return new VacunatorioTab();
    } else if (label == 'Vacunas') {
      return new VacunasTab();
    } else if (label == 'Planes de Vacunacion') {
      return new PlanVacunacionTab();
    } else if (label == 'Proveedores') {
      return new ProveedoresTab();
    } else if (label == 'Agendas') {
      return new AgendaTab();
    } else if (label == 'Enfermedades') {
      return new EnfermedadesTab();
    } else if (label == 'Mis Vacunas') {
      return new MisVacunasTab();
    } else if (label == 'Chat') {
      return new ChatTab();
    } else {
      return new ErrorTab();
    }
  }
}
