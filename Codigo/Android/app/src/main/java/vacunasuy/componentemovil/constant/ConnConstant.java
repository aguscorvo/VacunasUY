package vacunasuy.componentemovil.constant;

public class ConnConstant {
    //GENERAL
    final public static String USER_AGENT = "VacunasUY.Android";
    final public static Integer REST_INFO_GRAL = 1;
    final public static Integer REST_USR_LOGIN = 2;
    final public static Integer REST_VACUNATORIOS = 3;

    final public static String API_INFOGRAL_URL = "https://vacunasuy.web.elasticloud.uy/rest/";
    final public static String API_DEPARTAMENTOS_URL = "https://vacunasuy.web.elasticloud.uy/rest/departamentos";
    final public static String API_SECTORLABORAL_URL = "http://192.168.4.92:8080/rest/sectoreslaborales";



    //USUARIO
    final public static String API_GUBUY_URL = "http://192.168.4.92:8080/rest/autenticaciongubuy";
    final public static String API_USRLOGIN_URL = "http://192.168.4.92:8080/rest/autenticaciongubuy/procesarTokens?";
    final public static String API_EDITUSR_URL = "http://192.168.4.92:8080/rest/usuarios/editar/{id}";


    //VACUNATORIO
    final public static String API_VACUNATORIOS_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios";
    //final public static String API_VACUNATORIOS_FILTERDEPTO_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatorios/listarPorDepartamento/{departamento}";
    //final public static String API_VACUNATORIOS_FILTERLOCALIDAD_URL = "https://vacunasuy.web.elasticloud.uy/rest/vacunatrios/listarPorUbicacion/{localidad}/{departamento}";
    final public static String API_VACUNATORIOS_FILTERDEPTO_URL = "http://192.168.4.92:8080/rest/vacunatorios/listarPorDepartamento/{departamento}";
    final public static String API_VACUNATORIOS_FILTERLOCALIDAD_URL = "http://192.168.4.92:8080/rest/vacunatorios/listarPorUbicacion/{localidad}/{departamento}";
    final public static String API_VACUNATORIOS_CERCANOS_URL = "http://192.168.4.92:8080/rest/vacunatorios/listar/cercanos";



}
