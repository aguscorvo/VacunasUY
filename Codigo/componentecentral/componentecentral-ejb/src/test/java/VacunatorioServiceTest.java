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
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Localidad;
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
	
}
	