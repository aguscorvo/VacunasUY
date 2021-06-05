package vacunasuy.nodoperifericobackend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vacunasuy.nodoperifericobackend.dao.IAgendaDAO;
import vacunasuy.nodoperifericobackend.entity.Agenda;

@Service
public class AgendaService {

	@Autowired
	private IAgendaDAO agendaDAO;
	
	private String hoy = LocalDate.now().toString() + " 00:00";
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public List<Agenda> listar(){
		return agendaDAO.findAll();
	}

	public List<Agenda> listarAgendasPendientes(){
		return agendaDAO.findByFechaGreaterThanAndVacunado(LocalDateTime.parse(hoy, formato), false);
	}
	
	public List<Agenda> listarAgendasProcesadas(){
		return agendaDAO.findByFechaGreaterThanAndVacunado(LocalDateTime.parse(hoy, formato), true);
	}
	
}
