package vacunasuy.componentemovil.constant;

public class ConnConstant {
    //GENERAL
    final public static String USER_AGENT = "VacunasUY.Android";
    final public static Integer REST_INFO_GRAL = 1;
    final public static Integer REST_USR_LOGIN = 2;
    final public static Integer REST_VACUNATORIOS = 3;

    final public static String API_INFOGRAL_URL = "https://vacunasuy.web.elasticloud.uy/rest/";
    final public static String API_DEPARTAMENTOS_URL = "https://vacunasuy.web.elasticloud.uy/rest/departamentos";
    final public static String API_SECTORLABORAL_URL = "https://vacunasuy.web.elasticloud.uy/rest/sectoreslaborales";

    //USUARIO
    final public static String API_GUBUY_URL = "https://vacunasuy.web.elasticloud.uy/rest/autenticaciongubuy";
    final public static String API_USRLOGIN_URL = "https://vacunasuy.web.elasticloud.uy/rest/autenticaciongubuy/procesarTokens?";
    final public static String API_EDITUSR_URL = "https://vacunasuy.web.elasticloud.uy/rest/usuarios/editar/{id}";
    final public static String API_ADDTOKENFIREBASE_URL = "https://vacunasuy.web.elasticloud.uy/rest/usuarios/registrarTokenFirebase";
    final public static String API_GETENFERMEDADESVACUNAS_URL = "https://vacunasuy.web.elasticloud.uy/rest/enfermedades/listarEnfermedadesPorUsuario/{id}";
    final public static String API_GETENFERMEDADVACUNAS_URL = "https://vacunasuy.web.elasticloud.uy/rest/actosVacunales/listarActosVacunalesPorUsuarioEnfermedad/{idUsuario}/{idEnfermedad}";
    final public static String API_GETUSUARIOAGENDA_URL = "https://vacunasuy.web.elasticloud.uy/rest/usuarios/listarAgendasCiudadano/{idUsuario}";


    //VACUNATORIO
    final public static String API_VACUNATORIOS_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios";
    final public static String API_VACUNATORIOS_FILTERDEPTO_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios/listarPorDepartamento/{departamento}";
    final public static String API_VACUNATORIOS_FILTERLOCALIDAD_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios/listarPorUbicacion/{localidad}/{departamento}";
    final public static String API_VACUNATORIOS_CERCANOS_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios/listar/cercanos/{latitud}/{longitud}/{distancia}";
    final public static String API_VACUNATORIOSPLAN_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios/listarVacunatoriosDadoPlan/{id}";

    //PLANES DE VACUNACION
    final public static String API_PLANESVACUNACION_URL = "https://vacunasuy.web.elasticloud.uy/rest/planesVacunacion";

    //AGENDA
    final public static String API_ADDAGENDA_URL = "https://vacunasuy.web.elasticloud.uy/rest/agendas";


}
