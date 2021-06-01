import 'package:VacunasUY/assets/VacunatorioCard.dart';
import 'package:VacunasUY/objects/Departamento.dart';
import 'package:VacunasUY/objects/Localidad.dart';
import 'package:VacunasUY/objects/Vacunatorio.dart';
import 'package:VacunasUY/tools/BackendConnection.dart';
import 'package:flutter/material.dart';

class VacunatoriosTab extends StatefulWidget {
  @override
  _VacunatoriosTabState createState() => _VacunatoriosTabState();
}

class _VacunatoriosTabState extends State<VacunatoriosTab> {
  String _selectedDepartamento = "";
  String _selectedLocalidad = "";
  List<Departamento> _depGuardados = [];

  @override
  Widget build(BuildContext context) {
    var client = BackendConnection();
    Departamento todosLosDepartamentos = new Departamento(id: -1, nombre: "Todos");
    Localidad todasLasLocalidades = new Localidad(id: -1, nombre: "Todas");

    if (_selectedDepartamento == "") {
      _selectedDepartamento = todosLosDepartamentos.nombre;
    }
    if (_selectedLocalidad == "") {
      _selectedLocalidad = todasLasLocalidades.nombre;
    }

    return Scaffold(
      body: Column(
        //mainAxisSize: MainAxisSize.max,
        //crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          new Container(
            height: 100,
            padding: const EdgeInsets.fromLTRB(50.0, 25.0, 50.0, 20),
            alignment: Alignment.center,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                filtroDepartamento(client, todosLosDepartamentos),
                filtroLocalidad(client, todasLasLocalidades),
              ],
            ),
          ),
          Expanded(
            child: new Container(
              padding: const EdgeInsets.fromLTRB(50.0, 0, 50.0, 50.0),
              alignment: Alignment.center,
              child: FutureBuilder(
                future: client.getVacunatorios(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return CircularProgressIndicator();
                  } else {
                    if (snapshot.data == null) {
                      return CircularProgressIndicator();
                    } else {
                      List<Vacunatorio> vacunatorios = [];
                      List<Vacunatorio> vacunatoriosTemp = snapshot.data;
                      vacunatoriosTemp.forEach((Vacunatorio element) {
                        if ((element.departamento.nombre == _selectedDepartamento || _selectedDepartamento == "Todos") &&
                            (element.localidad.nombre == _selectedLocalidad || _selectedLocalidad == "Todas")) {
                          vacunatorios.add(element);
                        }
                      });

                      if (vacunatorios.length == 0) {
                        return Text("No hay vacunatorios en esta localidad");
                      }

                      return GridView.builder(
                        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                          childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                          maxCrossAxisExtent: 600,
                          mainAxisExtent: 175,
                          crossAxisSpacing: 10,
                          mainAxisSpacing: 10,
                        ),
                        itemCount: vacunatorios.length,
                        itemBuilder: (context, index) {
                          return new InkWell(
                            hoverColor: Colors.transparent,
                            onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                            child: new Container(
                              child: new VacunatorioCard(
                                vacunatorio: vacunatorios[index],
                              ),
                            ),
                          );
                        },
                      );
                    }
                  }
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

  Container filtroDepartamento(var client, Departamento todosLosDepartamentos) {
    return Container(
      padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
      decoration: new BoxDecoration(
        border: new Border.all(
          color: Colors.blueAccent,
        ),
        borderRadius: BorderRadius.all(Radius.circular(25)),
        color: Colors.blueAccent,
      ),
      child: Row(
        children: [
          Text("Departamento    "),
          FutureBuilder(
            future: client.getDepartamentos(),
            builder: (context, snapshot) {
              if (snapshot.connectionState != ConnectionState.done) {
                return CircularProgressIndicator();
              } else {
                if (snapshot.data == null) {
                  return CircularProgressIndicator();
                } else {
                  List<Departamento> departamentos = snapshot.data;
                  _depGuardados = snapshot.data;
                  departamentos.add(todosLosDepartamentos);

                  DropdownButton toReturn = DropdownButton<String>(
                    items: departamentos.map((Departamento dep) {
                      return DropdownMenuItem<String>(
                        value: dep.nombre,
                        child: Text(dep.nombre),
                      );
                    }).toList(),
                    onChanged: (String dep) {
                      setState(() {
                        this._selectedDepartamento = dep;
                        this._selectedLocalidad = "Todas";
                      });
                    },
                    value: _selectedDepartamento,
                  );

                  return toReturn;
                }
              }
            },
          ),
        ],
      ),
    );
  }

  Container filtroLocalidad(BackendConnection client, Localidad todasLasLocalidades) {
    return Container(
      padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
      decoration: new BoxDecoration(
        border: new Border.all(
          color: Colors.blueAccent,
        ),
        borderRadius: BorderRadius.all(Radius.circular(25)),
        color: Colors.blueAccent,
      ),
      child: Row(
        children: [
          Text("Localidad    "),
          FutureBuilder(
            future: client.getLocalidades(),
            builder: (context, snapshot) {
              if (snapshot.connectionState != ConnectionState.done) {
                return CircularProgressIndicator();
              } else {
                if (snapshot.data == null) {
                  return CircularProgressIndicator();
                } else {
                  List<Localidad> localidades;
                  if (_selectedDepartamento == "Todos") {
                    localidades = snapshot.data;
                  } else {
                    localidades = [];
                    _depGuardados.forEach((dep) {
                      if (dep.nombre == _selectedDepartamento) {
                        localidades.addAll(dep.localidades);
                      }
                    });
                  }
                  localidades.add(todasLasLocalidades);

                  DropdownButton toReturn = DropdownButton<String>(
                    items: localidades.map((Localidad loc) {
                      return DropdownMenuItem<String>(
                        value: loc.nombre,
                        child: Text(loc.nombre),
                      );
                    }).toList(),
                    onChanged: (String loc) {
                      setState(() {
                        this._selectedLocalidad = loc;
                      });
                    },
                    value: _selectedLocalidad,
                  );

                  return toReturn;
                }
              }
            },
          ),
        ],
      ),
    );
  }
}
