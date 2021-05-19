import 'package:flutter/material.dart';
import 'package:VacunasUY/tools/UserCredentials.dart';
import 'dart:html' as html;

class Profile extends StatefulWidget {
  @override
  _ProfileState createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: new Center(
        child: new Column(mainAxisAlignment: MainAxisAlignment.start, mainAxisSize: MainAxisSize.max, crossAxisAlignment: CrossAxisAlignment.center, children: <Widget>[
          new Row(children: <Widget>[new Text("Nombre:"), new Text(storedUserCredentials.userData.nombre)]),
          new Row(children: <Widget>[new Text("Apellido:"), new Text(storedUserCredentials.userData.apellido)]),
          new Row(children: <Widget>[new Text("Correo:"), new Text(storedUserCredentials.userData.correo)]),
          new Row(children: <Widget>[new Text("Documento:"), new Text(storedUserCredentials.userData.documento)]),
          new Row(children: <Widget>[new Text("Fecha Nacimiento:"), new Text(storedUserCredentials.userData.fechaNacimiento)]),
          new Row(children: <Widget>[
            Container(
              height: 50,
              width: 250,
              decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
              child: FlatButton(
                onPressed: () async {
                  //todo
                  storedUserCredentials = logedOffUser;
                  saveUserCredentials();
                  if (html.window.location.toString().contains("/?code")) {
                    html.window.location.replace(html.window.location.toString().split("/?code")[0]);
                  } else {
                    html.window.location.reload();
                  }
                },
                child: Text(
                  'Cerrar Sesion',
                  style: TextStyle(color: Colors.white, fontSize: 25),
                ),
              ),
            ),
          ]),
        ]),
      ),
    );
  }
}
