class Localidad {
  int id = -1;
  String nombre = "";

  Localidad();
  Localidad.all(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  Localidad.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }
  Map<String, dynamic> toJson() => {
        'id': id,
        'nombre': nombre,
      };
}
