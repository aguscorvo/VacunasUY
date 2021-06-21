import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:flutter/material.dart';

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
                title: Text("Iniciar Sesión"),
                bottom: TabBar(
                  tabs: [Tab(text: "Iniciar Sesión con Gub.uy"), Tab(text: "Iniciar Sesión con Backoffice")],
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
                          child: TextButton(
                            onPressed: () async {
                              String url = baseUrl + '/autenticaciongubuy';
                              urlReplace(url);
                              //openLogin(url);
                            },
                            child: Text(
                              'Iniciar Sesión',
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
                            controller: usernameController..text = "", //to set text
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
                            controller: passwordContrller..text = "", //to set text
                            obscureText: true,
                            decoration: InputDecoration(border: OutlineInputBorder(), labelText: 'Contraseña', hintText: 'Ingresar Contraseña'),
                          ),
                        ),
                        TextButton(
                          onPressed: () {
                            //Codigo de Olbido Contraseña
                          },
                          child: Text(
                            '¿Olvidó su contraseña?',
                            style: TextStyle(color: Colors.blue, fontSize: 15),
                          ),
                        ),
                        Container(
                          height: 50,
                          width: 250,
                          decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                          child: TextButton(
                            onPressed: () async {
                              bool valid = false;
                              var client = BackendConnection();
                              valid = await client.login(
                                correo: usernameController.text,
                                password: passwordContrller.text,
                              );
                              if (valid) {
                                appReload();
                              }
                            },
                            child: Text(
                              'Iniciar Sesión',
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
