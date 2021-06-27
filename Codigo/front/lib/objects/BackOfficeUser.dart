class BackOfficeUser {
  String? token = "";
  String? nombre = "";
  int? rol = -1;

  BackOfficeUser({this.nombre, this.rol, this.token});

  Map<String, dynamic> toJson() {
    Map<String, dynamic> toReturn = {};
    try {
      Map<String, dynamic> toReturn = {
        'token': token,
        'nombre': nombre,
        'rol': rol,
      };
      return toReturn;
    } catch (err) {
      return toReturn;
    }
  }
}
