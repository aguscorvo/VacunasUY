package vacunasuy.componentecentral.business;

import javax.ejb.Local;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface INotificacionService {

	public void enviarNotificacionFirebase(String destinatario, String titulo, String mensaje) throws VacunasUyException;
	
}
