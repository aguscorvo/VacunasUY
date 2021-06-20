class ActoVacunal {
  String fecha = "";
  String nombre = "";

  ActoVacunal({required this.fecha, required this.nombre});

  ActoVacunal.fromJson(Map<String, dynamic> json) {
    fecha = json['fecha'];
    nombre = json['nombre'];
  }
}
