class Pais {
  int id = -1;
  String nombre = "";

  Pais();

  Pais.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
}
