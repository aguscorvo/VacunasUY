package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IPlanVacunacionService {

	public List<PlanVacunacionDTO> listar() throws VacunasUyException;
	public List<PlanVacunacionDTO> listarPlanesVigentes() throws VacunasUyException;
	public PlanVacunacionDTO listarPorId(Long id) throws VacunasUyException;
	public PlanVacunacionDTO crear(PlanVacunacionCrearDTO planVacunacionDTO) throws VacunasUyException;
	public PlanVacunacionDTO editar(Long id, PlanVacunacionCrearDTO planVacunacionDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
}
