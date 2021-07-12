import 'package:vacunas_uy/assets/CustomAppBar.dart';
import 'package:vacunas_uy/assets/CustomNavBar.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/CertificadoTab.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonitorVacunacionTab.dart';
import 'package:vacunas_uy/tools/CertificadoInfo.dart';

class DesktopLandingPage extends StatefulWidget {
  final String? title;
  DesktopLandingPage({
    this.title,
  });

  @override
  _DesktopLandingPageState createState() => _DesktopLandingPageState();
}

class _DesktopLandingPageState extends State<DesktopLandingPage> {
  Widget _body = MonitorVacunacionTab();

  _setBody(Widget val) {
    setState(() {
      _body = val;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (CertificadoInfo.hayCertificado) {
      _body = CertificadoTab();
      CertificadoInfo.hayCertificado = false;
    }

    CustomNavBar navBar = CustomNavBar(
      key: Key(""),
      title: widget.title!,
      onElementSelected: (Widget val) => _setBody(val),
    );
    CustomAppBar appBar = CustomAppBar(
      key: Key(""),
      title: widget.title!,
      onElementSelected: (Widget val) => _setBody(val),
    );
    return Scaffold(
      appBar: appBar,
      body: Row(
        mainAxisSize: MainAxisSize.max,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Expanded(
            child: _body,
          ),
        ],
      ),
      bottomNavigationBar: navBar,
    );
  }
}
