package vacunasuy.componentecentral.business;

import javax.ejb.Local;

@Local
public interface INotificacionService {

	public void enviarNotificacionFirebase(String destinatario, String titulo, String mensaje);
	
}
