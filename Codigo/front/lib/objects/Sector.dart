class Sector {
  int id;
  String nombre;

  Sector({this.id, this.nombre});

  Sector.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'];
  }

  Map<String, dynamic> toJson() => {
        'id': id ?? "",
        'nombre': nombre ?? "",
      };
}
