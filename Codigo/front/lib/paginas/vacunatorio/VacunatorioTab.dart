import 'package:vacunas_uy/paginas/vacunatorio/VacunatorioListing.dart';
import 'package:vacunas_uy/paginas/vacunatorio/VacunatorioSelected.dart';
import 'package:flutter/material.dart';

class VacunatorioTab extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    VacunatorioSelected selected = VacunatorioSelected(
      vacun: null,
      mode: "Show",
    );
    VacunatorioListing listador = VacunatorioListing(vacunSelected: selected);
    return Scaffold(
      body: Row(
        mainAxisAlignment: MainAxisAlignment.start,
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          listador,
          selected,
        ],
      ),
    );
  }
}
