package vacunasuy.nodosperifericos.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.nodosperifericos.converter.VacunatorioConverter;
import vacunasuy.nodosperifericos.dao.IVacunatorioDAO;
import vacunasuy.nodosperifericos.dto.VacunatorioDTO;
import vacunasuy.nodosperifericos.entity.Vacunatorio;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

@Stateless
public class VacunatorioServiceImpl implements IVacunatorioService {

	@EJB
	private IVacunatorioDAO vacunatorioDAO;
	
	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@Override
	public List<VacunatorioDTO> listar() throws NodosPerifericosException {
		try {
			return vacunatorioConverter.fromEntity(vacunatorioDAO.listar());
		} catch (Exception e) {
			throw new NodosPerifericosException(e.getLocalizedMessage(), NodosPerifericosException.ERROR_GENERAL);
		}
	}

	@Override
	public VacunatorioDTO crear(VacunatorioDTO vacunatorioDTO) throws NodosPerifericosException {
		try {
			Vacunatorio vacunatorio = vacunatorioConverter.fromDTO(vacunatorioDTO);
			return vacunatorioConverter.fromEntity(vacunatorioDAO.crear(vacunatorio));
		} catch (Exception e) {
			throw new NodosPerifericosException(e.getLocalizedMessage(), NodosPerifericosException.ERROR_GENERAL);
		}
	}
	
}
