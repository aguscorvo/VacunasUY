import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import vacunasuy.componentecentral.business.StockServiceImpl;
import vacunasuy.componentecentral.business.VacunatorioGeoServiceImpl;
import vacunasuy.componentecentral.business.VacunatorioServiceImpl;
import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.converter.UbicacionConverter;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.converter.VacunatorioConverter;
import vacunasuy.componentecentral.dao.ActoVacunalDAOImpl;
import vacunasuy.componentecentral.dao.DepartamentoDAOImpl;
import vacunasuy.componentecentral.dao.EventoDAOImpl;
import vacunasuy.componentecentral.dao.LocalidadDAOImpl;
import vacunasuy.componentecentral.dao.PlanVacunacionDAOImpl;
import vacunasuy.componentecentral.dao.VacunatorioDAOImpl;
import vacunasuy.componentecentral.dto.AgendaVacunatorioDTO;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.UsuarioMinDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Atiende;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class VacunatorioServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private VacunatorioServiceImpl vacunatorioService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	
	@Mock
	private VacunatorioDAOImpl vacunatorioDAO;
	
	@Mock
	private PlanVacunacionDAOImpl planVacunacionDAO;
	
	@Mock
	private LocalidadDAOImpl localidadDAO;
	
	@Mock
	private DepartamentoDAOImpl departamentoDAO;
	
	@Mock
	private EventoDAOImpl eventoDAO;
	
	@Mock
	private ActoVacunalDAOImpl actoVacunalDAO;
	
	@Mock
	private VacunatorioGeoServiceImpl vacunatorioGeoService;
	
	@Mock
	private StockServiceImpl stockService;
	
	@Mock
	private VacunatorioConverter vacunatorioConverter;
	
	@Mock
	private UsuarioConverter usuarioConverter;
	
	@Mock
	private UbicacionConverter ubicacionConverter;
	
	@Mock
	private AgendaConverter agendaConverter;
	
	
	@Before
	public void init() {
		vacunatorioService = new VacunatorioServiceImpl();
		vacunatorioService.vacunatorioGeoService = this.vacunatorioGeoService;
		vacunatorioService.stockService = this.stockService;
		vacunatorioService.vacunatorioDAO = this.vacunatorioDAO;
		vacunatorioService.localidadDAO = this.localidadDAO;
		vacunatorioService.departamentoDAO = this.departamentoDAO;
		vacunatorioService.planDAO = this.planVacunacionDAO;
		vacunatorioService.eventoDAO = this.eventoDAO;
		vacunatorioService.actoVacunalDAO = this.actoVacunalDAO;
		vacunatorioService.vacunatorioConverter = this.vacunatorioConverter;
		vacunatorioService.usuarioConverter = this.usuarioConverter;
		vacunatorioService.ubicacionConverter = this.ubicacionConverter;
		vacunatorioService.agendaConverter = this.agendaConverter;
	}
	
	@Test
	public void listar() {
		List<Vacunatorio> vacunatorios = new ArrayList<Vacunatorio>();
		vacunatorios.add(new Vacunatorio(1L, "Prueba", null, null, null, null, null, null, null, null, null));
		List<VacunatorioDTO> vacunatoriosDTO = new ArrayList<VacunatorioDTO>();
		vacunatoriosDTO.add(new VacunatorioDTO(1L, "Prueba", null, null, null, null, null, null, null));
		Mockito.when(vacunatorioService.vacunatorioDAO.listar()).thenReturn(vacunatorios);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacunatorios)).thenReturn(vacunatoriosDTO);
		try {
			List<VacunatorioDTO> vacesperados = vacunatorioService.listar();
			assertEquals(1, vacesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(v)).thenReturn(vDTO);
		try {
			VacunatorioDTO vf = vacunatorioService.listarPorId(1L);
			assertEquals(v.getId(), vf.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_vacunatorioNull() throws VacunasUyException {
		Vacunatorio v = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		VacunatorioDTO vf = vacunatorioService.listarPorId(1L);
	}
	
	//Comentar linea 118 de VacunatorioServiceImpl para realizar este test de manera completa, descomentar fragmento comentado y quitar expected
	@Test(expected = VacunasUyException.class)
	public void crear() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombre", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromCrearDTO(vCDTO)).thenReturn(v);
		List<Localidad> localidades = new ArrayList<Localidad>();
		Localidad localidad = new Localidad(1L, "nombre");
		localidades.add(localidad);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Departamento depto = new Departamento(1L, "nombre", localidades);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		Mockito.when(vacunatorioService.vacunatorioDAO.crear(v)).thenReturn(v);
		@SuppressWarnings("unused")
		VacunatorioDTO vCreado = vacunatorioService.crear(vCDTO);
		/*VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(v)).thenReturn(vDTO);
		VacunatorioGeoDTO vGDTO= new VacunatorioGeoDTO(1L, vDTO);
		try {
			Mockito.when(vacunatorioService.vacunatorioGeoService.crear(vDTO)).thenReturn(vGDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		try {
			VacunatorioDTO vCreado = vacunatorioService.crear(vCDTO);
			assertEquals(vCreado.getId(), vDTO.getId());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}*/
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_localidadNull() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombre", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromCrearDTO(vCDTO)).thenReturn(v);
		List<Localidad> localidades = new ArrayList<Localidad>();
		Localidad localidad = null;
		localidades.add(localidad);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		@SuppressWarnings("unused")
		VacunatorioDTO vCreado = vacunatorioService.crear(vCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_departamentoNull() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombre", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromCrearDTO(vCDTO)).thenReturn(v);
		List<Localidad> localidades = new ArrayList<Localidad>();
		Localidad localidad = new Localidad(1L, "nombre");
		localidades.add(localidad);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Departamento depto = null;
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		@SuppressWarnings("unused")
		VacunatorioDTO vCreado = vacunatorioService.crear(vCDTO);
	}
	
	@Test
	public void editar() {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombreEdit", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		List<Localidad> localidades = new ArrayList<Localidad>();
		Localidad localidad = new Localidad(1L, "nombre");
		localidades.add(localidad);
		Departamento depto = new Departamento(1L, "nombre", localidades);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		Vacunatorio v_editado = new Vacunatorio(1L, "nombreEdit", 1D, 1D, "direccion", null, localidad, depto, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.editar(v)).thenReturn(v_editado);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, "nombreEdit", 1D, 1D, "direccion", null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(v_editado)).thenReturn(vDTO);
		try {
			VacunatorioDTO vEditado = vacunatorioService.editar(1L, vCDTO);
			assertEquals(vEditado.getNombre(), vDTO.getNombre());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_vacunatorioNull() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombreEdit", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.editar(1L, vCDTO);
	}		

	@Test(expected = VacunasUyException.class)
	public void editar_localidadNull() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombreEdit", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Localidad localidad = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.editar(1L, vCDTO);	
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_departamentoNull() throws VacunasUyException {
		VacunatorioCrearDTO vCDTO = new VacunatorioCrearDTO("nombreEdit", 1D, 1D, "direccion", 1L, 1L);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Localidad localidad = new Localidad(1L, "nombre");
		Departamento depto = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.editar(1L, vCDTO);	
	}
	
	@Test
	public void eliminar() {
		Vacunatorio v = new Vacunatorio (1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		try {
			vacunatorioService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(null);
		vacunatorioService.eliminar(1L);
	}

	@Test 
	public void listarCercanos() {
		UbicacionDTO ubicacionDTO = new UbicacionDTO(1D, 1D, 1D);
		List<Long> vacunatoriosId = new ArrayList<Long>();
		vacunatoriosId.add(1L);
		try {
			Mockito.when(vacunatorioService.vacunatorioGeoService.listarCercanos(ubicacionDTO)).thenReturn(vacunatoriosId);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		List<Vacunatorio> vacs = new ArrayList<Vacunatorio>();
		vacs.add(v);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		VacunatorioDTO vacDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		List<VacunatorioDTO> vacsDTO = new ArrayList<VacunatorioDTO>();
		vacsDTO.add(vacDTO);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacs)).thenReturn(vacsDTO);
		try {
			List<VacunatorioDTO> vacsesperados = vacunatorioService.listarCercanos(ubicacionDTO);
			assertEquals(vacsesperados.size(), 1);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void agregarEvento() {
		List<Evento> eventos = new ArrayList<Evento>();
		Evento evento = new Evento (2L, null, null, null, null, null, null, null);
		eventos.add(evento);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, eventos, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Evento eventoAgregar = new Evento (1L, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.eventoDAO.listarPorId(1L)).thenReturn(eventoAgregar);
		Mockito.when(vacunatorioService.vacunatorioDAO.editar(v)).thenReturn(v);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(v)).thenReturn(vDTO);
		try {
			VacunatorioDTO vEditado = vacunatorioService.agregarEvento(1L, 1L);
			assertEquals(vEditado.getNombre(), vDTO.getNombre());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarEvento_vacunatorioNull() throws VacunasUyException {
		Vacunatorio v = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarEvento(1L, 1L);	
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarEvento_eventoNull() throws VacunasUyException {
		List<Evento> eventos = new ArrayList<Evento>();
		Evento evento = new Evento (2L, null, null, null, null, null, null, null);
		eventos.add(evento);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, eventos, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Evento eventoAgregar = null;
		Mockito.when(vacunatorioService.eventoDAO.listarPorId(1L)).thenReturn(eventoAgregar);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarEvento(1L, 1L);
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarEvento_eventoAgregado() throws VacunasUyException {
		List<Evento> eventos = new ArrayList<Evento>();
		Evento evento = new Evento (1L, null, null, null, null, null, null, null);
		eventos.add(evento);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, eventos, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Evento eventoAgregar = new Evento (1L, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.eventoDAO.listarPorId(1L)).thenReturn(eventoAgregar);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarEvento(1L, 1L);
	}
	
	@Test
	public void agregarActoVacunal () {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (2L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		ActoVacunal av = new ActoVacunal(1L, null, 1, null);
		Mockito.when(vacunatorioService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		Mockito.when(vacunatorioService.vacunatorioDAO.editar(v)).thenReturn(v);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(v)).thenReturn(vDTO);
		try {
			VacunatorioDTO vEditado = vacunatorioService.agregarActoVacunal(1L, 1L);
			assertEquals(vEditado.getNombre(), vDTO.getNombre());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarActoVacunal_vacunatorioNull() throws VacunasUyException {
		Vacunatorio v = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarActoVacunal(1L, 1L);	
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarActoVacunal_actoNull () throws VacunasUyException {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (2L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		ActoVacunal av = null;
		Mockito.when(vacunatorioService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarActoVacunal(1L, 1L);
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void agregarActoVacunal_repetido () throws VacunasUyException {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (1L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		ActoVacunal av = new ActoVacunal(1L, null, 1, null);
		Mockito.when(vacunatorioService.actoVacunalDAO.listarPorId(1L)).thenReturn(av);
		@SuppressWarnings("unused")
		VacunatorioDTO vEditado = vacunatorioService.agregarActoVacunal(1L, 1L);
			
	}
	
	@Test
	public void obtenerAsignacionVacunadores () {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (2L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Usuario u = new Usuario(1L, null, null, null, null, null, null, null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2021-06-01", formato);
		Atiende a = new Atiende(u, null, fecha);
		List<Atiende> atiendes = new ArrayList<Atiende>();
		atiendes.add(a);
		Puesto p = new Puesto(1L, 1, v, null, atiendes);
		a.setPuesto(p);
		List<Puesto> puestos = new ArrayList<Puesto>();
		puestos.add(p);
		v.setPuestos(puestos);
		UsuarioMinDTO min = new UsuarioMinDTO(1L, null, null, null, null, null);
		Mockito.when(vacunatorioService.usuarioConverter.fromEntityToMin(u)).thenReturn(min);
		try {
			List<UsuarioMinDTO> mins = vacunatorioService.obtenerAsignacionVacunadores(1L, "clave", "2021-06-01");
			assertEquals(1, mins.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void obtenerAsignacionVacunadores_vacunatorioNull () throws VacunasUyException {
		Vacunatorio v = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		List<UsuarioMinDTO> mins = vacunatorioService.obtenerAsignacionVacunadores(1L, "clave", "2021-06-01");
	}
	
	@Test(expected = VacunasUyException.class)
	public void obtenerAsignacionVacunadores_claveDiferente () throws VacunasUyException {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (2L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		@SuppressWarnings("unused")
		List<UsuarioMinDTO> mins = vacunatorioService.obtenerAsignacionVacunadores(1L, "claveDiferente", "2021-06-01");
		
	}
	
	@Test
	public void obtenerAsignacionVacunadores_fechaDiferente () {
		List<ActoVacunal> actos = new ArrayList<ActoVacunal>();
		ActoVacunal acto = new ActoVacunal (2L, null, 1, null);
		actos.add(acto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, actos);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(v);
		Usuario u = new Usuario(1L, null, null, null, null, null, null, null, null, null, null, null, null);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fecha = LocalDate.parse("2021-06-02", formato);
		Atiende a = new Atiende(u, null, fecha);
		List<Atiende> atiendes = new ArrayList<Atiende>();
		atiendes.add(a);
		Puesto p = new Puesto(1L, 1, v, null, atiendes);
		a.setPuesto(p);
		List<Puesto> puestos = new ArrayList<Puesto>();
		puestos.add(p);
		v.setPuestos(puestos);
		try {
			List<UsuarioMinDTO> mins = vacunatorioService.obtenerAsignacionVacunadores(1L, "clave", "2021-06-01");
			assertEquals(0, mins.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorUbicacion () {
		Localidad localidad = new Localidad(1L, "nombre");
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		List<Localidad> localidades = new ArrayList<Localidad>();
		localidades.add(localidad);
		Departamento depto = new Departamento(1L, "nombre", localidades);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, null);
		List<Vacunatorio> vacs = new ArrayList<Vacunatorio>();
		vacs.add(v);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorUbicacion(1L, 1L)).thenReturn(vacs);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		List<VacunatorioDTO> vacsDTO = new ArrayList<VacunatorioDTO>();
		vacsDTO.add(vDTO);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacs)).thenReturn(vacsDTO);
		try {
			List<VacunatorioDTO> devueltos = vacunatorioService.listarPorUbicacion(1L, 1L);
			assertEquals(1, devueltos.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorUbicacion_localidadNull () throws VacunasUyException {
		Localidad localidad = null;
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		@SuppressWarnings("unused")
		List<VacunatorioDTO> devueltos = vacunatorioService.listarPorUbicacion(1L, 1L);	
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorUbicacion_departamentoNull () throws VacunasUyException {
		Localidad localidad = new Localidad(1L, "nombre");
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Departamento depto = null;
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		@SuppressWarnings("unused")
		List<VacunatorioDTO> devueltos = vacunatorioService.listarPorUbicacion(1L, 1L);
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorUbicacion_ErrorLocalidadDepartamento () throws VacunasUyException {
		Localidad localidad1 = new Localidad(1L, "nombre1");
		Localidad localidad2 = new Localidad(2L, "nombre2");
		Mockito.when(vacunatorioService.localidadDAO.listarPorId(1L)).thenReturn(localidad1);
		List<Localidad> localidades = new ArrayList<Localidad>();
		localidades.add(localidad2);
		Departamento depto = new Departamento(1L, "nombre", localidades);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		@SuppressWarnings("unused")
		List<VacunatorioDTO> devueltos = vacunatorioService.listarPorUbicacion(1L, 1L);
	}
	
	@Test
	public void listarPorDepartamento () {
		Departamento depto = new Departamento(1L, "nombre", null);
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		Vacunatorio v = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, null);
		List<Vacunatorio> vacs = new ArrayList<Vacunatorio>();
		vacs.add(v);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorDepartamento(1L)).thenReturn(vacs);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		List<VacunatorioDTO> vacsDTO = new ArrayList<VacunatorioDTO>();
		vacsDTO.add(vDTO);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacs)).thenReturn(vacsDTO);
		try {
			List<VacunatorioDTO> devueltos = vacunatorioService.listarPorDepartamento(1L);
			assertEquals(1, devueltos.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorDepartamento_null () throws VacunasUyException {
		Departamento depto = null;
		Mockito.when(vacunatorioService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		@SuppressWarnings("unused")
		List<VacunatorioDTO> devueltos = vacunatorioService.listarPorDepartamento(1L);
	}
	
	@Test
	public void listarVacunatoriosDadoPlan () {
		Vacuna v = new Vacuna (1L, null, 1, 1, 1, null);
		Vacuna v2 = new Vacuna (2L, null, 1, 1, 1, null);
		Lote l = new Lote (1L, 1L, 1L, null, v);
		Lote l2 = new Lote (2L, 1L, 1L, null, v2);
		Evento e1 = new Evento(1L, null, null, 1L, null, l, null, null);
		Evento e2 = new Evento(2L, null, null, 1L, null, l2, null, null);
		List<Evento> eventos = new ArrayList<Evento>();
		eventos.add(e1);
		eventos.add(e2);
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 1, null, null, null, v);
		Mockito.when(vacunatorioService.planDAO.listarPorId(1L)).thenReturn(plan);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, eventos, null);
		List<Vacunatorio> vacs = new ArrayList<Vacunatorio>();
		vacs.add(vacunatorio);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarVacunatoriosDadoVacuna(1L)).thenReturn(vacs);
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		List<VacunatorioDTO> vacsDTO = new ArrayList<VacunatorioDTO>();
		vacsDTO.add(vDTO);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacs)).thenReturn(vacsDTO);
		try {
			List<VacunatorioDTO> devueltos = vacunatorioService.listarVacunatoriosDadoPlan(1L);
			assertEquals(1, devueltos.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarVacunatoriosDadoPlan_Null () throws VacunasUyException {
		PlanVacunacion plan = null;
		Mockito.when(vacunatorioService.planDAO.listarPorId(1L)).thenReturn(plan);
		@SuppressWarnings("unused")
		List<VacunatorioDTO> devueltos = vacunatorioService.listarVacunatoriosDadoPlan(1L);
	}
	
	
	@Test
	public void crearGeometrias() {
		VacunatorioDTO vDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, null);
		List<Vacunatorio> vacs = new ArrayList<Vacunatorio>();
		vacs.add(vacunatorio);
		Mockito.when(vacunatorioService.vacunatorioDAO.listar()).thenReturn(vacs);
		Mockito.when(vacunatorioService.vacunatorioConverter.fromEntity(vacunatorio)).thenReturn(vDTO);
		try {
			vacunatorioService.crearGeometrias();
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarAgendasPorVacunatorio () {
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime fecha = LocalDateTime.parse("2021-06-02 00:00", formato);
		Agenda a = new Agenda(1L, fecha, 1, null, null, null);
		List<Agenda> agendas = new ArrayList<Agenda>();
		agendas.add(a);
		Puesto p = new Puesto (1L, 1, vacunatorio, agendas, null);
		List<Puesto> puestos = new ArrayList<Puesto>();
		puestos.add(p);
		vacunatorio.setPuestos(puestos);
		AgendaVacunatorioDTO avDTO = new AgendaVacunatorioDTO(1L, null, null, null, null, null, 0);	
		Mockito.when(vacunatorioService.agendaConverter.fromEntityToAgendaVacunatorio(a)).thenReturn(avDTO);
		try {
			List<AgendaVacunatorioDTO> agendasEsperadas = vacunatorioService.listarAgendasPorVacunatorio(1L, "2021-06-02T00:00");
			assertEquals(1, agendasEsperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarAgendasPorVacunatorio_Null () throws VacunasUyException {
		Vacunatorio vacunatorio = null;
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		@SuppressWarnings("unused")
		List<AgendaVacunatorioDTO> agendasEsperadas = vacunatorioService.listarAgendasPorVacunatorio(1L, "2021-06-02T00:00");
	}
	
	@Test
	public void vacunatorioTienePlan () {
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, "clave", null, null, null, null, null);
		Mockito.when(vacunatorioService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		Vacuna v = new Vacuna (1L, null, 1, 1, 1, null);
		Vacuna v2 = new Vacuna (2L, null, 1, 1, 1, null);
		Lote l = new Lote (1L, 1L, 1L, null, v);
		Lote l2 = new Lote (2L, 1L, 1L, null, v2);
		Evento e1 = new Evento(1L, null, null, 1L, null, l, null, null);
		Evento e2 = new Evento(2L, null, null, 1L, null, l2, null, null);
		List<Evento> eventos = new ArrayList<Evento>();
		eventos.add(e1);
		eventos.add(e2);
		vacunatorio.setEventos(eventos);
		PlanVacunacion plan = new PlanVacunacion(1L, 1, 1, null, null, null, v);
		Mockito.when(vacunatorioService.planDAO.listarPorId(1L)).thenReturn(plan);
		try {
			boolean tiene = vacunatorioService.vacunatorioTienePlan(1L, 1L);
			assertTrue(tiene);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
	