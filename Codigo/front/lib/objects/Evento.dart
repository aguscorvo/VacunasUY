import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Evento {
  int id;

  Evento({this.id});

  Evento.fromJson(Map<String, dynamic> json) {
    id = json['id'];
  }
}
