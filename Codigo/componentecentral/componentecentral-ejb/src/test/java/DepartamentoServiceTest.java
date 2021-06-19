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

import vacunasuy.componentecentral.business.DepartamentoServiceImpl;
import vacunasuy.componentecentral.converter.DepartamentoConverter;
import vacunasuy.componentecentral.dao.DepartamentoDAOImpl;
import vacunasuy.componentecentral.dao.LocalidadDAOImpl;
import vacunasuy.componentecentral.dto.DepartamentoCrearDTO;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class DepartamentoServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private DepartamentoServiceImpl departamentoService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private DepartamentoDAOImpl departamentoDAO;
	
	@Mock
	private LocalidadDAOImpl localidadDAO;
	
	@Mock
	private DepartamentoConverter departamentoConverter;
	
	@Before
	public void init() {
		departamentoService = new DepartamentoServiceImpl();
		departamentoService.departamentoDAO = this.departamentoDAO;
		departamentoService.localidadDAO = this.localidadDAO;
		departamentoService.departamentoConverter = this.departamentoConverter;
		
	}
	
	@Test
	public void listar () {
		List<Departamento> deps = new ArrayList<Departamento>();
		deps.add(new Departamento(1L, "Prueba", null));
		List<DepartamentoDTO> depsDTO = new ArrayList<DepartamentoDTO>();
		depsDTO.add(new DepartamentoDTO(1L, "Prueba", null));
		Mockito.when(departamentoService.departamentoDAO.listar()).thenReturn(deps);
		Mockito.when(departamentoService.departamentoConverter.fromEntity(deps)).thenReturn(depsDTO);
		try {
			List<DepartamentoDTO> depsEsperados = departamentoService.listar();
			assertEquals(1, depsEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId () {
		Departamento depto= new Departamento(1L, "Prueba", null);
		DepartamentoDTO deptoDTO = new DepartamentoDTO(1L, "Prueba", null);
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		Mockito.when(departamentoService.departamentoConverter.fromEntity(depto)).thenReturn(deptoDTO);
		try {
			DepartamentoDTO dep = departamentoService.listarPorId(1L);
			assertEquals(deptoDTO.getId(), dep.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Departamento departamento = null;
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(departamento);
		@SuppressWarnings("unused")
		DepartamentoDTO dep = departamentoService.listarPorId(1L);	
	}
	
	@Test
	public void crear () {
		Localidad localidad = new Localidad (1L, "localidad");
		List<Localidad>  localidades = new ArrayList<Localidad>();
		localidades.add(localidad);
		List<LocalidadDTO>  localidadesDTO = new ArrayList<LocalidadDTO>();
		localidadesDTO.add(new LocalidadDTO (1L, "localidad"));
		List<Long> longs= new ArrayList<Long>();
		longs.add(1L);
		Departamento departamento= new Departamento (1L, "Prueba", localidades);
		DepartamentoCrearDTO deptoCDTO = new DepartamentoCrearDTO ("Prueba", longs);
		DepartamentoDTO deptoDTO = new DepartamentoDTO(1L, "Creado", localidadesDTO);
		Mockito.when(departamentoService.departamentoConverter.fromCrearDTO(deptoCDTO)).thenReturn(departamento);
		Mockito.when(departamentoService.departamentoDAO.crear(departamento)).thenReturn(departamento);
		Mockito.when(departamentoService.departamentoConverter.fromEntity(departamento)).thenReturn(deptoDTO);
		Mockito.when(departamentoService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		try {
			DepartamentoDTO dep = departamentoService.crear(deptoCDTO);
			assertEquals(dep.getNombre(), "Creado");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_dep_locNull () throws VacunasUyException {
		List<Long> longs= new ArrayList<Long>();
		longs.add(1L);
		DepartamentoCrearDTO deptoCDTO = new DepartamentoCrearDTO ("Prueba", longs);
		Mockito.when(departamentoService.localidadDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		DepartamentoDTO dep = departamentoService.crear(deptoCDTO);
	}
	
	@Test
	public void editar () {
		Localidad localidad = new Localidad (1L, "localidad");
		List<Localidad>  localidades = new ArrayList<Localidad>();
		localidades.add(localidad);
		List<LocalidadDTO>  localidadesDTO = new ArrayList<LocalidadDTO>();
		localidadesDTO.add(new LocalidadDTO (1L, "localidad"));
		List<Long> longs= new ArrayList<Long>();
		longs.add(1L);
		Departamento departamento= new Departamento (1L, "Prueba", localidades);
		Departamento departamento_edit= new Departamento (1L, "Editado", localidades);
		DepartamentoCrearDTO deptoCDTO = new DepartamentoCrearDTO ("Prueba", longs);
		DepartamentoDTO deptoDTO = new DepartamentoDTO(1L, "Editado", localidadesDTO);
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(departamento);
		Mockito.when(departamentoService.departamentoDAO.editar(departamento)).thenReturn(departamento_edit);
		Mockito.when(departamentoService.departamentoConverter.fromEntity(departamento_edit)).thenReturn(deptoDTO);
		Mockito.when(departamentoService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		try {
			DepartamentoDTO dep = departamentoService.editar(1L, deptoCDTO);
			assertEquals(dep.getNombre(), "Editado");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_null () throws VacunasUyException {
		Departamento departamento = null;
		DepartamentoCrearDTO deptoCDTO = new DepartamentoCrearDTO ("Prueba", null);
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(departamento);
		@SuppressWarnings("unused")
		DepartamentoDTO dep = departamentoService.editar(1L, deptoCDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_locNull () throws VacunasUyException {
		Departamento departamento = new Departamento (1L, "Departamento", null);
		List<Long> longs= new ArrayList<Long>();
		longs.add(1L);
		DepartamentoCrearDTO deptoCDTO = new DepartamentoCrearDTO ("DeptoDTO", longs);
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(departamento);
		Mockito.when(departamentoService.localidadDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		DepartamentoDTO dep = departamentoService.editar(1L, deptoCDTO);
	}
	
	@Test
	public void eliminar () {
		Departamento depto= new Departamento (1L, "Prueba", null);
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(depto);
		try {
			departamentoService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null () throws VacunasUyException {
		Departamento departamento = null;
		Mockito.when(departamentoService.departamentoDAO.listarPorId(1L)).thenReturn(departamento);
		departamentoService.eliminar(1L);
	}
	
}