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
import vacunasuy.componentecentral.dao.EnfermedadDAOImpl;
import vacunasuy.componentecentral.dao.ReporteDAOImpl;
import vacunasuy.componentecentral.dao.SectorLaboralDAOImpl;
import vacunasuy.componentecentral.dao.VacunaDAOImpl;
import vacunasuy.componentecentral.dto.ReporteActoVacunalDTO;
import vacunasuy.componentecentral.dto.ReporteEvolucionTiempoDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Usuario;
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
	public void listarPorEvolucionEnTiempo() {
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		ReporteEvolucionTiempoDTO reporteDTO = new ReporteEvolucionTiempoDTO("2021-10-10", 1);
		List<ReporteEvolucionTiempoDTO> reportesDTO = new ArrayList<ReporteEvolucionTiempoDTO>();
		reportesDTO.add(reporteDTO);
		PlanVacunacion planVacunacion = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		@SuppressWarnings("unused")
		ActoVacunal actoVacunal = new ActoVacunal(1L, LocalDate.parse("2021-10-10"), 1, planVacunacion);
		Mockito.when(reporteService.vacunaDAO.listarPorId(vacuna.getId())).thenReturn(vacuna);
		Mockito.when(reporteService.reporteDAO.listarPorEvolucionEnTiempo("2021-10-01", "2021-10-31", vacuna.getId()))
			.thenReturn(reportesDTO);
		try {
			List<ReporteEvolucionTiempoDTO> reportesDTOResultado = reporteService.listarPorEvolucionEnTiempo("2021-10-01", 
					"2021-10-31", vacuna.getId());
			assertEquals(reportesDTOResultado, reportesDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorEvolucionEnTiempoVacunaNull() throws VacunasUyException {
		Mockito.when(reporteService.vacunaDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		List<ReporteEvolucionTiempoDTO> reportesDTOResultado = reporteService.listarPorEvolucionEnTiempo("2021-10-01", "2021-10-31", 1L);
	}
	
	@Test
	public void listarPorEdad() {
		Enfermedad enfermedad = new Enfermedad(1L, null);
		@SuppressWarnings("unused")
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		Mockito.when(reporteService.enfermedadDAO.listarPorId(enfermedad.getId())).thenReturn(enfermedad);
		LocalDate hoy = LocalDate.now();
		LocalDate fechaEdadInicio = hoy.minusYears(20);
		LocalDate fechaEdadFin = hoy.minusYears(70);
		PlanVacunacion planVacunacion = new PlanVacunacion(1L, 1, 1, null, null, null, null);
		ActoVacunal actoVacunal = new ActoVacunal(1L, LocalDate.parse("2021-10-10"), 1, planVacunacion);
		List<ActoVacunal> actosVacunales = new ArrayList<ActoVacunal>();
		actosVacunales.add(actoVacunal);
		@SuppressWarnings("unused")
		Usuario usuario = new Usuario(1L, null, null, null, null, LocalDate.parse("1993-10-27"), null, null, null, null, null, 
				actosVacunales, null);
		ReporteActoVacunalDTO reporteDTO = new ReporteActoVacunalDTO("Vacuna", 1);
		List<ReporteActoVacunalDTO> reportesDTO = new ArrayList<ReporteActoVacunalDTO>();
		reportesDTO.add(reporteDTO);
		Mockito.when(reporteService.reporteDAO.listarPorEdad("2021-10-01", "2021-10-31", fechaEdadInicio.toString(), 
				fechaEdadFin.toString(), enfermedad.getId())).thenReturn(reportesDTO);
		try {
			List<ReporteActoVacunalDTO> reportesDTOResultado = reporteService.listarPorEdad("2021-10-01", "2021-10-31", 
					20, 70, enfermedad.getId());
			assertEquals(reportesDTOResultado, reportesDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = VacunasUyException.class)
	public void listarPorEdadEnfermedadNull() throws VacunasUyException {
		Mockito.when(reporteService.enfermedadDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		List<ReporteActoVacunalDTO> reportesDTOResultado = reporteService.listarPorEdad("2021-10-01", "2021-10-31", 20, 70, 1L);
	}
	
	@Test
	public void listarPorSectorLaboral() {
		SectorLaboral sector = new SectorLaboral(1L, null);
		Mockito.when(reporteService.sectorLaboralDAO.listarPorId(1L)).thenReturn(sector);
		Enfermedad enfermedad = new Enfermedad(1L, null);
		@SuppressWarnings("unused")
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		Mockito.when(reporteService.enfermedadDAO.listarPorId(enfermedad.getId())).thenReturn(enfermedad);
		PlanVacunacion planVacunacion = new PlanVacunacion(1L, 1, 1, null, null, null, null);
		ActoVacunal actoVacunal = new ActoVacunal(1L, LocalDate.parse("2021-10-10"), 1, planVacunacion);
		List<ActoVacunal> actosVacunales = new ArrayList<ActoVacunal>();
		actosVacunales.add(actoVacunal);
		@SuppressWarnings("unused")
		Usuario usuario = new Usuario(1L, null, null, null, null, LocalDate.parse("1993-10-27"), null, null, null, sector, null, 
				actosVacunales, null);
		ReporteActoVacunalDTO reporteDTO = new ReporteActoVacunalDTO("Vacuna", 1);
		List<ReporteActoVacunalDTO> reportesDTO = new ArrayList<ReporteActoVacunalDTO>();
		reportesDTO.add(reporteDTO);
		Mockito.when(reporteService.reporteDAO.listarPorSectorLaboral("2021-10-01", "2021-10-31", sector.getId(), 
				enfermedad.getId())).thenReturn(reportesDTO);
		try {
			List<ReporteActoVacunalDTO> reportesDTOResultado = reporteService.listarPorSectorLaboral("2021-10-01", "2021-10-31", 
					sector.getId(), enfermedad.getId());
			assertEquals(reportesDTOResultado, reportesDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void listarPorSectorLaboralSectorNull() throws VacunasUyException {
		Mockito.when(reporteService.sectorLaboralDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		List<ReporteActoVacunalDTO> reportesDTOResultado = reporteService.listarPorSectorLaboral("2021-10-01", "2021-10-31", 1L, 1L);
	}
	
	@Test (expected = VacunasUyException.class)
	public void listarPorSectorLaboralEnfermedadNull() throws VacunasUyException {
		SectorLaboral sector = new SectorLaboral(1L, null);
		Mockito.when(reporteService.sectorLaboralDAO.listarPorId(1L)).thenReturn(sector);
		Mockito.when(reporteService.enfermedadDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		List<ReporteActoVacunalDTO> reportesDTOResultado =reporteService.listarPorSectorLaboral("2021-10-01", "2021-10-31", sector.getId(), 1L);
	}

}
