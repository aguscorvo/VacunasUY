import 'dart:typed_data';

import 'package:vacunas_uy/objects/ActoVacunal.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/objects/Usuario.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/tools/CertificadoInfo.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:screenshot/screenshot.dart';

class CertificadoTab extends StatefulWidget {
  @override
  _CertificadoTabState createState() => _CertificadoTabState();
}

class _CertificadoTabState extends State<CertificadoTab> {
  BackendConnection client = BackendConnection();
  late Enfermedad enfermedad;

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: client.getEnfermedades(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        } else {
          if (snapshot.data == null) {
            return Center(child: CircularProgressIndicator());
          } else {
            List<Enfermedad> enfermedadesTemp = snapshot.data as List<Enfermedad>;
            enfermedadesTemp.forEach((Enfermedad element) {
              if (element.id == CertificadoInfo.idEnf) {
                enfermedad = element;
              }
            });
            return FutureBuilder(
              future: client.getActosVacunalesUsuarioPorEnfermedad(CertificadoInfo.idUsu, enfermedad.id),
              builder: (context, snapshot) {
                if (snapshot.connectionState != ConnectionState.done) {
                  return Center(child: CircularProgressIndicator());
                } else {
                  if (snapshot.data == null) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    List<ActoVacunal> actosVacunales = snapshot.data as List<ActoVacunal>;
                    return FutureBuilder(
                      future: client.getUsuarioPorId(CertificadoInfo.idUsu),
                      builder: (context, snapshot) {
                        if (snapshot.connectionState != ConnectionState.done) {
                          return Center(child: CircularProgressIndicator());
                        } else {
                          if (snapshot.data == null) {
                            return Center(child: CircularProgressIndicator());
                          } else {
                            Usuario usuario = snapshot.data as Usuario;
                            return Certificado(usuario, enfermedad, actosVacunales);
                          }
                        }
                      },
                    );
                  }
                }
              },
            );
          }
        }
      },
    );
  }
}

// ignore: must_be_immutable
class Certificado extends StatelessWidget {
  late Usuario usu;
  late Enfermedad enf;
  late List<ActoVacunal> actosVacunales;
  Certificado(this.usu, this.enf, this.actosVacunales);

  ScreenshotController screenshotController = ScreenshotController();

  @override
  Widget build(BuildContext context) {
    actosVacunales.sort((a, b) {
      var adate = a.fecha;
      var bdate = b.fecha;
      return adate.compareTo(bdate);
    });
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: generatePDF,
        child: const Icon(
          Icons.picture_as_pdf,
          size: 35,
        ),
        backgroundColor: Colors.red,
      ),
      body: Material(
        elevation: 10,
        child: Container(
          width: MediaQuery.of(context).size.width,
          padding: EdgeInsets.only(
            top: 25,
            left: MediaQuery.of(context).size.width * 0.2,
            right: MediaQuery.of(context).size.width * 0.2,
          ),
          child: Screenshot(
            controller: screenshotController,
            child: Material(
              elevation: 10,
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    Material(
                      elevation: 10,
                      child: Container(
                        padding: EdgeInsets.all(10),
                        width: MediaQuery.of(context).size.width - 60,
                        margin: EdgeInsets.all(10),
                        child: Column(
                          children: [
                            Text(
                              "Certificado de Vacunación",
                              style: TextStyle(fontSize: 25),
                            ),
                          ],
                        ),
                      ),
                    ),
                    SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        padding: EdgeInsets.all(10),
                        width: MediaQuery.of(context).size.width - 60,
                        margin: EdgeInsets.all(10),
                        child: Column(
                          children: [
                            Text(
                              enf.nombre,
                              style: TextStyle(fontSize: 25),
                            ),
                          ],
                        ),
                      ),
                    ),
                    SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        height: (MediaQuery.of(context).size.height * 0.2) > 175 ? 175 : MediaQuery.of(context).size.height * 0.2,
                        width: MediaQuery.of(context).size.width - 60,
                        margin: EdgeInsets.all(10),
                        child: Container(
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceAround,
                                children: [
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      margin: EdgeInsets.symmetric(horizontal: 50, vertical: 5),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(Radius.circular(16.0)),
                                      ),
                                      child: Material(
                                        elevation: 10,
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                                          children: [
                                            Text(
                                              "Nombres: ",
                                              style: TextStyle(fontSize: 25),
                                            ),
                                            Text(
                                              usu.nombre,
                                              style: TextStyle(fontSize: 25),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      margin: EdgeInsets.symmetric(horizontal: 50, vertical: 5),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(Radius.circular(16.0)),
                                      ),
                                      child: Material(
                                        elevation: 10,
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                                          children: [
                                            Text(
                                              "Apellidos: ",
                                              style: TextStyle(fontSize: 25),
                                            ),
                                            Text(
                                              usu.apellido,
                                              style: TextStyle(fontSize: 25),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceAround,
                                children: [
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      margin: EdgeInsets.symmetric(horizontal: 50, vertical: 5),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(Radius.circular(16.0)),
                                      ),
                                      child: Material(
                                        elevation: 10,
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                                          children: [
                                            Text(
                                              "Documento: ",
                                              style: TextStyle(fontSize: 25),
                                            ),
                                            Text(
                                              usu.documento,
                                              style: TextStyle(fontSize: 25),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      margin: EdgeInsets.symmetric(horizontal: 50, vertical: 5),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.all(Radius.circular(16.0)),
                                      ),
                                      child: Material(
                                        elevation: 10,
                                        child: Column(
                                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                                          children: [
                                            Text(
                                              "Sector Laboral: ",
                                              style: TextStyle(fontSize: 25),
                                            ),
                                            Text(
                                              usu.sectorLaboral.nombre,
                                              style: TextStyle(fontSize: 25),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                    SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width - 60,
                        height: 500,
                        margin: EdgeInsets.all(10),
                        child: actosVacunales.length == 0
                            ? Center(child: Text("No tiene actos vacunales para la enfermedad"))
                            : ListView.builder(
                                itemCount: actosVacunales.length,
                                reverse: false,
                                padding: EdgeInsets.all(5),
                                itemExtent: 163,
                                itemBuilder: (context, index) {
                                  ActoVacunal ac = actosVacunales[index];
                                  return Card(
                                    margin: EdgeInsets.symmetric(vertical: 10),
                                    elevation: 10,
                                    child: Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                                      children: [
                                        Text(
                                          "Vacuna: " + ac.nombre,
                                          style: TextStyle(fontSize: 25),
                                        ),
                                        Text(
                                          "Dosis: " + (index + 1).toString() + "/" + actosVacunales.length.toString(),
                                          style: TextStyle(fontSize: 25),
                                        ),
                                        Text(
                                          "Fecha: " + ac.fecha,
                                          style: TextStyle(fontSize: 25),
                                        ),
                                      ],
                                    ),
                                  );
                                },
                              ),
                      ),
                    )
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  Future<void> generatePDF() async {
    screenshotController.capture().then((Uint8List? image) => {
          printPDF("Certificado", image!),
        });
  }
}
