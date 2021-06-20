class Sector {
  int id = -1;
  String nombre = "";

  Sector();
  Sector.all(this.id, this.nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  Sector.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'nombre': nombre,
      };
}
