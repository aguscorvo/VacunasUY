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
	/* @Scheduled(fixedDelay = 5000)
	public void obtenerVacunadores() {
		vacunatorioService.obtenerAsignacionVacunadores();
	}*/
	
	/* Obtener agendas */
	@Scheduled(fixedDelay = 10000)
	public void obtenerAgendas() {
		vacunatorioService.obtenerAgendasPorVacunatorio();
	}
	
}
