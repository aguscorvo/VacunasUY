package vacunasuy.nodoperifericobackend.service;

import java.util.List;

import vacunasuy.nodoperifericobackend.dao.IVacunadorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacunasuy.nodoperifericobackend.entity.Vacunador;

@Service
public class VacunadorService {
	
	@Autowired
	private IVacunadorDAO vacunadorDAO;
	
	public List<Vacunador> listar(){
		return vacunadorDAO.findAll();
	}
	
	public Vacunador crear(Vacunador vacunador) {
		return vacunadorDAO.save(vacunador);
	}

}
