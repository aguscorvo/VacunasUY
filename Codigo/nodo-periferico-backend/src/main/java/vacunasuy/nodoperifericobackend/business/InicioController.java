package vacunasuy.nodoperifericobackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vacunasuy.nodoperifericobackend.service.AgendaService;
import vacunasuy.nodoperifericobackend.service.TransportistaService;
import vacunasuy.nodoperifericobackend.service.VacunadorService;
import vacunasuy.nodoperifericobackend.service.VacunatorioService;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/")
public class InicioController {

	@Autowired
	private TransportistaService transportistaService;
	
	@Autowired
	private VacunadorService vacunadorService;
	
	@Autowired
	private VacunatorioService vacunatorioService;
	
	@Autowired
	private AgendaService agendaService;
	
	@GetMapping
	public String inicio() {
		int cantVacunatorios = vacunatorioService.listar().size();
		int cantVacunadores = vacunadorService.listar().size();
		int cantAgendasAProcesar = agendaService.listarAgendasPendientes().size();
		int cantAgendasProcesadas = agendaService.listarAgendasProcesadas().size();
		int cantTransportistas =  transportistaService.listar().size();
		
		return "<html><head><meta charset=\"UFT-8\"/></head><body><h2>Componente: Nodos periféricos</h2><h3>Status: <span style=\"color:green\">activo<span></h3><h3>Datos registrados:</h3><h4>Vacunatorios: " + cantVacunatorios + " registros</h4><h4>Vacunadores: " + cantVacunadores + " registros</h4><h4>Agendas del día a procesar: " + cantAgendasAProcesar + " registros</h4><h4>Agendas del día procesadas: " + cantAgendasProcesadas + " registros</h4><h4>Transportistas: " + cantTransportistas + " registros</h4></body></html>";
	}
	
}
