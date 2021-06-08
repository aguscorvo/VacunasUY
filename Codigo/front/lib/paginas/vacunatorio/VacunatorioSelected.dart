import 'dart:convert';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import "package:latlong/latlong.dart" as LatLng;

class VacunatorioSelected extends StatefulWidget {
  final Vacunatorio vacun;
  final String mode;
  VacunatorioSelected({@required this.vacun, @required this.mode});

  static _VacunatorioSelectedState state;
  @override
  _VacunatorioSelectedState createState() => state = _VacunatorioSelectedState(vacun, mode);
}

class _VacunatorioSelectedState extends State<VacunatorioSelected> {
  _VacunatorioSelectedState(this.vacun, this.mode);

  Vacunatorio vacun;
  String mode;
  FlutterMap map;
  MapController controller = MapController();

  void changeVacunatorio(Vacunatorio newVacun) {
    setState(() {
      vacun = newVacun;
      if (map != null) {
        controller.move(LatLng.LatLng(vacun.latitud, vacun.longitud), map.options.zoom);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    String nombre = "Seleccione un Vacunatorio";

    if (this.vacun != null) {
      nombre = this.vacun.nombre;
    }

    return Expanded(
      flex: 3,
      child: Container(
        padding: const EdgeInsets.fromLTRB(0.0, 100, 25.0, 25.0),
        child: Column(
          children: [
            Text(
              nombre,
              style: TextStyle(fontSize: 20),
            ),
            vacun != null
                ? Material(
                    elevation: 10,
                    child: Column(
                      children: [
                        Text(
                          "Ubicacion",
                          style: TextStyle(fontSize: 15),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text("Departamento"),
                            Text("Localidad"),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text(vacun.departamento.nombre),
                            Text(vacun.localidad.nombre),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text("Direccion"),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text(vacun.direccion),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text("Latitud"),
                            Text("Longitud"),
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            Text(vacun.latitud.toString()),
                            Text(vacun.longitud.toString()),
                          ],
                        ),
                        Container(
                          width: 250,
                          height: 250,
                          padding: const EdgeInsets.fromLTRB(0, 20, 0, 20),
                          child: map = new FlutterMap(
                            mapController: controller,
                            options: new MapOptions(
                              center: new LatLng.LatLng(vacun.latitud, vacun.longitud),
                              zoom: 13.0,
                            ),
                            layers: [
                              new TileLayerOptions(urlTemplate: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", subdomains: ['a', 'b', 'c']),
                              new MarkerLayerOptions(
                                markers: [
                                  new Marker(
                                    width: 25.0,
                                    height: 25.0,
                                    point: new LatLng.LatLng(vacun.latitud, vacun.longitud),
                                    builder: (ctx) => new Container(
                                      child: new FlutterLogo(),
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  )
                : Material(),
          ],
        ),
      ),
    );
  }
}
