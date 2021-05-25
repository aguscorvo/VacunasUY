package vacunasuy.nodoperifericobackend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vacunasuy.nodoperifericobackend.dao.ITransportistaDAO;
import vacunasuy.nodoperifericobackend.entity.Transportista;

@Service
public class TransportistaService {

	@Autowired
	private ITransportistaDAO transportistaDAO;
	
	
	public List<Transportista> listar(){
		return transportistaDAO.findAll();
	}
	
	public Transportista crear(Transportista transportista) {
		return transportistaDAO.save(transportista);
	}
	
	
	
	
}
