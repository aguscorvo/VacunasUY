export 'package:vacunas_uy/tools/platformSpecific/Mobile.dart'
    if (dart.library.html) 'package:vacunas_uy/tools/platformSpecific/Web.dart'
    if (dart.library.io) 'package:vacunas_uy/tools/platformSpecific/Mobile.dart';
