package vacunasuy.nodoperifericobackend.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vacunasuy.nodoperifericobackend.service.VacunatorioService;

@Service
public class VacunatorioJobs {
	
	@Autowired
	private VacunatorioService vacunatorioService;
	
	/* Obtener vacunadores */
	/* Se ejecuta cada minuto en el segundo 30 */
	@Scheduled(cron = "30 * * * * ?")
	public void obtenerVacunadores() {
		vacunatorioService.obtenerAsignacionVacunadores();
	}
	
	/* Obtener agendas */
	/* Se ejecuta cada minuto en el segundo 30 */
	@Scheduled(cron = "30 * * * * ?")
	public void obtenerAgendas() {
		vacunatorioService.obtenerAgendasPorVacunatorio();
	}
	
	/* Enviar actos vacunales */
	/* Se ejecuta cada minuto en el segundo 30 */
	@Scheduled(cron = "30 * * * * ?")
	public void enviarActosVacunalesPorVacunatorio() {
		vacunatorioService.enviarActosVacunalesPorVacunatorio();
	}
	
}
