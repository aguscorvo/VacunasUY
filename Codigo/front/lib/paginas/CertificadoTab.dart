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
    bool portrait = false;
    if (MediaQuery.of(context).size.width < MediaQuery.of(context).size.height) {
      portrait = true;
    }
    EdgeInsets edgesA = portrait ? EdgeInsets.symmetric(horizontal: 5, vertical: 5) : EdgeInsets.symmetric(horizontal: 50, vertical: 5);
    EdgeInsets edgesB = portrait ? EdgeInsets.all(2) : EdgeInsets.all(5);
    EdgeInsets edgesC = portrait ? EdgeInsets.all(5) : EdgeInsets.all(10);
    TextStyle styleA = portrait ? TextStyle(fontSize: 14) : TextStyle(fontSize: 25);
    TextStyle styleB = portrait ? TextStyle(fontSize: 18) : TextStyle(fontSize: 25);
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
            left: portrait ? MediaQuery.of(context).size.width * 0.02 : MediaQuery.of(context).size.width * 0.2,
            right: portrait ? MediaQuery.of(context).size.width * 0.02 : MediaQuery.of(context).size.width * 0.2,
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
                        padding: edgesC,
                        width: MediaQuery.of(context).size.width - 60,
                        margin: edgesC,
                        child: Column(
                          children: [
                            Text(
                              "Certificado de Vacunación",
                              style: styleB,
                            ),
                          ],
                        ),
                      ),
                    ),
                    portrait ? SizedBox(height: 10) : SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        padding: edgesC,
                        width: MediaQuery.of(context).size.width - 60,
                        margin: edgesC,
                        child: Column(
                          children: [
                            Text(
                              enf.nombre,
                              style: styleA,
                            ),
                          ],
                        ),
                      ),
                    ),
                    portrait ? SizedBox(height: 10) : SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        height: (MediaQuery.of(context).size.height * 0.2) > 175 ? 175 : MediaQuery.of(context).size.height * 0.2,
                        width: MediaQuery.of(context).size.width - 60,
                        margin: edgesC,
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
                                      padding: edgesB,
                                      margin: edgesA,
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
                                              style: styleA,
                                            ),
                                            Text(
                                              usu.nombre,
                                              style: styleA,
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: edgesB,
                                      margin: edgesA,
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
                                              style: styleA,
                                            ),
                                            Text(
                                              usu.apellido,
                                              style: styleA,
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
                                      padding: edgesB,
                                      margin: edgesA,
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
                                              style: styleA,
                                            ),
                                            Text(
                                              usu.documento,
                                              style: styleA,
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: edgesB,
                                      margin: edgesA,
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
                                              style: styleA,
                                            ),
                                            Text(
                                              usu.sectorLaboral.nombre,
                                              style: styleA,
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
                    portrait ? SizedBox(height: 10) : SizedBox(height: 20),
                    Material(
                      elevation: 10,
                      child: Container(
                        width: MediaQuery.of(context).size.width - 60,
                        height: portrait ? 200 : 500,
                        margin: edgesC,
                        child: actosVacunales.length == 0
                            ? Center(child: Text("No tiene actos vacunales para la enfermedad"))
                            : ListView.builder(
                                itemCount: actosVacunales.length,
                                reverse: false,
                                padding: edgesB,
                                itemExtent: portrait ? 60 : 163,
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
                                          style: styleA,
                                        ),
                                        Text(
                                          "Dosis: " + (index + 1).toString() + "/" + actosVacunales.length.toString(),
                                          style: styleA,
                                        ),
                                        Text(
                                          "Fecha: " + ac.fecha,
                                          style: styleA,
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
