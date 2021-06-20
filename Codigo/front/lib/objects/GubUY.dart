class GubUY {
  String code = "";
  String state = "";

  GubUY();

  GubUY.fromJson(Map<String, dynamic> json) {
    code = json['code'];
    state = json['state'];
  }

  Map<String, dynamic> toJson() => {
        'code': code,
        'state': state,
      };
}
