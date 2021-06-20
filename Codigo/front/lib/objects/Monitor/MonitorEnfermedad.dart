class MonitorEnfermedad {
  int cantidadAgendasHoy = -1;
  int cantidadVacunadosHoy = -1;
  List<VacunaSimp> vacunas = [];
  List<PlanSimp> planes = [];

  MonitorEnfermedad();

  MonitorEnfermedad.fromJson(Map<String, dynamic> json) {
    cantidadAgendasHoy = json['cantidadAgendasHoy'];
    cantidadVacunadosHoy = json['cantidadVacunadosHoy'];
    List<dynamic> vacunasJ = json['vacunas'];
    List<dynamic> planesJ = json['planes'];

    this.vacunas = [];
    this.planes = [];

    vacunasJ.forEach((value) {
      vacunas.add(VacunaSimp(cantidad: value[1], nombre: value[0]));
    });
    planesJ.forEach((value) {
      planes.add(PlanSimp(id: value[0], nombre: value[2], rangoFecha: value[1]));
    });
  }
}

class VacunaSimp {
  String nombre = "";
  int cantidad = -1;

  VacunaSimp({
    required this.nombre,
    required this.cantidad,
  });

  VacunaSimp.fromJson(Map<String, dynamic> json) {
    nombre = json['nombre'];
    cantidad = json['cantidad'];
  }

  Map<String, dynamic> toJson() => {
        'nombre': nombre,
        'cantidad': cantidad,
      };
}

class PlanSimp {
  int id = -1;
  String rangoFecha = "";
  String nombre = "";

  PlanSimp({
    required this.id,
    required this.rangoFecha,
    required this.nombre,
  });

  PlanSimp.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    rangoFecha = json['rangoFecha'];
    nombre = json['nombre'];
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'rangoFecha': rangoFecha,
        'nombre': nombre,
      };
}
