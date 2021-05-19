import 'package:VacunasUY/tools/BackendConnection.dart';
import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:VacunasUY/tools/UserCredentials.dart';

class Login extends StatefulWidget {
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  final usernameController = new TextEditingController();
  final passwordContrller = new TextEditingController();

  @override
  Widget build(BuildContext context) {
    double width = MediaQuery.of(context).size.width;
    return DefaultTabController(
        length: 2,
        child: Scaffold(
            appBar: AppBar(
                title: Text("Iniciar Sesion"),
                bottom: TabBar(
                  tabs: [Tab(text: "Iniciar Sesion con Gub.uy"), Tab(text: "Iniciar Sesion con Backoffice")],
                )),
            body: TabBarView(
              children: [
                Scaffold(
                  backgroundColor: Colors.white,
                  body: SingleChildScrollView(
                    child: Column(
                      children: <Widget>[
                        Padding(
                          padding: const EdgeInsets.only(top: 60.0),
                          child: Center(
                            child: Container(width: 200, height: 150, child: Image.asset('assets/logo/gubUY.png')),
                          ),
                        ),
                        Container(
                          height: 50,
                          width: 250,
                          decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                          child: FlatButton(
                            onPressed: () async {
                              String url = baseUrl + '/autenticaciongubuy';
                              launch(url);
                            },
                            child: Text(
                              'Iniciar Sesion',
                              style: TextStyle(color: Colors.white, fontSize: 25),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 130,
                        ),
                      ],
                    ),
                  ),
                ),
                Scaffold(
                  backgroundColor: Colors.white,
                  body: SingleChildScrollView(
                    child: Column(
                      children: <Widget>[
                        Padding(
                          padding: const EdgeInsets.only(top: 60.0),
                          child: Center(
                            child: Container(width: 200, height: 150, child: Image.asset('assets/logo/vacunasUY.png')),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: (width - 400) / 2),
                          child: TextFormField(
                            controller: usernameController..text = "jo.cs1998@hotmail.com",
                            decoration: InputDecoration(
                              border: OutlineInputBorder(),
                              labelText: 'E-Mail',
                              hintText: 'Ingresar un correo valido como abc@gmail.com',
                            ),
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.symmetric(horizontal: (width - 400) / 2, vertical: 15),
                          child: TextFormField(
                            controller: passwordContrller..text = "Chiquito",
                            obscureText: true,
                            decoration: InputDecoration(border: OutlineInputBorder(), labelText: 'Contraseña', hintText: 'Ingresar Contraseña'),
                          ),
                        ),
                        FlatButton(
                          onPressed: () {
                            //Codigo de Olbido Contraseña
                          },
                          child: Text(
                            'Olbido su contraseña?',
                            style: TextStyle(color: Colors.blue, fontSize: 15),
                          ),
                        ),
                        Container(
                          height: 50,
                          width: 250,
                          decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                          child: FlatButton(
                            onPressed: () async {
                              bool valid = false;
                              var client = BackendConnection();
                              valid = await client.login(
                                correo: usernameController.text,
                                password: passwordContrller.text,
                              );
                            },
                            child: Text(
                              'Iniciar Sesion',
                              style: TextStyle(color: Colors.white, fontSize: 25),
                            ),
                          ),
                        ),
                        SizedBox(
                          height: 130,
                        ),
                        Text('Usuario Nuevo? Crear Cuenta')
                      ],
                    ),
                  ),
                )
              ],
            )));
  }
}
