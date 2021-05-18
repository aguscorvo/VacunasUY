package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface ISectorLaboralService {

	public List<SectorLaboralDTO> listar() throws VacunasUyException;
	
}
