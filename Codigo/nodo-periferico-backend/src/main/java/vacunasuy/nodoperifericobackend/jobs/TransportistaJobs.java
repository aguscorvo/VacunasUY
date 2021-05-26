package vacunasuy.nodoperifericobackend.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vacunasuy.nodoperifericobackend.service.TransportistaService;

@Service
public class TransportistaJobs {

	@Autowired
	private TransportistaService transportistaService;
	
	/* Obtener eventos en estado INICIADO */
	/* Se ejecuta cada minuto */
	@Scheduled(cron = "0 * * * * ?")
	public void obtenerEventosIniciados() {
		transportistaService.obtenerEventosIniciados();
	}

}
