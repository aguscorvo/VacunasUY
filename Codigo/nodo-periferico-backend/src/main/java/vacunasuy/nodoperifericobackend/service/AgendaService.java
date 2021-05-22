package vacunasuy.nodoperifericobackend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vacunasuy.nodoperifericobackend.dao.IAgendaDAO;
import vacunasuy.nodoperifericobackend.entity.Agenda;

@Service
public class AgendaService {

	@Autowired
	private IAgendaDAO agendaDAO;
	
	public List<Agenda> listar(){
		return agendaDAO.findAll();
	}
	
}
