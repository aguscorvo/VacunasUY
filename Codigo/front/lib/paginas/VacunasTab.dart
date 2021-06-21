import 'package:vacunas_uy/assets/VacunaCard.dart';
import 'package:vacunas_uy/objects/Vacuna.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class VacunasTab extends StatefulWidget {
  @override
  _VacunasTabState createState() => _VacunasTabState();
}

class _VacunasTabState extends State<VacunasTab> {
  @override
  Widget build(BuildContext context) {
    var client = BackendConnection();

    var topRow = <Container>[];
    var topRowH = 50.0;
    if (isUserAdmin()) {
      topRow.add(addVacuna());
      topRowH = 100;
    }

    return Scaffold(
      body: Column(
        children: [
          new LayoutBuilder(builder: (BuildContext context, BoxConstraints constraints) {
            if (constraints.maxWidth >= 900.0) {
              return new Container(
                height: topRowH,
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
            child: new Container(
              padding: const EdgeInsets.fromLTRB(50.0, 0, 50.0, 50.0),
              alignment: Alignment.center,
              child: FutureBuilder(
                future: client.getVacunas(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      List<Vacuna> vacunas = [];
                      List<Vacuna> vacunasTemp = snapshot.data as List<Vacuna>;
                      vacunasTemp.forEach((Vacuna element) {
                        vacunas.add(element);
                      });

                      if (vacunas.length == 0) {
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
                        itemCount: vacunas.length,
                        itemBuilder: (context, index) {
                          return new InkWell(
                            hoverColor: Colors.transparent,
                            onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                            child: new Container(
                              child: new VacunaCard(
                                vacuna: vacunas[index],
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

  Container addVacuna() {
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
}
