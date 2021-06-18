package vacunasuy.componentecentral.business;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.notification.Notificacion;
import vacunasuy.componentecentral.notification.NotificacionFirebase;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class NotificacionServiceImpl implements INotificacionService {
	
	@Override
	@Asynchronous
	public void enviarNotificacionFirebase(String destinatario, String titulo, String mensaje) throws VacunasUyException {
		try {
			Client cliente = ClientBuilder.newClient();
			WebTarget target = cliente.target(Constantes.FIREBASE_FCM_URL);
			Notificacion notificacion = Notificacion.builder()
					.title(titulo)
					.body(mensaje)
					.build();
			NotificacionFirebase notificacionFirebase = NotificacionFirebase.builder()
					.to(destinatario)
					.notification(notificacion)
					.build();
			Response response = target.request(MediaType.APPLICATION_JSON)
	                .accept(MediaType.APPLICATION_JSON)
	                .header(HttpHeaders.AUTHORIZATION, "key=" + Constantes.FIREBASE_API_KEY)
	                .post(Entity.json(notificacionFirebase));
			if(response.getStatus() != 200) {
				System.out.println("Error al enviar notificación por Firebase.");
			}
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

}
