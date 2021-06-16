package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;

import vacunasuy.componentecentral.dto.MonitorEnfermedadPlanesDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadVacunasDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDosisDTO;

@Local
public interface IMonitorDAO {

	/* Filtro por enfermedad */
	/* 1 - Listar agendas para hoy */
	public Long listarAgendasPorEnfermedad(Long idEnfermedad);
	/* 2 - Listar vacunados para hoy */
	public Long listarVacunadosPorEnfermedad(Long idEnfermedad);
	/* 3 - Listar vacunas por enfermedad */
	public List<MonitorEnfermedadVacunasDTO> listarVacunasPorEnfermedad(Long idEnfermedad);
	/* 4 - Listar planes por enfermedad */
	public List<MonitorEnfermedadPlanesDTO> listarPlanesPorEnfermedad(Long idEnfermedad);
	
	/* Filtro por vacuna */
	/* 1 - Listar agendas para hoy por dósis */
	public List<MonitorVacunaDosisDTO> listarAgendasPorDosis(Long idVacuna);
	/* 2 - Listar vacunados para hoy por dósis */
	public List<MonitorVacunaDosisDTO> listarVacunadosPorDosis(Long idVacuna);
	
	/* Filtro por plan */
	/* 1 - Listar agendas para hoy */
	public Long listarAgendasPorPlan(Long idPlan);
	/* 2 - Listar vacunados para hoy */
	public Long listarVacunadosPorPlan(Long idPlan);
	/* 3 - Listar total de vacunados */
	public Long listarTotalVacunadosPorPlan(Long idPlan);
	
}
