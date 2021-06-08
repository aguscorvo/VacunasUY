import 'dart:convert';

import 'Vacunatorio.dart';
import 'Agenda.dart';

class Puesto {
  int id;
  int numero;
  Vacunatorio vacunatorio;
  List<Agenda> agendas;

  Puesto({this.id, this.numero, this.vacunatorio, this.agendas});

  Puesto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    numero = json['numero'];

    if (json['vacunatorio'] != null) {
      vacunatorio = Vacunatorio.fromJson(json['vacunatorio']);
    } else {
      vacunatorio = null;
    }

    agendas = [];
    if (json['agendas'] != null) {
      json['agendas'].forEach((localidad) => {agendas.add(Agenda.fromJson(localidad))});
    }
  }

  Map<String, dynamic> toJson() => {
        'id': id ?? "",
        'numero': numero ?? "",
        'vacunatorio': vacunatorio != null ? jsonEncode(vacunatorio) : null,
        'agendas': agendas.length > 0 ? jsonEncode(agendas) : null,
      };
}
