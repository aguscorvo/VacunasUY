import 'package:vacunas_uy/assets/EnfermedadCard.dart';
import 'package:vacunas_uy/objects/Enfermedad.dart';
import 'package:vacunas_uy/tools/BackendConnection.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class EnfermedadesTab extends StatefulWidget {
  @override
  _EnfermedadesTabState createState() => _EnfermedadesTabState();
}

class _EnfermedadesTabState extends State<EnfermedadesTab> {
  @override
  Widget build(BuildContext context) {
    var client = BackendConnection();

    var topRow = <Container>[];
    var topRowH = 50.0;
    if (isUserAdmin()) {
      topRow.add(addEnfermedad());
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
                future: client.getEnfermedades(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState != ConnectionState.done) {
                    return Center(child: CircularProgressIndicator());
                  } else {
                    if (snapshot.data == null) {
                      return Center(child: CircularProgressIndicator());
                    } else {
                      List<Enfermedad> enfermedades = [];
                      List<Enfermedad> enfermedadesTemp = snapshot.data as List<Enfermedad>;
                      enfermedadesTemp.forEach((Enfermedad element) {
                        enfermedades.add(element);
                      });

                      if (enfermedades.length == 0) {
                        return Text("No se encuentran Enfermedades, reintente!");
                      }

                      return GridView.builder(
                        gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                          childAspectRatio: MediaQuery.of(context).size.width / (MediaQuery.of(context).size.height / 4),
                          maxCrossAxisExtent: 600,
                          mainAxisExtent: 176,
                          crossAxisSpacing: 10,
                          mainAxisSpacing: 10,
                        ),
                        itemCount: enfermedades.length,
                        itemBuilder: (context, index) {
                          return new InkWell(
                            hoverColor: Colors.transparent,
                            //onTap: () => Navigator.of(context).pushNamed('', arguments: ''),
                            child: new Container(
                              child: new EnfermedadCard(
                                enfermedad: enfermedades[index],
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

  Container addEnfermedad() {
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
