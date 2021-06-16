package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.PlanVacunacionConverter;
import vacunasuy.componentecentral.dao.IMonitorDAO;
import vacunasuy.componentecentral.dao.IPlanVacunacionDAO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadPlanesDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadVacunasDTO;
import vacunasuy.componentecentral.dto.MonitorPlanDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDosisDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class MonitorServiceImpl implements IMonitorService {

	@EJB
	private IMonitorDAO monitorDAO;
	
	@EJB
	private IPlanVacunacionDAO planVacunacionDAO;
	
	@EJB
	private PlanVacunacionConverter planVacunacionConverter;
	
	/* Filtro por enfermedad */
	@Override
	public MonitorEnfermedadDTO listarDatosPorEnfermedad(Long idEnfermedad)  throws VacunasUyException {
		try {
			Long cantidadAgendas = monitorDAO.listarAgendasPorEnfermedad(idEnfermedad);
			Long cantidadVacunados = monitorDAO.listarVacunadosPorEnfermedad(idEnfermedad);
			List<MonitorEnfermedadVacunasDTO> vacunas = monitorDAO.listarVacunasPorEnfermedad(idEnfermedad);
			List<MonitorEnfermedadPlanesDTO> planes = monitorDAO.listarPlanesPorEnfermedad(idEnfermedad);
			MonitorEnfermedadDTO datos = MonitorEnfermedadDTO.builder()
					.cantidadAgendasHoy(cantidadAgendas)
					.cantidadVacunadosHoy(cantidadVacunados)
					.vacunas(vacunas)
					.planes(planes)
					.build();
			return datos;
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL); 
		}
	}

	/* Filtro por vacuna */
	@Override
	public MonitorVacunaDTO listarDatosPorVacuna(Long idVacuna) throws VacunasUyException {
		try {
			List<MonitorVacunaDosisDTO> agendas = monitorDAO.listarAgendasPorDosis(idVacuna);
			List<MonitorVacunaDosisDTO> vacunados = monitorDAO.listarVacunadosPorDosis(idVacuna);
			MonitorVacunaDTO datos = MonitorVacunaDTO.builder()
					.agendas(agendas)
					.vacunados(vacunados)
					.build();
			return datos;
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	/* Filtro por plan */
	@Override
	public MonitorPlanDTO listarDatosPorPlan(Long idPlan) throws VacunasUyException {
		try {
			Long cantidadAgendasHoy = monitorDAO.listarAgendasPorPlan(idPlan);
			Long cantidadVacunadosHoy = monitorDAO.listarVacunadosPorPlan(idPlan);
			Long cantidadTotalVacunados = monitorDAO.listarTotalVacunadosPorPlan(idPlan);
			PlanVacunacionDTO plan = planVacunacionConverter.fromEntity(planVacunacionDAO.listarPorId(idPlan));
			MonitorPlanDTO datos = MonitorPlanDTO.builder()
					.cantidadAgendasHoy(cantidadAgendasHoy)
					.cantidadVacunadosHoy(cantidadVacunadosHoy)
					.cantidadTotalVacunados(cantidadTotalVacunados)
					.plan(plan)
					.build();
			return datos;
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
}
