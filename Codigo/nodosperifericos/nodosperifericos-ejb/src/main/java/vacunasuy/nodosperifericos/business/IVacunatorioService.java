package vacunasuy.nodosperifericos.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.nodosperifericos.dto.VacunatorioDTO;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

@Local
public interface IVacunatorioService {

	public List<VacunatorioDTO> listar() throws NodosPerifericosException;
	public VacunatorioDTO crear(VacunatorioDTO vacunatorio) throws NodosPerifericosException;
	
}
