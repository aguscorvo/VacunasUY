package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IRolService {
	public List<RolDTO> listar() throws VacunasUyException;
	public RolDTO listarPorId(Long id) throws VacunasUyException;
	

}
