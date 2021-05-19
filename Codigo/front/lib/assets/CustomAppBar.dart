import 'package:VacunasUY/paginas/Profile.dart';
import 'package:flutter/material.dart';

import '../paginas/Login.dart';
import '../paginas/Profile.dart';

import 'package:VacunasUY/tools/UserCredentials.dart';

class CustomAppBar extends StatefulWidget implements PreferredSizeWidget {
  final String title;
  final Function(Widget) onElementSelected;

  CustomAppBar({Key key, this.title, @required this.onElementSelected})
      : preferredSize = Size.fromHeight(kToolbarHeight),
        super(key: key);

  @override
  final Size preferredSize; // default is 56.0

  @override
  _CustomAppBarState createState() => _CustomAppBarState(onElementSelected);
}

class _CustomAppBarState extends State<CustomAppBar> {
  final Function(Widget) onElementSelected;
  _CustomAppBarState(this.onElementSelected);

  @override
  Widget build(BuildContext context) {
    if (storedUserCredentials.token != '') {
      return getLogedAppBar(context, widget);
    } else {
      return getNoLogedAppBar(context, widget);
    }
  }

  Widget getNoLogedAppBar(BuildContext context, CustomAppBar widget) {
    return AppBar(
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: const EdgeInsets.all(0.0),
            child: Text("  " + widget.title),
          ),
          Container(
            padding: const EdgeInsets.all(0.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  padding: const EdgeInsets.all(0.0),
                  child: Text("Iniciar Sesion"),
                ),
                RawMaterialButton(
                  onPressed: () {
                    onElementSelected(Login());
                    //Navigator.push(context, MaterialPageRoute(builder: (_) => Login()));
                  },
                  elevation: 2.0,
                  fillColor: Colors.blue,
                  child: Icon(
                    Icons.account_circle_sharp,
                    size: 40.0,
                  ),
                  padding: EdgeInsets.all(0.0),
                  shape: CircleBorder(),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }

  AppBar getLogedAppBar(BuildContext context, CustomAppBar widget) {
    return AppBar(
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: const EdgeInsets.all(0.0),
            child: Text("  " + widget.title),
          ),
          Container(
            padding: const EdgeInsets.all(0.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  padding: const EdgeInsets.all(0.0),
                  child: Text(storedUserCredentials.userData.nombre + ' ' + storedUserCredentials.userData.apellido),
                ),
                RawMaterialButton(
                  onPressed: () {
                    onElementSelected(Profile());
                    //Navigator.push(context, MaterialPageRoute(builder: (_) => Profile()));
                  },
                  elevation: 2.0,
                  fillColor: Colors.blue,
                  child: Icon(
                    Icons.account_circle_sharp,
                    size: 40.0,
                  ),
                  padding: EdgeInsets.all(0.0),
                  shape: CircleBorder(),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
