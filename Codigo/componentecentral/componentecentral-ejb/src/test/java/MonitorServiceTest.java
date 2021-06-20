import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.MonitorServiceImpl;
import vacunasuy.componentecentral.converter.PlanVacunacionConverter;
import vacunasuy.componentecentral.dao.MonitorDAOImpl;
import vacunasuy.componentecentral.dao.PlanVacunacionDAOImpl;
import vacunasuy.componentecentral.dto.MonitorEnfermedadDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadPlanesDTO;
import vacunasuy.componentecentral.dto.MonitorEnfermedadVacunasDTO;
import vacunasuy.componentecentral.dto.MonitorPlanDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDTO;
import vacunasuy.componentecentral.dto.MonitorVacunaDosisDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class MonitorServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private MonitorServiceImpl monitorService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private MonitorDAOImpl monitorDAO;
	
	@Mock
	private PlanVacunacionDAOImpl planVacunacionDAO;
	
	@Mock
	private PlanVacunacionConverter planVacunacionConverter;
	
	@Before
	public void init() {
		monitorService = new MonitorServiceImpl();
		monitorService.planVacunacionDAO = this.planVacunacionDAO;
		monitorService.monitorDAO = this.monitorDAO;
		monitorService.planVacunacionConverter = this.planVacunacionConverter;
	}
	
	@Test
	public void listarDatosPorEnfermedad() {
		List<MonitorEnfermedadVacunasDTO> vacunas = new ArrayList<MonitorEnfermedadVacunasDTO>();
		MonitorEnfermedadVacunasDTO mevdto = new MonitorEnfermedadVacunasDTO("nombre", 10L);
		vacunas.add(mevdto);
		List<MonitorEnfermedadPlanesDTO> planes = new ArrayList<MonitorEnfermedadPlanesDTO>();
		MonitorEnfermedadPlanesDTO mepdto = new MonitorEnfermedadPlanesDTO(1L, "1", "nombre");
		planes.add(mepdto);
		Mockito.when(monitorService.monitorDAO.listarAgendasPorEnfermedad(1L)).thenReturn(1L);
		Mockito.when(monitorService.monitorDAO.listarVacunadosPorEnfermedad(1L)).thenReturn(1L);
		Mockito.when(monitorService.monitorDAO.listarVacunasPorEnfermedad(1L)).thenReturn(vacunas);
		Mockito.when(monitorService.monitorDAO.listarPlanesPorEnfermedad(1L)).thenReturn(planes);
		try {
			MonitorEnfermedadDTO meDTO = monitorService.listarDatosPorEnfermedad(1L);
			assertEquals(meDTO.getVacunas(), vacunas);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarDatosPorVacuna() {
		MonitorVacunaDosisDTO mvdDTO1 = new MonitorVacunaDosisDTO(1, 1);
		List<MonitorVacunaDosisDTO> agendas = new ArrayList<MonitorVacunaDosisDTO>();
		agendas.add(mvdDTO1);
		MonitorVacunaDosisDTO mvdDTO2 = new MonitorVacunaDosisDTO(1, 1);
		List<MonitorVacunaDosisDTO> vacunados = new ArrayList<MonitorVacunaDosisDTO>();
		agendas.add(mvdDTO2);
		Mockito.when(monitorService.monitorDAO.listarAgendasPorDosis(1L)).thenReturn(agendas);
		Mockito.when(monitorService.monitorDAO.listarVacunadosPorDosis(1L)).thenReturn(vacunados);
		try {
			MonitorVacunaDTO meDTO = monitorService.listarDatosPorVacuna(1L);
			assertEquals(meDTO.getAgendas(), agendas);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void listarDatosPorPlan() {
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 1, null, null, null, null);
		PlanVacunacionDTO planDTO = new PlanVacunacionDTO(1L, 1, 1, null, null, null, null);
		Mockito.when(monitorService.monitorDAO.listarAgendasPorPlan(1L)).thenReturn(1L);
		Mockito.when(monitorService.monitorDAO.listarVacunadosPorPlan(1L)).thenReturn(1L);
		Mockito.when(monitorService.monitorDAO.listarTotalVacunadosPorPlan(1L)).thenReturn(1L);
		Mockito.when(planVacunacionDAO.listarPorId(1L)).thenReturn(plan);
		Mockito.when(planVacunacionConverter.fromEntity(plan)).thenReturn(planDTO);
		try {
			MonitorPlanDTO mpDTO = monitorService.listarDatosPorPlan(1L);
			assertEquals(mpDTO.getPlan(), planDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
}