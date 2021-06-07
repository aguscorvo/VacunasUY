import 'Pais.dart';

class Proveedor {
  int id;
  String nombre;
  Pais pais;

  Proveedor({this.id, this.nombre, this.pais});

  Proveedor.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    pais = Pais.fromJson(json['pais']);
  }
}
