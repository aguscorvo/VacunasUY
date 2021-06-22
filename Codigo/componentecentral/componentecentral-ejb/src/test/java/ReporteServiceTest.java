import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.ReporteServiceImpl;
import vacunasuy.componentecentral.business.RolServiceImpl;
import vacunasuy.componentecentral.converter.RolConverter;
import vacunasuy.componentecentral.dao.EnfermedadDAOImpl;
import vacunasuy.componentecentral.dao.ReporteDAOImpl;
import vacunasuy.componentecentral.dao.RolDAOImpl;
import vacunasuy.componentecentral.dao.SectorLaboralDAOImpl;
import vacunasuy.componentecentral.dao.VacunaDAOImpl;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class ReporteServiceTest {

	@InjectMocks
	private ReporteServiceImpl reporteService;
	
	@Mock
	private ReporteDAOImpl reporteDAO;
	
	@Mock
	private VacunaDAOImpl vacunaDAO;
	
	@Mock
	private SectorLaboralDAOImpl sectorLaboralDAO;
	
	@Mock
	private EnfermedadDAOImpl enfermedadDAO;
	
	@Before
	public void init() {
		reporteService = new ReporteServiceImpl();
		reporteService.reporteDAO = this.reporteDAO;
		reporteService.vacunaDAO = this.vacunaDAO;
		reporteService.sectorLaboralDAO = this.sectorLaboralDAO;
		reporteService.enfermedadDAO = this.enfermedadDAO;
	}
	
	@Test
	public void listar() {
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		Mockito.when(reporteService.vacunaDAO.listarPorId(1L)).thenReturn(vacuna);
		ReporteEvolucionTiempoDTO reporteDTO = new ReporteEvolucionTiempoDTO("2021-10-10", 1);
		List<ReporteEvolucionTiempoDTO> reportesDTO = new ArrayList<ReporteEvolucionTiempoDTO>();
		reportesDTO.add(reporteDTO);
		PlanVacunacion planVacunacion = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		ActoVacunal actoVacunal = new ActoVacunal(1L, LocalDate.parse("2021-10-10"), 1, planVacunacion);
		Mockito.when(reporteService.reporteDAO.listarPorEvolucionEnTiempo("2021-10-01", "2021-10-31", vacuna.getId()));
		try {
			List<ReporteEvolucionTiempoDTO> reportesDTOResultado = reporteService.listarPorEvolucionEnTiempo("2021-10-01", "2021-10-31", vacuna.getId());
			assertEquals(reportesDTOResultado, reportesDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void listarPorId() {
//		Rol rol = new Rol(1L, "Prueba");
//		RolDTO r3 = new RolDTO(1L, "Prueba");
//		Mockito.when(rolService.rolDAO.listarPorId(1L)).thenReturn(rol);
//		Mockito.when(rolService.rolConverter.fromEntity(rol)).thenReturn(r3);
//		try {
//			RolDTO rol1 = rolService.listarPorId(1L);
//			assertEquals(rol.getId(), rol1.getId());
//		} catch (VacunasUyException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(expected = VacunasUyException.class)
//	public void listarPorId_null () throws VacunasUyException {
//		Rol rol = null;
//		Mockito.when(rolService.rolDAO.listarPorId(1L)).thenReturn(rol);
//		@SuppressWarnings("unused")
//		RolDTO rolDTO = rolService.listarPorId(1L);	
//	}
}
