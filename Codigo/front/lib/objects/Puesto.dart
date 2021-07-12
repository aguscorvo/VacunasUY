import 'dart:convert';

import 'Vacunatorio.dart';
import 'Agenda.dart';

class Puesto {
  int id = -1;
  int numero = -1;
  Vacunatorio vacunatorio = Vacunatorio();
  List<Agenda> agendas = [];

  Puesto();

  Puesto.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    numero = json['numero'];

    if (json['vacunatorio'] != null) {
      vacunatorio = Vacunatorio.fromJson(json['vacunatorio']);
    } else {
      vacunatorio = Vacunatorio();
    }

    agendas = [];
    if (json['agendas'] != null) {
      json['agendas'].forEach((localidad) {
        agendas.add(Agenda.fromJson(localidad));
      });
    }
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'numero': numero,
        'vacunatorio': jsonEncode(vacunatorio),
        'agendas': agendas.length > 0 ? jsonEncode(agendas) : null,
      };
}
