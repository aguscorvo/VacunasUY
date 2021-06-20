class Enfermedad {
  int id = -1;
  String nombre = "";

  Enfermedad();

  Enfermedad.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
}
