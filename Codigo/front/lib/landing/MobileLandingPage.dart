import 'package:vacunas_uy/assets/CustomAppBar.dart';
import 'package:vacunas_uy/assets/CustomNavBar.dart';
import 'package:flutter/material.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonitorVacunacionTab.dart';

class MobileLandingPage extends StatefulWidget {
  final String? title;
  MobileLandingPage({
    this.title,
  });

  @override
  _MobileLandingPageState createState() => _MobileLandingPageState();
}

class _MobileLandingPageState extends State<MobileLandingPage> {
  Widget _body = MonitorVacunacionTab();

  _setBody(Widget val) {
    setState(() {
      _body = val;
    });
  }

  @override
  Widget build(BuildContext context) {
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
