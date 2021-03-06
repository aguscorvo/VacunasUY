package vacunasuy.componentecentral.util;

public final class Constantes {

	/* JsonWebToken */
	public static final String JWT_KEY = "sh281$2JKshazn21Lm9=";
	
	/* gub.uy */
	public static final String CLIENT_ID = "890192";
	public static final String CLIENT_SECRET = "457d52f181bf11804a3365b49ae4d29a2e03bbabe74997a2f510b179";
	public static final String AUTHORIZATION_URL = "https://auth-testing.iduruguay.gub.uy/oidc/v1/authorize";
	public static final String ACCESSTOKEN_URL = "https://auth-testing.iduruguay.gub.uy/oidc/v1/token";
	public static final String USERINFO_URL = "https://auth-testing.iduruguay.gub.uy/oidc/v1/userinfo";
	//public static final String REDIRECT_URI = "https://vacunasuy.web.elasticloud.uy/rest/autenticaciongubuy/procesarTokens";
	//public static final String REDIRECT_URI = "http://localhost:8080";
	public static final String REDIRECT_URI = "https://vacunasuy.web.elasticloud.uy";
	
	/* Nodos periféricos */
//	public static final String NODOS_PERIFERICOS_REST_URL = "http://localhost:8081";
//	public static final String NODOS_EXTERNOS_REST_URL = "http://localhost:8082";
	public static final String NODOS_PERIFERICOS_REST_URL = "https://nodos-perifericos.herokuapp.com";
	public static final String NODOS_EXTERNOS_REST_URL = "https://nodos-externos.herokuapp.com";
	
	/* Notificaciones Firebase */
	public static final String FIREBASE_API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXX";
	public static final String FIREBASE_FCM_URL = "https://fcm.googleapis.com/fcm/send";
	
}
