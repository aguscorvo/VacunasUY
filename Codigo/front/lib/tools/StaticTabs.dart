import 'package:vacunas_uy/paginas/EnfermedadesTab.dart';
import 'package:vacunas_uy/paginas/ErrorTab.dart';
import 'package:vacunas_uy/paginas/PlanVacunacionTab.dart';
import 'package:vacunas_uy/paginas/ProveedoresTab.dart';
import 'package:vacunas_uy/paginas/VacunasTab.dart';
import 'package:vacunas_uy/paginas/agenda/AgendaTab.dart';
import 'package:vacunas_uy/paginas/chat/ChatTab.dart';
import 'package:vacunas_uy/paginas/misVacunas/MisVacunasTab.dart';
import 'package:vacunas_uy/paginas/monitorVacunacion/MonitorVacunacionTab.dart';
import 'package:vacunas_uy/paginas/vacunatorio/VacunatorioTab.dart';

class StaticTabs {
  static MonitorVacunacionTab monitor = new MonitorVacunacionTab();
  static VacunatorioTab vacunatorios = new VacunatorioTab();
  static VacunasTab vacunas = new VacunasTab();
  static PlanVacunacionTab planVacunacion = new PlanVacunacionTab();
  static ProveedoresTab proveedores = new ProveedoresTab();
  static AgendaTab agenda = new AgendaTab();
  static EnfermedadesTab enfermedades = new EnfermedadesTab();
  static MisVacunasTab misVacunas = new MisVacunasTab();
  static ChatTab chat = new ChatTab();
  static ErrorTab error = new ErrorTab();
}
