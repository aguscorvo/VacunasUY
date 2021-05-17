import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class GubUY {
  String code;
  String state;

  GubUY({this.code, this.state});

  GubUY.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    state = json['state'];
  }

  Map<String, dynamic> toJson() => {
        'code': code ?? "",
        'state': state ?? "",
      };
}
