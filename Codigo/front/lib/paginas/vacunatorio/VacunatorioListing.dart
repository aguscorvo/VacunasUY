import 'package:vacunas_uy/assets/VacunatorioCard.dart';
import 'package:vacunas_uy/objects/Departamento.dart';
import 'package:vacunas_uy/objects/Localidad.dart';
import 'package:vacunas_uy/objects/Vacunatorio.dart';
import 'package:vacunas_uy/paginas/vacunatorio/VacunatorioSelected.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class VacunatorioListing extends StatefulWidget {
  final VacunatorioSelected? vacunSelected;
  const VacunatorioListing({this.vacunSelected});

  @override
  _VacunatorioListingState createState() => _VacunatorioListingState(vacunSelected!);
}

class _VacunatorioListingState extends State<VacunatorioListing> {
  final VacunatorioSelected vacunSelected;
  _VacunatorioListingState(this.vacunSelected);

  String _selectedDepartamento = "";
  String _selectedLocalidad = "";
  List<Departamento> _depGuardados = [];

  @override
  Widget build(BuildContext context) {
    var client = BackendConnection();
    Departamento todosLosDepartamentos = new Departamento.all(-1, "Todos", []);
    Localidad todasLasLocalidades = new Localidad.all(-1, "Todas");

    if (_selectedDepartamento == "") {
      _selectedDepartamento = todosLosDepartamentos.nombre;
    }
    if (_selectedLocalidad == "") {
      _selectedLocalidad = todasLasLocalidades.nombre;
    }

    var topRow = <Container>[];
    topRow.add(filtroDepartamento(client, todosLosDepartamentos));
    topRow.add(filtroLocalidad(client, todasLasLocalidades));
    if (isUserAdmin()) {
      topRow.add(addVacunatorio());
    }

    return Expanded(
      flex: 7,
      child: Column(
        children: [
          new LayoutBuilder(builder: (BuildContext context, BoxConstraints constraints) {
            if (constraints.maxWidth >= 900.0) {
              return new Container(
                height: 100,
                padding: const EdgeInsets.fromLTRB(50.0, 25.0, 50.0, 20),
                alignment: Alignment.center,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: topRow,
                ),
              );
            } else {
              return new Container(
                height: 200,
                padding: const EdgeInsets.fromLTRB(50.0, 25.0, 50.0, 20),
                alignment: Alignment.center,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: topRow,
                ),
              );
            }
          }),
          Expanded(
            flex: 7,
            child: new Container(
              padding: const EdgeInsets.fromLTRB(50.0, 0, 25.0, 50.0),
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
                      List<Vacunatorio> vacunatoriosTemp = snapshot.data as List<Vacunatorio>;
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
                          mainAxisExtent: 176,
                          crossAxisSpacing: 10,
                          mainAxisSpacing: 10,
                        ),
                        padding: const EdgeInsets.fromLTRB(0, 0, 10, 0),
                        itemCount: vacunatorios.length,
                        itemBuilder: (context, index) {
                          return new InkWell(
                            hoverColor: Colors.transparent,
                            onTap: () => {VacunatorioSelected.state.changeVacunatorio(vacunatorios[index])},
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

  Container addVacunatorio() {
    return Container(
      padding: const EdgeInsets.fromLTRB(15, 0, 15, 0),
      decoration: new BoxDecoration(
        border: new Border.all(
          color: Colors.blueAccent,
        ),
        borderRadius: BorderRadius.all(Radius.circular(25)),
        color: Colors.blueAccent,
      ),
      child: FloatingActionButton(
        onPressed: () {},
        tooltip: 'Increment',
        child: Icon(Icons.add),
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
                  List<Departamento> departamentos = snapshot.data as List<Departamento>;
                  _depGuardados = snapshot.data as List<Departamento>;
                  departamentos.add(todosLosDepartamentos);

                  DropdownButton toReturn = DropdownButton<String>(
                    items: departamentos.map((Departamento dep) {
                      return DropdownMenuItem<String>(
                        value: dep.nombre,
                        child: Text(dep.nombre),
                      );
                    }).toList(),
                    onChanged: (String? dep) {
                      setState(() {
                        this._selectedDepartamento = dep!;
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
                    localidades = snapshot.data as List<Localidad>;
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
                    onChanged: (String? loc) {
                      setState(() {
                        this._selectedLocalidad = loc!;
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
