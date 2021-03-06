import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:intl/intl.dart';
import 'package:date_field/date_field.dart';
import 'package:vacunas_uy/assets/CustomAppBar.dart';

class Profile extends StatefulWidget {
  @override
  _ProfileState createState() => _ProfileState();
}

class _ProfileState extends State<Profile> with SingleTickerProviderStateMixin {
  bool _status = true;
  final FocusNode myFocusNode = FocusNode();

  final nombreController = new TextEditingController();
  final apellidoController = new TextEditingController();
  final correoController = new TextEditingController();
  final documentoController = new TextEditingController();
  final fechaNacimientoController = new TextEditingController();

  final BackendConnection client = BackendConnection();

  final format = DateFormat("yyyy-MM-dd");

  DateTime? fechaNacimiento;
  DateTime? fechaNacimientoOld;
  DateTimeFormField? datetimeform;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: Material(
          elevation: 10,
          child: Center(
            child: new Container(
              width: MediaQuery.of(context).size.width * 0.5,
              color: Colors.white,
              child: FutureBuilder(
                future: client.getUsuarioToken(storedUserCredentials!.token!),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      Usuario usu = snapshot.data as Usuario;
                      storedUserCredentials!.userData = usu;
                      saveUserCredentials();
                      nombreController..text = usu.nombre;
                      apellidoController..text = usu.apellido;
                      correoController..text = usu.correo;
                      documentoController..text = usu.documento;
                      if (fechaNacimiento == null) {
                        fechaNacimientoOld = usu.fechaNacimiento;
                      }
                      fechaNacimiento = usu.fechaNacimiento;
                      return new ListView(
                        children: <Widget>[
                          Column(
                            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            children: <Widget>[
                              Container(
                                padding: const EdgeInsets.fromLTRB(0, 25, 0, 25),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: getBanners(),
                                ),
                              ),
                              new Container(
                                color: Color(0xffFFFFFF),
                                child: Padding(
                                  padding: EdgeInsets.only(bottom: 25.0),
                                  child: new Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: <Widget>[
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 25.0),
                                          child: new Row(
                                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Column(
                                                mainAxisAlignment: MainAxisAlignment.start,
                                                mainAxisSize: MainAxisSize.min,
                                                children: <Widget>[
                                                  new Text(
                                                    'Informaci??n personal',
                                                    style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ],
                                              ),
                                              new Column(
                                                mainAxisAlignment: MainAxisAlignment.end,
                                                mainAxisSize: MainAxisSize.min,
                                                children: <Widget>[
                                                  _status ? _getEditIcon() : new Container(),
                                                ],
                                              )
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 25.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            mainAxisAlignment: MainAxisAlignment.start,
                                            children: <Widget>[
                                              Expanded(
                                                child: Container(
                                                  child: new Text(
                                                    'Nombres',
                                                    style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ),
                                                flex: 2,
                                              ),
                                              Expanded(
                                                child: Container(
                                                  child: new Text(
                                                    'Apellidos',
                                                    style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ),
                                                flex: 2,
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 2.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            mainAxisAlignment: MainAxisAlignment.start,
                                            children: <Widget>[
                                              Flexible(
                                                child: Padding(
                                                  padding: EdgeInsets.only(right: 10.0),
                                                  child: new TextField(
                                                    controller: nombreController,
                                                    decoration: const InputDecoration(hintText: "Nombres"),
                                                    enabled: !_status,
                                                  ),
                                                ),
                                                flex: 2,
                                              ),
                                              Flexible(
                                                child: new TextField(
                                                  controller: apellidoController,
                                                  decoration: const InputDecoration(hintText: "Apellidos"),
                                                  enabled: !_status,
                                                ),
                                                flex: 2,
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 25.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Column(
                                                mainAxisAlignment: MainAxisAlignment.start,
                                                mainAxisSize: MainAxisSize.min,
                                                children: <Widget>[
                                                  new Text(
                                                    'Correo',
                                                    style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ],
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 2.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Flexible(
                                                child: new TextField(
                                                  controller: correoController,
                                                  decoration: const InputDecoration(hintText: "Correo"),
                                                  enabled: !_status,
                                                ),
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 25.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Column(
                                                mainAxisAlignment: MainAxisAlignment.start,
                                                mainAxisSize: MainAxisSize.min,
                                                children: <Widget>[
                                                  new Text(
                                                    'Documento',
                                                    style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ],
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 2.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Flexible(
                                                child: new TextField(
                                                  controller: documentoController,
                                                  decoration: const InputDecoration(hintText: "Documento"),
                                                  enabled: !_status,
                                                ),
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 25.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Column(
                                                mainAxisAlignment: MainAxisAlignment.start,
                                                mainAxisSize: MainAxisSize.min,
                                                children: <Widget>[
                                                  new Text(
                                                    'Fecha de nacimiento',
                                                    style: TextStyle(fontSize: 16.0, fontWeight: FontWeight.bold),
                                                  ),
                                                ],
                                              ),
                                            ],
                                          )),
                                      Padding(
                                          padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 2.0),
                                          child: new Row(
                                            mainAxisSize: MainAxisSize.max,
                                            children: <Widget>[
                                              new Flexible(
                                                child: datetimeform = DateTimeFormField(
                                                  mode: DateTimeFieldPickerMode.date,
                                                  autovalidateMode: AutovalidateMode.always,
                                                  decoration: const InputDecoration(hintText: "Fecha de nacimiento"),
                                                  enabled: !_status,
                                                  initialValue: fechaNacimiento,
                                                  onDateSelected: (value) => {fechaNacimiento = value},
                                                ),
                                              ),
                                            ],
                                          )),
                                      !_status ? _getActionButtons() : new Container(),
                                    ],
                                  ),
                                ),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: <Widget>[
                                  Container(
                                    height: 50,
                                    width: 250,
                                    decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                                    child: TextButton(
                                      onPressed: () async {
                                        storedUserCredentials = logedOffUser;
                                        await deleteUserCredentials();
                                        await appReload();
                                      },
                                      child: Text(
                                        'Cerrar sesi??n',
                                        style: TextStyle(color: Colors.white, fontSize: 25),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ],
                      );
                    }
                  }
                },
              ),
            ),
          )),
    );
  }

  @override
  void dispose() {
    // Clean up the controller when the Widget is disposed
    myFocusNode.dispose();
    super.dispose();
  }

  Widget _getActionButtons() {
    return Padding(
      padding: EdgeInsets.only(left: 25.0, right: 25.0, top: 45.0),
      child: new Row(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.start,
        children: <Widget>[
          Expanded(
            child: Padding(
              padding: EdgeInsets.only(right: 10.0),
              child: Container(
                  child: new ElevatedButton(
                child: new Text("Guardar"),
                style: ElevatedButton.styleFrom(
                  shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(20.0)),
                  primary: Colors.green, // background
                  onPrimary: Colors.white, // foreground
                ),
                onPressed: () async {
                  // ignore: await_only_futures
                  Usuario cambios = new Usuario.partial(
                    storedUserCredentials!.userData!.id,
                    nombreController.text,
                    apellidoController.text,
                    correoController.text,
                    documentoController.text,
                    fechaNacimiento!,
                    storedUserCredentials!.userData!.roles,
                  );
                  if (await client.actualizarDatosUsuario(cambios)) {
                    CustomAppBar.appbarstate!.userNameChange(cambios.nombre + " " + cambios.apellido);
                  }
                  setState(() {
                    _status = true;
                    FocusScope.of(context).requestFocus(new FocusNode());
                  });
                },
              )),
            ),
            flex: 2,
          ),
          Expanded(
            child: Padding(
              padding: EdgeInsets.only(left: 10.0),
              child: Container(
                  child: new ElevatedButton(
                child: new Text("Cancelar"),
                style: ElevatedButton.styleFrom(
                  shape: new RoundedRectangleBorder(borderRadius: new BorderRadius.circular(20.0)),
                  primary: Colors.red, // background
                  onPrimary: Colors.white, // foreground
                ),
                onPressed: () async {
                  datetimeform = DateTimeFormField(
                    mode: DateTimeFieldPickerMode.date,
                    autovalidateMode: AutovalidateMode.always,
                    decoration: const InputDecoration(hintText: "Fecha de nacimiento"),
                    enabled: !_status,
                    initialValue: fechaNacimientoOld,
                    onDateSelected: (value) => {fechaNacimiento = value},
                  );
                  // ignore: await_only_futures
                  nombreController..text = storedUserCredentials!.userData!.nombre;
                  apellidoController..text = storedUserCredentials!.userData!.apellido;
                  correoController..text = storedUserCredentials!.userData!.correo;
                  documentoController..text = storedUserCredentials!.userData!.documento;
                  setState(() {
                    _status = true;
                    FocusScope.of(context).requestFocus(new FocusNode());
                  });
                },
              )),
            ),
            flex: 2,
          ),
        ],
      ),
    );
  }

  Widget _getEditIcon() {
    return new GestureDetector(
      child: new CircleAvatar(
        backgroundColor: Colors.red,
        radius: 14.0,
        child: new Icon(
          Icons.edit,
          color: Colors.white,
          size: 16.0,
        ),
      ),
      onTap: () {
        setState(() {
          _status = false;
        });
      },
    );
  }

  List<Widget> getBanners() {
    List<Widget> toReturn = [];

    if (isUserAdmin()) {
      toReturn.add(Container(
        height: 30,
        width: 150,
        decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(15)),
        child: Center(
            child: Text(
          'Administrador',
          style: TextStyle(color: Colors.white, fontSize: 15),
        )),
      ));
    }
    if (isUserAutoridad()) {
      toReturn.add(Container(
        height: 30,
        width: 150,
        decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(15)),
        child: Center(
            child: Text(
          'Autoridad',
          style: TextStyle(color: Colors.white, fontSize: 15),
        )),
      ));
    }
    if (isUserVacunador()) {
      toReturn.add(Container(
        height: 30,
        width: 150,
        decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(15)),
        child: Center(
            child: Text(
          'Vacunador',
          style: TextStyle(color: Colors.white, fontSize: 15),
        )),
      ));
    }
    if (isUserCiudadano()) {
      toReturn.add(Container(
        height: 30,
        width: 150,
        decoration: BoxDecoration(color: Colors.blue, borderRadius: BorderRadius.circular(15)),
        child: Center(
            child: Text(
          'Ciudadano',
          style: TextStyle(color: Colors.white, fontSize: 15),
        )),
      ));
    }

    return toReturn;
  }
}
