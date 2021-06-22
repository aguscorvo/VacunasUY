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

import vacunasuy.componentecentral.business.ActoVacunalServiceImpl;
import vacunasuy.componentecentral.business.StockServiceImpl;
import vacunasuy.componentecentral.business.UsuarioServiceImpl;
import vacunasuy.componentecentral.converter.ActoVacunalConverter;
import vacunasuy.componentecentral.dao.ActoVacunalDAOImpl;
import vacunasuy.componentecentral.dao.EnfermedadDAOImpl;
import vacunasuy.componentecentral.dao.PlanVacunacionDAOImpl;
import vacunasuy.componentecentral.dao.UsuarioDAOImpl;
import vacunasuy.componentecentral.dao.VacunatorioDAOImpl;
import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;
import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Stock;
import vacunasuy.componentecentral.entity.StockID;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class ActoVacunalServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private ActoVacunalServiceImpl actoVacunalService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	@Mock
	private UsuarioServiceImpl usuarioService;
	
	@Mock
	private StockServiceImpl stockService;
	
	@Mock
	private ActoVacunalDAOImpl actoVacunalDAO;
	
	@Mock
	private EnfermedadDAOImpl enfermedadDAO;
	
	@Mock
	private VacunatorioDAOImpl vacunatorioDAO;
	
	@Mock
	private PlanVacunacionDAOImpl planVacunacionDAO;
	
	@Mock
	private UsuarioDAOImpl usuarioDAO;
	
	@Mock
	private ActoVacunalConverter actoVacunalConverter;
	
	@Before
	public void init() {
		actoVacunalService = new ActoVacunalServiceImpl();
		actoVacunalService.usuarioService =this.usuarioService;
		actoVacunalService.stockService =this.stockService;
		actoVacunalService.actoVacunalDAO = this.actoVacunalDAO;
		actoVacunalService.enfermedadDAO = this.enfermedadDAO;
		actoVacunalService.vacunatorioDAO = this.vacunatorioDAO;
		actoVacunalService.planVacunacionDAO = this.planVacunacionDAO;
		actoVacunalService.usuarioDAO = this.usuarioDAO;
		actoVacunalService.actoVacunalConverter = this.actoVacunalConverter;
	}
	
	@Test
	public void listar() {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		actos.add(new ActoVacunal(1L, null, 1, null));
		List<ActoVacunalDTO> actosVacunalesDTO = new ArrayList<ActoVacunalDTO>();
		actosVacunalesDTO.add(new ActoVacunalDTO(1L, null, null));
		Mockito.when(actoVacunalService.actoVacunalDAO.listar()).thenReturn(actos);
		Mockito.when(actoVacunalService.actoVacunalConverter.fromEntity(actos)).thenReturn(actosVacunalesDTO);
		try {
			List<ActoVacunalDTO> actosesperados = actoVacunalService.listar();
			assertEquals(1, actosesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		ActoVacunal av = new ActoVacunal(1L, null, 1, null);
		ActoVacunalDTO avDTO = new ActoVacunalDTO(1L, null, null);
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		Mockito.when(actoVacunalService.actoVacunalConverter.fromEntity(av)).thenReturn(avDTO);
		try {
			ActoVacunalDTO avf = actoVacunalService.listarPorId(1L);
			assertEquals(av.getId(), avf.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_actoNull() throws VacunasUyException {
		ActoVacunal av = null;
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		@SuppressWarnings("unused")
		ActoVacunalDTO avf = actoVacunalService.listarPorId(1L);
	}
	
	@Test
	public void listarActosVacunalesPorUsuarioEnfermedad() {
		List<ActoVacunalCertificadoDTO> actos = new ArrayList<ActoVacunalCertificadoDTO> ();
		actos.add(new ActoVacunalCertificadoDTO("fecha", "nombreAC"));
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Enfermedad enf = new Enfermedad(1L, "nombreE");
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Mockito.when(actoVacunalService.enfermedadDAO.listarPorId(1L)).thenReturn(enf);
		Mockito.when(actoVacunalService.actoVacunalDAO.listarActosVacunalesPorUsuarioEnfermedad(1L, 1L)).thenReturn(actos);
		try {
			List<ActoVacunalCertificadoDTO> actosesperados = actoVacunalService.listarActosVacunalesPorUsuarioEnfermedad(1L, 1L);
			assertEquals(1, actosesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarActosVacunalesPorUsuarioEnfermedad_usuarioNull() throws VacunasUyException {
		List<ActoVacunalCertificadoDTO> actos = new ArrayList<ActoVacunalCertificadoDTO> ();
		actos.add(new ActoVacunalCertificadoDTO("fecha", "nombreAC"));
		Usuario u = null;
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		@SuppressWarnings("unused")
		List<ActoVacunalCertificadoDTO> actosesperados = actoVacunalService.listarActosVacunalesPorUsuarioEnfermedad(1L, 1L);
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarActosVacunalesPorUsuarioEnfermedad_enfermedadNull() throws VacunasUyException {
		List<ActoVacunalCertificadoDTO> actos = new ArrayList<ActoVacunalCertificadoDTO> ();
		actos.add(new ActoVacunalCertificadoDTO("fecha", "nombreAC"));
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Enfermedad enf = null;
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Mockito.when(actoVacunalService.enfermedadDAO.listarPorId(1L)).thenReturn(enf);
		@SuppressWarnings("unused")
		List<ActoVacunalCertificadoDTO> actosesperados = actoVacunalService.listarActosVacunalesPorUsuarioEnfermedad(1L, 1L);
			
	}
	
	@Test
	public void crear () {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Stock s = new Stock(new StockID(1L, 1L), 1L);
		try {
			Mockito.when(actoVacunalService.stockService.listarStockPorVacuna(1L,1L)).thenReturn(s);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		ActoVacunal av = new ActoVacunal(1L, null, 1, pv);
		Mockito.when(actoVacunalService.actoVacunalConverter.fromCrearDTO(avCDTO)).thenReturn(av);
		Mockito.when(actoVacunalService.actoVacunalDAO.crear(av)).thenReturn(av);
		try {
			Mockito.doNothing().when(actoVacunalService.usuarioService).agregarActoVacunal(1L, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		try {
			Mockito.doNothing().when(actoVacunalService.stockService).restarStock(v, vacuna, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		ActoVacunalDTO avDTO = new ActoVacunalDTO(1L, null, null);
		Mockito.when(actoVacunalService.actoVacunalConverter.fromEntity(av)).thenReturn(avDTO);
		
		try {
			ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
			assertEquals(avCreado.getId(), avDTO.getId());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_ciudadanoNull() throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = null;
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		@SuppressWarnings("unused")
		ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_pvNull() throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		PlanVacunacion pv = null;
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		@SuppressWarnings("unused")
		ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_vacunatorioNull() throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		Vacunatorio v = null;
		Mockito.when(actoVacunalService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_StockNull() throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Stock s = null;
		try {
			Mockito.when(actoVacunalService.stockService.listarStockPorVacuna(1L,1L)).thenReturn(s);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_Stock0 () throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("fecha", 1L, 1L, 1L, 3);
		Usuario u = new Usuario (1L, null, null, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.usuarioDAO.listarPorId(1L)).thenReturn(u);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 1, null, null, null, vacuna);
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pv);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(actoVacunalService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Stock s = new Stock(new StockID(1L, 1L), 0L);
		try {
			Mockito.when(actoVacunalService.stockService.listarStockPorVacuna(1L,1L)).thenReturn(s);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		ActoVacunalDTO avCreado = actoVacunalService.crear(avCDTO);
			
	}
	
	@Test
	public void editar () {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("2021-06-22", 1L, 1L, 1L, 3);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 99, null, null, null, vacuna);
		ActoVacunal av = new ActoVacunal(1L, null, 1, pv);
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		PlanVacunacion pvEditar = new PlanVacunacion(1L, 18, 99, null, null, null, vacuna);
		ActoVacunal avEditar = new ActoVacunal(1L, null, 1, pvEditar);
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pvEditar);
		Mockito.when(actoVacunalService.actoVacunalDAO.editar(av)).thenReturn(avEditar);
		ActoVacunalDTO avDTO = new ActoVacunalDTO(1L, null, null);
		Mockito.when(actoVacunalService.actoVacunalConverter.fromEntity(avEditar)).thenReturn(avDTO);
		try {
			ActoVacunalDTO avEditado = actoVacunalService.editar(1L,avCDTO);
			assertEquals(avEditado.getId(), avDTO.getId());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void editar_avNull () throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("2021-06-22", 1L, 1L, 1L, 3);
		ActoVacunal av = null;
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		@SuppressWarnings("unused")
		ActoVacunalDTO avEditado = actoVacunalService.editar(1L,avCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_pvNull () throws VacunasUyException {
		ActoVacunalCrearDTO avCDTO = new ActoVacunalCrearDTO("2021-06-22", 1L, 1L, 1L, 3);
		Vacuna vacuna = new Vacuna (1L, null, 1, 1, 1, null);
		PlanVacunacion pv = new PlanVacunacion(1L, 1, 99, null, null, null, vacuna);
		ActoVacunal av = new ActoVacunal(1L, null, 1, pv);
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		PlanVacunacion pvEditar = null;
		Mockito.when(actoVacunalService.planVacunacionDAO.listarPorId(1L)).thenReturn(pvEditar);
		@SuppressWarnings("unused")
		ActoVacunalDTO avEditado = actoVacunalService.editar(1L, avCDTO);
	}
	
	@Test
	public void eliminar() {
		ActoVacunal av = new ActoVacunal (1L, null, 0, null);
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		try {
			actoVacunalService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(actoVacunalService.actoVacunalDAO.listarPorId(1L)).thenReturn(null);
		actoVacunalService.eliminar(1L);
	}
	
}