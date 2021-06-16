package vacunasuy.componentecentral.business;

import javax.ejb.Local;
import vacunasuy.componentecentral.dto.MonitorEnfermedadDTO;
import vacunasuy.componentecentral.dto.MonitorPlanDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IMonitorService {

	/* Filtro por enfermedad */
	public MonitorEnfermedadDTO listarDatosPorEnfermedad(Long idEnfermedad) throws VacunasUyException;
	
	/* Filtro por vacuna */
	public MonitorVacunaDTO listarDatosPorVacuna(Long idVacuna) throws VacunasUyException;
	
	/* Filtro por plan de vacunación */
	public MonitorPlanDTO listarDatosPorPlan(Long idPlan) throws VacunasUyException;
	
}
