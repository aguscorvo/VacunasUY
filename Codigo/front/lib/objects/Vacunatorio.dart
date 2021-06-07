import 'Localidad.dart';
import 'Departamento.dart';
import 'Puesto.dart';

class Vacunatorio {
  int id;
  String nombre;
  double latitud;
  double longitud;
  String direccion;
  Localidad localidad;
  Departamento departamento;
  List<Puesto> puestos;

  Vacunatorio({this.id, this.nombre, this.latitud, this.longitud, this.direccion, this.localidad, this.departamento, this.puestos});

  Vacunatorio.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    latitud = json['latitud'];
    longitud = json['longitud'];
    direccion = json['direccion'];
    localidad = Localidad.fromJson(json['localidad']);
    departamento = Departamento.fromJsonShort(json['departamento']);

    puestos = [];
    if (json['puestos'] != null) {
      json['puestos'].forEach((puesto) => {puestos.add(Puesto.fromJson(puesto))});
    }
  }
}
