package vacunasuy.nodosperifericos.business;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.nodosperifericos.converter.VacunadorConverter;
import vacunasuy.nodosperifericos.dao.IVacunadorDAO;
import vacunasuy.nodosperifericos.dto.VacunadorDTO;
import vacunasuy.nodosperifericos.entity.Vacunador;
import vacunasuy.nodosperifericos.exception.NodosPerifericosException;

@Stateless
public class VacunadorServiceImpl implements IVacunadorService{

	@EJB
	private IVacunadorDAO vacunadorDAO;
	
	@EJB
	private VacunadorConverter vacunadorConverter;
	
	@Override
	public List<VacunadorDTO> listar() throws NodosPerifericosException {
		try {
			return vacunadorConverter.fromEntity(vacunadorDAO.listar());
		} catch (Exception e) {
			throw new NodosPerifericosException(e.getLocalizedMessage(), NodosPerifericosException.ERROR_GENERAL);
		}
	}

	@Override
	public void crear(List<VacunadorDTO> vacunadoresDTO, String fecha, Long idVacunatorio) throws NodosPerifericosException {
		try {
			vacunadorDAO.eliminarRegistros(fecha, idVacunatorio);
			for (VacunadorDTO vacunadorDTO : vacunadoresDTO) {
				Vacunador vacunador = vacunadorConverter.fromDTO(vacunadorDTO);
				vacunador.setFechaAsignacion(LocalDate.parse(fecha));
				vacunador.setIdVacunatorio(idVacunatorio);
				vacunadorDAO.crear(vacunador);
			}
		} catch (Exception e) {
			throw new NodosPerifericosException(e.getLocalizedMessage(), NodosPerifericosException.ERROR_GENERAL);
		}
	}
	
}
