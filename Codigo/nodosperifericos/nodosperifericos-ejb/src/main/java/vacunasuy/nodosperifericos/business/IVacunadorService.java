package vacunasuy.nodosperifericos.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.nodosperifericos.dto.VacunadorDTO;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

@Local
public interface IVacunadorService {

	public List<VacunadorDTO> listar() throws NodosPerifericosException;
	public void crear(List<VacunadorDTO> vacunadoresDTO, String fecha, Long idVacunatorio) throws NodosPerifericosException;
	
}
