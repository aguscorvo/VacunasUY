import 'package:vacunas_uy/landing/DesktopLandingPage.dart';
import 'package:vacunas_uy/landing/MobileLandingPage.dart';
import 'package:vacunas_uy/tools/FirebaseApi.dart';
import 'package:vacunas_uy/tools/PlatformSpecific.dart';
import 'package:vacunas_uy/tools/UserCredentials.dart';
import 'package:flutter/material.dart';

class BaseApp extends StatefulWidget {
  final String? title;
  BaseApp({this.title});

  @override
  _BaseAppState createState() => _BaseAppState();
}

class _BaseAppState extends State<BaseApp> {
  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: autoLogIn(),
      builder: (BuildContext context, AsyncSnapshot<bool> snapshot) {
        if (storedUserCredentials == null) {
          return Container(
            color: Colors.blueGrey,
            child: Center(
              child: CircularProgressIndicator(),
            ),
          );
        } else {
          FirebaseApi().signIn();
          return LandingPageLayoutBuilder(
            title: widget.title!,
          );
        }
      },
    );
  }
}

class LandingPageLayoutBuilder extends StatelessWidget {
  final String? title;
  const LandingPageLayoutBuilder({this.title});
  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (BuildContext context, BoxConstraints constraints) {
      if (constraints.maxWidth >= 900.0) {
        return DesktopLandingPage(
          title: this.title!,
        );
      } else {
        return MobileLandingPage(
          title: this.title!,
        );
      }
    });
  }
}
