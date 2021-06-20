import 'Pais.dart';

class Proveedor {
  int id = -1;
  String nombre = "";
  Pais pais = Pais();

  Proveedor();

  Proveedor.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
    pais = Pais.fromJson(json['pais']);
  }
}
