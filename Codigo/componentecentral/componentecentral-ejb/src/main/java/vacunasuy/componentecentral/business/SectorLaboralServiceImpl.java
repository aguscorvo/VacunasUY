package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.SectorLaboralConverter;
import vacunasuy.componentecentral.dao.ISectorLaboralDAO;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class SectorLaboralServiceImpl implements ISectorLaboralService {

	@EJB
	private ISectorLaboralDAO sectorLaboralDAO;
	
	@EJB
	private SectorLaboralConverter sectorLaboralConverter;
	
	@Override
	public List<SectorLaboralDTO> listar() throws VacunasUyException {
		try {
			return sectorLaboralConverter.fromEntity(sectorLaboralDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
}
