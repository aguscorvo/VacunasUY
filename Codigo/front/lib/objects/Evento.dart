class Evento {
  int id = -1;

  Evento();

  Evento.fromJson(Map<String, dynamic> json) {
    id = json['id'];
  }
}
