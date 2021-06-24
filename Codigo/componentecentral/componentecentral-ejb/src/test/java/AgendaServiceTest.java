import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.AgendaServiceImpl;
import vacunasuy.componentecentral.business.NotificacionServiceImpl;
import vacunasuy.componentecentral.business.UsuarioServiceImpl;
import vacunasuy.componentecentral.business.VacunatorioServiceImpl;
import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.dao.AgendaDAOImpl;
import vacunasuy.componentecentral.dao.PlanVacunacionDAOImpl;
import vacunasuy.componentecentral.dao.PuestoDAOImpl;
import vacunasuy.componentecentral.dao.UsuarioDAOImpl;
import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class AgendaServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private AgendaServiceImpl agendaService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	@Mock
	private AgendaDAOImpl agendaDAO;
	
	@Mock
	private PuestoDAOImpl puestoDAO;
	
	@Mock
	private UsuarioDAOImpl usuarioDAO;
	
	@Mock
	private PlanVacunacionDAOImpl planVacunacionDAO;
	
	@Mock
	private VacunatorioServiceImpl vacunatorioService;
	
	@Mock
	private UsuarioServiceImpl usuarioService;
	
	@Mock
	private NotificacionServiceImpl notificacionService;
	
	@Mock
	private AgendaConverter agendaConverter;
	
	
	@Before
	public void init() {
		agendaService = new AgendaServiceImpl();
		agendaService.usuarioService = this.usuarioService;
		agendaService.notificacionService = this.notificacionService;
		agendaService.vacunatorioService = this.vacunatorioService;
		agendaService.agendaDAO = this.agendaDAO;
		agendaService.puestoDAO = this.puestoDAO;
		agendaService.usuarioDAO = this.usuarioDAO;
		agendaService.planVacunacionDAO = this.planVacunacionDAO;
		agendaService.agendaConverter = this.agendaConverter;
	}
	
	@Test
	public void listar() {
		List<Agenda> agendas = new ArrayList<Agenda>();
		agendas.add(new Agenda(1L, null, 1, null, null, null));
		List<AgendaDTO> agendasDTO = new ArrayList<AgendaDTO>();
		agendasDTO.add(new AgendaDTO(1L, null, null, null));
		Mockito.when(agendaService.agendaDAO.listar()).thenReturn(agendas);
		Mockito.when(agendaService.agendaConverter.fromEntity(agendas)).thenReturn(agendasDTO);
		try {
			List<AgendaDTO> agendasesperadas = agendaService.listar();
			assertEquals(1, agendasesperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Agenda a = new Agenda(1L, null, 1, null, null, null);
		AgendaDTO aDTO = new AgendaDTO(1L, null, null, null);
		Mockito.when(agendaService.agendaDAO.listarPorId(1L)).thenReturn(a);
		Mockito.when(agendaService.agendaConverter.fromEntity(a)).thenReturn(aDTO);
		try {
			AgendaDTO af = agendaService.listarPorId(1L);
			assertEquals(a.getId(), af.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_agendaNull() throws VacunasUyException {
		Agenda a = null;
		Mockito.when(agendaService.agendaDAO.listarPorId(1L)).thenReturn(a);
		@SuppressWarnings("unused")
		AgendaDTO af = agendaService.listarPorId(1L);
	}
	
	@Test
	public void crear() {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 2, 2, 2, null);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2000-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 99, null, null, sectores, vacuna);
		Mockito.when(agendaService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		try {
			Mockito.when(agendaService.usuarioService.existeAgenda(1L, 1L)).thenReturn(false);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime fecha2 = LocalDateTime.parse("2021-07-23 18:00", formato2);
		Agenda agenda = new Agenda (1L, fecha2, 1, p, pv, u);  
		Mockito.when(agendaService.agendaConverter.fromCrearDTO(Mockito.any())).thenReturn(agenda);
		Mockito.when(agendaService.agendaDAO.crear(agenda)).thenReturn(agenda);
		AgendaMinDTO aMDTO = new AgendaMinDTO(1L, "2021-07-23T18:00", null);
		Mockito.when(agendaService.agendaConverter.fromEntityToMin(agenda)).thenReturn(aMDTO);
		try {
			Mockito.doNothing().when(agendaService.notificacionService).enviarNotificacionFirebase(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		try {
			List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
			assertEquals(2, agendasEsperadas.size());
		}catch (VacunasUyException e) {
			e.printStackTrace();	
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_ciudadanoNull() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		Usuario u = null;
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_puestoNull() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2000-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Puesto p = null;
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_puestoInvalido() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2000-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(false);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_PlanVacunacionNull() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2000-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		PlanVacunacion pv = null;
		Mockito.when(agendaService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_agendaExistente() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 2, 2, 2, null);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2000-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 99, null, null, sectores, vacuna);
		Mockito.when(agendaService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		try {
			Mockito.when(agendaService.usuarioService.existeAgenda(1L, 1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_edadInvalida() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 2, 2, 2, null);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2018-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		PlanVacunacion pv = new PlanVacunacion(1L, 18, 99, null, null, sectores, vacuna);
		Mockito.when(agendaService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		try {
			Mockito.when(agendaService.usuarioService.existeAgenda(1L, 1L)).thenReturn(false);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_sectorInvalido() throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 2, 2, 2, null);
		SectorLaboral sector = new SectorLaboral(1L, "Sector1");
		SectorLaboral sector2 = new SectorLaboral(2L, "Sector2");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(sector2);
		Vacunatorio v = new Vacunatorio(1L, "nombre", null, null, "direccion", null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2018-06-01", formato);
		Usuario u = new Usuario(1L, "123456", null, null, null, fecha, null, "tokenFireBase", null, sector, null, null, null);
		Mockito.when(agendaService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		List<Agenda> agendasPuesto = new ArrayList<Agenda>();
		Puesto p = new Puesto(1L, 1, v, agendasPuesto, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			Mockito.when(agendaService.vacunatorioService.vacunatorioTienePlan(1L,1L)).thenReturn(true);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		PlanVacunacion pv = new PlanVacunacion(1L, 18, 99, null, null, sectores, vacuna);
		Mockito.when(agendaService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		try {
			Mockito.when(agendaService.usuarioService.existeAgenda(1L, 1L)).thenReturn(false);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		List<AgendaMinDTO> agendasEsperadas = agendaService.crear(aCDTO);
			
	}
	
	@Test
	public void editar () {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23T18:30", 1L, 1L, 1L);
		Agenda agenda = new Agenda (1L, null, 1, null, null, null);  
		Mockito.when(agendaService.agendaDAO.listarPorId(1L)).thenReturn(agenda);
		Puesto p = new Puesto(1L, 80, null, null, null);
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
		Mockito.when(agendaService.agendaDAO.editar(agenda)).thenReturn(agenda);
		AgendaDTO agendaDTO = new AgendaDTO(1L, null, null, null);
 		Mockito.when(agendaService.agendaConverter.fromEntity(agenda)).thenReturn(agendaDTO);
 		try {
 			AgendaDTO agendaEsperada = agendaService.editar(1L, aCDTO);
 			assertEquals(agendaEsperada.getId(), agendaDTO.getId());
 		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_agendaNull () throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23T18:30", 1L, 1L, 1L);
		Agenda agenda = null;
		Mockito.when(agendaService.agendaDAO.listarPorId(1L)).thenReturn(agenda);
 		@SuppressWarnings("unused")
		AgendaDTO agendaEsperada = agendaService.editar(1L, aCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_puestoNull () throws VacunasUyException {
		AgendaCrearDTO aCDTO = new AgendaCrearDTO("2021-07-23T18:30", 1L, 1L, 1L);
		Agenda agenda = new Agenda (1L, null, 1, null, null, null);  
		Mockito.when(agendaService.agendaDAO.listarPorId(1L)).thenReturn(agenda);
		Puesto p = null;
		Mockito.when(agendaService.puestoDAO.listarPorId(1L)).thenReturn(p);
 		@SuppressWarnings("unused")
		AgendaDTO agendaEsperada = agendaService.editar(1L, aCDTO);
	}
	
	@Test
	public void cancelarAgenda () {
		try {
			Mockito.doNothing().when(agendaService.usuarioService).cancelarAgenda(1L, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		try {
			agendaService.cancelarAgenda(1L, 1L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
}