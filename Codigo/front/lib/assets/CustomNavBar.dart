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
import 'package:vacunas_uy/tools/StaticTabs.dart';

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
  })  : preferredSize = Size.fromHeight(kToolbarHeight),
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
      label: 'Monitor Vacunación',
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
      _selectedIndex = getLabelIndex("Monitor Vacunación");
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
    int lastIndex = _selectedIndex;
    setState(() {
      _selectedIndex = index;
    });
    onElementSelected(getTab(index, lastIndex));
  }

  Widget getTab(int index, int lastIndex) {
    String label = getIndexLabel(index);

    setState(() {
      _selectedIndex = index;
    });

    if (label == 'Monitor Vacunación') {
      if (index == lastIndex) {
        return StaticTabs.monitor = new MonitorVacunacionTab();
      } else {
        return StaticTabs.monitor;
      }
    } else if (label == 'Vacunatorios') {
      if (index == lastIndex) {
        return StaticTabs.vacunatorios = new VacunatorioTab();
      } else {
        return StaticTabs.vacunatorios;
      }
    } else if (label == 'Vacunas') {
      if (index == lastIndex) {
        return StaticTabs.vacunas = new VacunasTab();
      } else {
        return StaticTabs.vacunas;
      }
    } else if (label == 'Planes de Vacunacion') {
      if (index == lastIndex) {
        return StaticTabs.planVacunacion = new PlanVacunacionTab();
      } else {
        return StaticTabs.planVacunacion;
      }
    } else if (label == 'Proveedores') {
      if (index == lastIndex) {
        return StaticTabs.proveedores = new ProveedoresTab();
      } else {
        return StaticTabs.proveedores;
      }
    } else if (label == 'Agendas') {
      if (index == lastIndex) {
        return StaticTabs.agenda = new AgendaTab();
      } else {
        return StaticTabs.agenda;
      }
    } else if (label == 'Enfermedades') {
      if (index == lastIndex) {
        return StaticTabs.enfermedades = new EnfermedadesTab();
      } else {
        return StaticTabs.enfermedades;
      }
    } else if (label == 'Mis Vacunas') {
      if (index == lastIndex) {
        return StaticTabs.misVacunas = new MisVacunasTab();
      } else {
        return StaticTabs.misVacunas;
      }
    } else if (label == 'Chat') {
      if (index == lastIndex) {
        return StaticTabs.chat = new ChatTab();
      } else {
        return StaticTabs.chat;
      }
    } else {
      if (index == lastIndex) {
        return StaticTabs.error = new ErrorTab();
      } else {
        return StaticTabs.error;
      }
    }
  }
}
