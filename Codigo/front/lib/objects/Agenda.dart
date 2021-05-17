import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Agenda {
  int id;

  Agenda({this.id});

  Agenda.fromJson(Map<String, dynamic> json) {
    id = json['id'];
  }
}
