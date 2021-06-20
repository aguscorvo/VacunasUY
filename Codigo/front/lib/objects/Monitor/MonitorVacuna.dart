import 'dart:convert';

class MonitorVacuna {
  List<dynamic> agendas = [];
  List<dynamic> vacunas = [];

  MonitorVacuna();

  MonitorVacuna.fromJson(Map<String, dynamic> json) {
    agendas = jsonDecode(json["agendas"]);
    vacunas = jsonDecode(json["vacunas"]);
  }
}
