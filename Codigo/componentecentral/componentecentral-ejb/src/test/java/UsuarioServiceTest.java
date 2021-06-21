import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import vacunasuy.componentecentral.business.UsuarioServiceImpl;
import vacunasuy.componentecentral.converter.AgendaConverter;
import vacunasuy.componentecentral.converter.AtiendeConverter;
import vacunasuy.componentecentral.converter.UsuarioConverter;
import vacunasuy.componentecentral.dao.IActoVacunalDAO;
import vacunasuy.componentecentral.dao.IAgendaDAO;
import vacunasuy.componentecentral.dao.IPuestoDAO;
import vacunasuy.componentecentral.dao.IRolDAO;
import vacunasuy.componentecentral.dao.ISectorLaboralDAO;
import vacunasuy.componentecentral.dao.IUsuarioDAO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.dto.RespuestaUserInfoDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDNICDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.dto.UsuarioRegistrarTFDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Atiende;
import vacunasuy.componentecentral.entity.PlanVacunacion;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.entity.SectorLaboral;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {
	
	/* Servicio que se quiere probar */
	@InjectMocks
	//@Spy
	private UsuarioServiceImpl usuarioService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	public IUsuarioDAO usuarioDAO;
	
	@Mock
	public UsuarioConverter usuarioConverter;
	
	@Mock
	public IRolDAO rolDAO;
	
	@Mock
	public ISectorLaboralDAO sectorLaboralDAO;
	
	@Mock
	public IPuestoDAO puestoDAO;
	
	@Mock
	public AtiendeConverter atiendeConverter;
	
	@Mock
	public IActoVacunalDAO actoVacunalDAO;
	
	@Mock
	public IAgendaDAO agendaDAO;
	
	@Mock
	public AgendaConverter agendaConverter;
	
	@Before
	public void init() {
		usuarioService = Mockito.spy(new UsuarioServiceImpl());
		usuarioService.usuarioDAO = this.usuarioDAO;
		usuarioService.usuarioConverter = this.usuarioConverter;
		usuarioService.rolDAO = this.rolDAO;
		usuarioService.sectorLaboralDAO = this.sectorLaboralDAO;
		usuarioService.puestoDAO = this.puestoDAO;
		usuarioService.atiendeConverter = this.atiendeConverter;
		usuarioService.actoVacunalDAO = this.actoVacunalDAO;
		usuarioService.agendaDAO = this.agendaDAO;
		usuarioService.agendaConverter = this.agendaConverter;
	}
	
	@Test
	public void listar() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario());
		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();
		usuariosDTO.add(new UsuarioDTO());
		Mockito.when(usuarioService.usuarioDAO.listar()).thenReturn(usuarios);
		Mockito.when(usuarioService.usuarioConverter.fromEntity(Mockito.anyList())).thenReturn(usuariosDTO);
		try {
			List<UsuarioDTO> usuariosEsperados = usuarioService.listar();
			assertEquals(1, usuariosEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromEntity(Mockito.any(Usuario.class))).thenReturn(usuarioDTO);
		try {
			UsuarioDTO usuarioEsperado = usuarioService.listarPorId(1L);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorCorreo() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setCorreo("prueba@gmail.com");
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(1L);
		usuarioDTO.setCorreo("prueba@gmail.com");
		Mockito.when(usuarioService.usuarioDAO.listarPorCorreo(Mockito.anyString())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromEntity(Mockito.any(Usuario.class))).thenReturn(usuarioDTO);
		try {
			UsuarioDTO usuarioEsperado = usuarioService.listarPorCorreo("prueba@gmail.com");
			assertEquals(usuario.getCorreo(), usuarioEsperado.getCorreo());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void crear() {
		List<Long> roles = new ArrayList<Long>();
		roles.add(1L);
		UsuarioCrearDTO usuarioCrearDTO = new UsuarioCrearDTO();
		usuarioCrearDTO.setCorreo("prueba@gmail.com");
		usuarioCrearDTO.setRoles(roles);
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setCorreo(null);
		usuario.setPassword("1234");
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(1L);
		Rol rol = new Rol();
		rol.setId(1L);
		Mockito.when(usuarioService.usuarioConverter.fromCrearDTO(Mockito.any(UsuarioCrearDTO.class))).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromEntity(Mockito.any(Usuario.class))).thenReturn(usuarioDTO);
		Mockito.when(usuarioService.rolDAO.listarPorId(Mockito.anyLong())).thenReturn(rol);
		Mockito.when(usuarioService.usuarioDAO.crear(Mockito.any(Usuario.class))).thenReturn(usuario);
		try {
			UsuarioDTO usuarioEsperado = usuarioService.crear(usuarioCrearDTO);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* El usuario no existe */
	public void editar1() {
		try {
			UsuarioDTO usuarioEsperado = usuarioService.editar(1L, null);
			assertEquals(null, usuarioEsperado);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* El correo electronico se encuentra en uso */
	public void editar2() {
		UsuarioCrearDTO usuarioCrearDTO = new UsuarioCrearDTO();
		usuarioCrearDTO.setCorreo("prueba@gmail.com");
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba1@gmail.com");
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioDAO.listarPorCorreo(Mockito.anyString())).thenReturn(usuario);
		try {
			UsuarioDTO usuarioEsperado = usuarioService.editar(1L, usuarioCrearDTO);
			assertEquals(null, usuarioEsperado);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}

	@Test
	/* Editar normal */
	public void editar3() {
		List<Long> roles = new ArrayList<Long>();
		roles.add(1L);
		UsuarioCrearDTO usuarioCrearDTO = new UsuarioCrearDTO();
		usuarioCrearDTO.setCorreo("prueba@gmail.com");
		usuarioCrearDTO.setFechaNacimiento("2020-01-01");
		usuarioCrearDTO.setRoles(roles);
		usuarioCrearDTO.setSectorLaboral(1L);
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba@gmail.com");
		SectorLaboral sector = new SectorLaboral();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setCorreo("prueba@gmail.com");
		Rol rol = new Rol();
		rol.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.sectorLaboralDAO.listarPorId(Mockito.anyLong())).thenReturn(sector);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any(Usuario.class))).thenReturn(usuario);
		Mockito.when(usuarioService.rolDAO.listarPorId(Mockito.anyLong())).thenReturn(rol);
		Mockito.when(usuarioService.usuarioConverter.fromEntity(Mockito.any(Usuario.class))).thenReturn(usuarioDTO);
		try {
			UsuarioDTO usuarioEsperado = usuarioService.editar(1L, usuarioCrearDTO);
			assertEquals(usuario.getCorreo(), usuarioEsperado.getCorreo());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void eliminar() {
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba@gmail.com");
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		try {
			usuarioService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Usuario incorrectos */
	public void loginBackoffice1() {
		UsuarioLoginBackofficeDTO usuario = new UsuarioLoginBackofficeDTO();
		usuario.setCorreo("prueba@gmail.com");
		Mockito.when(usuarioService.usuarioDAO.listarPorCorreo(Mockito.anyString())).thenReturn(null);
		try {
			usuarioService.loginBackoffice(usuario);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Contraseña incorrecta */
	public void loginBackoffice2() {
		UsuarioLoginBackofficeDTO usuarioLoginDTO = new UsuarioLoginBackofficeDTO();
		usuarioLoginDTO.setCorreo("prueba@gmail.com");
		usuarioLoginDTO.setPassword("12345");
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba@gmail.com");
		usuario.setPassword("1234");
		Mockito.when(usuarioService.usuarioDAO.listarPorCorreo(Mockito.anyString())).thenReturn(usuario);
		try {
			usuarioService.loginBackoffice(usuarioLoginDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Login correcto */
	public void loginBackoffice3() {
		UsuarioLoginBackofficeDTO usuarioLoginDTO = new UsuarioLoginBackofficeDTO();
		usuarioLoginDTO.setCorreo("prueba@gmail.com");
		usuarioLoginDTO.setPassword("1234");
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setCorreo("prueba@gmail.com");
		usuario.setPassword("$2a$12$z4dj/MDu1T.tiJSJT.11te3iz9VVHawqjxvDIiGShnIg/E8WIcgUO");
		List<Rol> roles = new ArrayList<Rol>();
		usuario.setRoles(roles);
		UsuarioLoginExitosoDTO usuarioLogin = new UsuarioLoginExitosoDTO();
		usuarioLogin.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorCorreo(Mockito.anyString())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromLogin(Mockito.any(Usuario.class), Mockito.anyString())).thenReturn(usuarioLogin);
		try {
			UsuarioLoginExitosoDTO usuarioEsperado = usuarioService.loginBackoffice(usuarioLoginDTO);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Usuario por primera vez */
	public void loginGubUy1() {
		RespuestaUserInfoDTO usuarioDTO = new RespuestaUserInfoDTO();
		usuarioDTO.setNumero_documento("1234");
		Rol rol = new Rol(4L, "Ciudadano");
		UsuarioDNICDTO datosDNIC = new UsuarioDNICDTO();
		datosDNIC.setFechaDeNacimiento("2020-01-01");
		datosDNIC.setSectorLaboral("Salud");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(new SectorLaboral(1L, "Salud"));
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		List<Rol> roles = new ArrayList<Rol>();
		usuario.setRoles(roles);
		UsuarioLoginExitosoDTO usuarioLogin = new UsuarioLoginExitosoDTO();
		usuarioLogin.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorDocumento(Mockito.anyString())).thenReturn(null);
		Mockito.when(usuarioService.rolDAO.listarPorId(Mockito.anyLong())).thenReturn(rol);
		Mockito.doReturn(datosDNIC).when(usuarioService).getDatosDNIC(Mockito.anyString());
		Mockito.when(usuarioService.sectorLaboralDAO.listar()).thenReturn(sectores);
		Mockito.when(usuarioService.usuarioDAO.crear(Mockito.any(Usuario.class))).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromLogin(Mockito.any(Usuario.class), Mockito.anyString())).thenReturn(usuarioLogin);
		try {
			UsuarioLoginExitosoDTO usuarioEsperado = usuarioService.loginGubUy(usuarioDTO);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Usuario ya existe y tiene rol */
	public void loginGubUy2() {
		RespuestaUserInfoDTO usuarioDTO = new RespuestaUserInfoDTO();
		usuarioDTO.setNumero_documento("1234");
		Rol rol = new Rol(4L, "Ciudadano");
		UsuarioDNICDTO datosDNIC = new UsuarioDNICDTO();
		datosDNIC.setFechaDeNacimiento("2020-01-01");
		datosDNIC.setSectorLaboral("Salud");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(new SectorLaboral(1L, "Salud"));
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(rol);
		usuario.setRoles(roles);
		UsuarioLoginExitosoDTO usuarioLogin = new UsuarioLoginExitosoDTO();
		usuarioLogin.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorDocumento(Mockito.anyString())).thenReturn(usuario);
		Mockito.when(usuarioService.rolDAO.listarPorId(Mockito.anyLong())).thenReturn(rol);
		Mockito.doReturn(datosDNIC).when(usuarioService).getDatosDNIC(Mockito.anyString());
		Mockito.when(usuarioService.sectorLaboralDAO.listar()).thenReturn(sectores);
		Mockito.when(usuarioService.usuarioDAO.crear(Mockito.any(Usuario.class))).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromLogin(Mockito.any(Usuario.class), Mockito.anyString())).thenReturn(usuarioLogin);
		try {
			UsuarioLoginExitosoDTO usuarioEsperado = usuarioService.loginGubUy(usuarioDTO);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	/* Usuario ya existe y no tiene rol */
	public void loginGubUy3() {
		RespuestaUserInfoDTO usuarioDTO = new RespuestaUserInfoDTO();
		usuarioDTO.setNumero_documento("1234");
		Rol rol = new Rol(4L, "Ciudadano");
		UsuarioDNICDTO datosDNIC = new UsuarioDNICDTO();
		datosDNIC.setFechaDeNacimiento("2020-01-01");
		datosDNIC.setSectorLaboral("Salud");
		List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
		sectores.add(new SectorLaboral(1L, "Salud"));
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		UsuarioLoginExitosoDTO usuarioLogin = new UsuarioLoginExitosoDTO();
		usuarioLogin.setId(1L);
		Mockito.when(usuarioService.usuarioDAO.listarPorDocumento(Mockito.anyString())).thenReturn(usuario);
		Mockito.when(usuarioService.rolDAO.listarPorId(Mockito.anyLong())).thenReturn(rol);
		Mockito.doReturn(datosDNIC).when(usuarioService).getDatosDNIC(Mockito.anyString());
		Mockito.doReturn("1234").when(usuarioService).crearJsonWebToken(Mockito.any(Usuario.class));
		Mockito.when(usuarioService.sectorLaboralDAO.listar()).thenReturn(sectores);
		Mockito.when(usuarioService.usuarioDAO.crear(Mockito.any(Usuario.class))).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any(Usuario.class))).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioConverter.fromLogin(Mockito.any(Usuario.class), Mockito.anyString())).thenReturn(usuarioLogin);
		try {
			UsuarioLoginExitosoDTO usuarioEsperado = usuarioService.loginGubUy(usuarioDTO);
			assertEquals(usuario.getId(), usuarioEsperado.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void asignarVacunadorAPuesto() {
		AtiendeCrearDTO atiendeDTO = new AtiendeCrearDTO();
		atiendeDTO.setIdUsuario(1L);
		atiendeDTO.setIdPuesto(1L);
		Usuario usuario = new Usuario();
		usuario.setCorreo("prueba@gmail.com");
		Puesto puesto = new Puesto();
		Atiende atiende = new Atiende();
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.puestoDAO.listarPorId(Mockito.anyLong())).thenReturn(puesto);
		Mockito.when(usuarioService.atiendeConverter.fromCrearDTO(Mockito.any())).thenReturn(atiende);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any())).thenReturn(usuario);
		try {
			usuarioService.asignarVacunadorAPuesto(atiendeDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void agregarActoVacunal() {
		ActoVacunal acto = new ActoVacunal();
		Usuario usuario = new Usuario();
		Mockito.when(usuarioService.actoVacunalDAO.listarPorId(Mockito.anyLong())).thenReturn(acto);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any())).thenReturn(usuario);
		try {
			usuarioService.agregarActoVacunal(1L, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Agenda no asociada */
	public void cancelarAgenda1() {
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(4L, "Ciudadano"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Agenda agenda = new Agenda();
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.agendaDAO.listarPorId(Mockito.anyLong())).thenReturn(agenda);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any())).thenReturn(usuario);
		try {
			usuarioService.cancelarAgenda(1L, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Agenda asociada */
	public void cancelarAgenda2() {
		PlanVacunacion plan = new PlanVacunacion();
		plan.setId(1L);
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(4L, "Ciudadano"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Agenda agenda = new Agenda();
		agenda.setId(1L);
		agenda.setFecha(LocalDateTime.now());
		usuario.getAgendas().add(agenda);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.agendaDAO.listarPorId(Mockito.anyLong())).thenReturn(agenda);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any())).thenReturn(usuario);
		try {
			usuarioService.cancelarAgenda(1L, 1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* No existe */
	public void existeAgenda1() {
		PlanVacunacion plan = new PlanVacunacion();
		plan.setId(1L);
		Agenda agenda = new Agenda();
		agenda.setId(1L);
		agenda.setPlanVacunacion(plan);
		Usuario usuario = new Usuario();
		usuario.getAgendas().add(agenda);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		try {
			assertFalse(usuarioService.existeAgenda(1L, 2L));
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/* Existe */
	public void existeAgenda2() {
		PlanVacunacion plan = new PlanVacunacion();
		plan.setId(1L);
		Agenda agenda = new Agenda();
		agenda.setId(1L);
		agenda.setPlanVacunacion(plan);
		Usuario usuario = new Usuario();
		usuario.getAgendas().add(agenda);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		try {
			assertTrue(usuarioService.existeAgenda(1L, 1L));
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void registrarTokenFirebase() {
		UsuarioRegistrarTFDTO usuarioDTO = new UsuarioRegistrarTFDTO();
		usuarioDTO.setId(1L);
		Usuario usuario = new Usuario();
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.usuarioDAO.editar(Mockito.any())).thenReturn(usuario);
		try {
			usuarioService.registrarTokenFirebase(usuarioDTO);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarAgendasCiudadano() {
		List<AgendaDTO> agendas = new ArrayList<AgendaDTO>();
		agendas.add(new AgendaDTO());
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(4L, "Ciudadano"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.agendaConverter.fromEntity(Mockito.anyList())).thenReturn(agendas);
		try {
			List<AgendaDTO> agendasEsperadas= usuarioService.listarAgendasCiudadano(1L);
			assertEquals(1, agendasEsperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarAgendasCiudadano2() {
		List<AgendaDTO> agendas = new ArrayList<AgendaDTO>();
		agendas.add(new AgendaDTO());
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(4L, "Ciudadano"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.agendaConverter.fromEntity(Mockito.anyList())).thenReturn(agendas);
		try {
			List<AgendaDTO> agendasEsperadas= usuarioService.listarAgendasCiudadano(null);
			assertEquals(1, agendasEsperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarAtiendeVacunador() {
		List<AtiendeDTO> atiendes = new ArrayList<AtiendeDTO>();
		atiendes.add(new AtiendeDTO());
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(3L, "Vacunador"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.atiendeConverter.fromEntity(Mockito.anyList())).thenReturn(atiendes);
		try {
			List<AtiendeDTO> atiendesEsperadas= usuarioService.listarAtiendeVacunador(1L);
			assertEquals(1, atiendesEsperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void listarAtiendeVacunador2() {
		List<AtiendeDTO> atiendes = new ArrayList<AtiendeDTO>();
		atiendes.add(new AtiendeDTO());
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(3L, "Vacunador"));
		Usuario usuario = new Usuario();
		usuario.setRoles(roles);
		Mockito.when(usuarioService.usuarioDAO.listarPorId(Mockito.anyLong())).thenReturn(usuario);
		Mockito.when(usuarioService.atiendeConverter.fromEntity(Mockito.anyList())).thenReturn(atiendes);
		try {
			List<AtiendeDTO> atiendesEsperadas= usuarioService.listarAtiendeVacunador(null);
			assertEquals(1, atiendesEsperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}

}
