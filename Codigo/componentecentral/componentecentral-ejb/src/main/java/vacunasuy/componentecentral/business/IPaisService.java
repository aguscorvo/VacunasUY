package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IPaisService {
	
	public List<PaisDTO> listar() throws VacunasUyException;
	public PaisDTO listarPorId(Long id) throws VacunasUyException;
	
}
