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

import vacunasuy.componentecentral.business.PlanVacunacionServiceImpl;
import vacunasuy.componentecentral.converter.PlanVacunacionConverter;
import vacunasuy.componentecentral.dao.PlanVacunacionDAOImpl;
import vacunasuy.componentecentral.dao.SectorLaboralDAOImpl;
import vacunasuy.componentecentral.dao.VacunaDAOImpl;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class PlanVacunacionServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private PlanVacunacionServiceImpl planVacunacionService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private PlanVacunacionDAOImpl planVacunacionDAO;
	
	@Mock
	private SectorLaboralDAOImpl sectorLaboralDAO;
	
	@Mock
	private VacunaDAOImpl vacunaDAO;
	
	@Mock
	private PlanVacunacionConverter planVacunacionConverter;
	
	@Before
	public void init() {
		planVacunacionService = new PlanVacunacionServiceImpl();
		planVacunacionService.planVacunacionDAO = this.planVacunacionDAO;
		planVacunacionService.sectorLaboralDAO = this.sectorLaboralDAO;
		planVacunacionService.vacunaDAO = this.vacunaDAO;
		planVacunacionService.planVacunacionConverter = this.planVacunacionConverter;
	}
	
	@Test
	public void listar() {
		List<PlanVacunacion> planes = new ArrayList<PlanVacunacion>();
		planes.add(new PlanVacunacion(1L, 1, 1, null, null, null, null));
		List<PlanVacunacionDTO> planesDTO = new ArrayList<PlanVacunacionDTO>();
		planesDTO.add(new PlanVacunacionDTO(1L, 1, 1, null, null, null, null));
		Mockito.when(planVacunacionService.planVacunacionDAO.listar()).thenReturn(planes);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromEntity(Mockito.anyList())).thenReturn(planesDTO);
		try {
			List<PlanVacunacionDTO> planesesperados = planVacunacionService.listar();
			assertEquals(1, planesesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPlanesVigentes() {
		List<PlanVacunacion> planes = new ArrayList<PlanVacunacion>();
		planes.add(new PlanVacunacion(1L, 1, 1, null, null, null, null));
		List<PlanVacunacionDTO> planesDTO = new ArrayList<PlanVacunacionDTO>();
		planesDTO.add(new PlanVacunacionDTO(1L, 1, 1, null, null, null, null));
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPlanesVigentes()).thenReturn(planes);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromEntity(planes)).thenReturn(planesDTO);
		try {
			List<PlanVacunacionDTO> planesesperados = planVacunacionService.listarPlanesVigentes();
			assertEquals(1, planesesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 1, null, null, null, null);
		PlanVacunacionDTO planDTO = new PlanVacunacionDTO(1L, 1, 1, null, null, null, null);
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(plan);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromEntity(plan)).thenReturn(planDTO);
		try {
			PlanVacunacionDTO pv = planVacunacionService.listarPorId(1L);
			assertEquals(plan.getId(), pv.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_planNull() throws VacunasUyException {
		PlanVacunacion plan = null;
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(plan);
		@SuppressWarnings("unused")
		PlanVacunacionDTO pv = planVacunacionService.listarPorId(1L);
		
	}
	
	@Test
	public void crear() {
		Vacuna v = new Vacuna(1L, null, 0, 0, 0, null);
		List<Long> longs =new ArrayList<Long>();
		longs.add(1L);
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		SectorLaboral sector = new SectorLaboral(1L, "nombre");
		sectores.add(sector);
		List<SectorLaboralDTO> sectoresDTO = new ArrayList<SectorLaboralDTO>();
		SectorLaboralDTO sectorDTO = new SectorLaboralDTO(1L, "nombre");
		sectoresDTO.add(sectorDTO);
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 99, null, null, sectores, v);
		PlanVacunacionCrearDTO pvCDTO = new PlanVacunacionCrearDTO (1, 99, null, null, longs, 1L);
		PlanVacunacionDTO planDTO = new PlanVacunacionDTO(1L, 1, 99, null, null, null, null);
		Mockito.when(planVacunacionService.sectorLaboralDAO.listarPorId(1L)).thenReturn(sector);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromCrearDTO(pvCDTO)).thenReturn(plan);
		Mockito.when(planVacunacionService.vacunaDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(planVacunacionService.planVacunacionDAO.crear(plan)).thenReturn(plan);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromEntity(plan)).thenReturn(planDTO);
		try {
			PlanVacunacionDTO pv = planVacunacionService.crear(pvCDTO);
			assertEquals(plan.getId(), pv.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_VacunaNull() throws VacunasUyException {
		Vacuna v = null;
		List<Long> longs =new ArrayList<Long>();
		longs.add(1L);
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		SectorLaboral sector = new SectorLaboral(1L, "nombre");
		sectores.add(sector);
		List<SectorLaboralDTO> sectoresDTO = new ArrayList<SectorLaboralDTO>();
		SectorLaboralDTO sectorDTO = new SectorLaboralDTO(1L, "nombre");
		sectoresDTO.add(sectorDTO);
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 99, null, null, sectores, v);
		PlanVacunacionCrearDTO pvCDTO = new PlanVacunacionCrearDTO (1, 99, null, null, longs, 1L);
		Mockito.when(planVacunacionService.sectorLaboralDAO.listarPorId(1L)).thenReturn(sector);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromCrearDTO(pvCDTO)).thenReturn(plan);
		Mockito.when(planVacunacionService.vacunaDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		PlanVacunacionDTO pv = planVacunacionService.crear(pvCDTO);
			
	}
	
	@Test
	public void editar() {
		Vacuna v = new Vacuna(1L, null, 0, 0, 0, null);
		List<Long> longs =new ArrayList<Long>();
		longs.add(1L);
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		SectorLaboral sector = new SectorLaboral(1L, "nombre");
		sectores.add(sector);
		List<SectorLaboralDTO> sectoresDTO = new ArrayList<SectorLaboralDTO>();
		SectorLaboralDTO sectorDTO = new SectorLaboralDTO(1L, "nombre");
		sectoresDTO.add(sectorDTO);
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 99, null, null, sectores, v);
		PlanVacunacion plan_editado = new PlanVacunacion(1L, 1, 98, null, null, sectores, v);
		PlanVacunacionCrearDTO pvCDTO = new PlanVacunacionCrearDTO (1, 98, "2022-12-01 00:00", "2022-12-02 00:00", longs, 1L);
		PlanVacunacionDTO planDTO = new PlanVacunacionDTO(1L, 1, 98, null, null, null, null);
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(plan);
		Mockito.when(planVacunacionService.vacunaDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(planVacunacionService.sectorLaboralDAO.listarPorId(1L)).thenReturn(sector);
		Mockito.when(planVacunacionService.planVacunacionDAO.editar(plan)).thenReturn(plan_editado);
		Mockito.when(planVacunacionService.planVacunacionConverter.fromEntity(plan_editado)).thenReturn(planDTO);
		try {
			PlanVacunacionDTO pv = planVacunacionService.editar(1L, pvCDTO);
			assertEquals(pv.getEdadMaxima(), 98);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_planNull() throws VacunasUyException {
		PlanVacunacionCrearDTO pvCDTO = new PlanVacunacionCrearDTO (1, 98, "2022-12-01 00:00", "2022-12-02 00:00", null, 1L);
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		PlanVacunacionDTO pv = planVacunacionService.editar(1L, pvCDTO);
			
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_vacunaNull() throws VacunasUyException {
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 99, null, null, null, null);
		PlanVacunacionCrearDTO pvCDTO = new PlanVacunacionCrearDTO (1, 98, "2022-12-01 00:00", "2022-12-02 00:00", null, 1L);
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(plan);
		Mockito.when(planVacunacionService.vacunaDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		PlanVacunacionDTO pv = planVacunacionService.editar(1L, pvCDTO);	
	}
	
	@Test
	public void eliminar() {
		PlanVacunacion pv = new PlanVacunacion (1L, 1, 1, null, null, null, null);
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		try {
			planVacunacionService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(planVacunacionService.planVacunacionDAO.listarPorId(1L)).thenReturn(null);
		planVacunacionService.eliminar(1L);
	}
	
}