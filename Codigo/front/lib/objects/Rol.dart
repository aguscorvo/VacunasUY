class Rol {
  int id;
  String nombre;

  Rol({
    required this.id,
    required this.nombre,
  });

  Rol.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        nombre = json['nombre'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'nombre': nombre,
      };
}
