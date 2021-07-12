class MonitorVacuna {
  Map<int, int> agendas = {};
  Map<int, int> vacunas = {};

  MonitorVacuna();

  MonitorVacuna.fromJson(Map<String, dynamic> json) {
    try {
      List<dynamic> agendasTemp = json["agendas"];
      List<dynamic> vacunasTemp = json["vacunados"];

      agendas = {};
      vacunas = {};

      agendasTemp.forEach((element) {
        agendas.addAll({
          element[0]: element[1],
        });
      });

      vacunasTemp.forEach((element) {
        vacunas.addAll({
          element[0]: element[1],
        });
      });
    } catch (err) {
      print(err);
    }
  }
}
