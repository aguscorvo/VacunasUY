import 'package:vacunas_uy/objects/PlanVacunacion.dart';

class MonitorPlan {
  int cantidadAgendasHoy = -1;
  int cantidadVacunadosHoy = -1;
  int cantidadTotalVacunados = -1;
  PlanVacunacion planes = PlanVacunacion();

  MonitorPlan();

  MonitorPlan.fromJson(Map<String, dynamic> json) {
    cantidadAgendasHoy = json['cantidadAgendasHoy'];
    cantidadVacunadosHoy = json['cantidadVacunadosHoy'];
    cantidadTotalVacunados = json['cantidadTotalVacunados'];
    planes = PlanVacunacion.fromJson(json['plan']);
  }
}
