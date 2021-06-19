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

import vacunasuy.componentecentral.business.LocalidadServiceImpl;
import vacunasuy.componentecentral.converter.LocalidadConverter;
import vacunasuy.componentecentral.dao.LocalidadDAOImpl;
import vacunasuy.componentecentral.dto.LocalidadCrearDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class LocalidadServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private LocalidadServiceImpl localidadService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private LocalidadDAOImpl localidadDAO;
	
	@Mock
	private LocalidadConverter localidadConverter;
	
	@Before
	public void init() {
		localidadService = new LocalidadServiceImpl();
		localidadService.localidadDAO = this.localidadDAO;
		localidadService.localidadConverter = this.localidadConverter;
	}
	
	@Test
	public void listar () {
		List<Localidad> locs = new ArrayList<Localidad>();
		locs.add(new Localidad(1L, "Prueba"));
		List<LocalidadDTO> locsDTO = new ArrayList<LocalidadDTO>();
		locsDTO.add(new LocalidadDTO(1L, "Prueba"));
		Mockito.when(localidadService.localidadDAO.listar()).thenReturn(locs);
		Mockito.when(localidadService.localidadConverter.fromEntity(locs)).thenReturn(locsDTO);
		try {
			List<LocalidadDTO> locsEsperados = localidadService.listar();
			assertEquals(1, locsEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId () {
		Localidad localidad = new Localidad(1L, "Prueba");
		LocalidadDTO locDTO = new LocalidadDTO(1L, "Prueba");
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Mockito.when(localidadService.localidadConverter.fromEntity(localidad)).thenReturn(locDTO);
		try {
			LocalidadDTO loc = localidadService.listarPorId(1L);
			assertEquals(locDTO.getId(), loc.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Localidad localidad = null;
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		@SuppressWarnings("unused")
		LocalidadDTO loc = localidadService.listarPorId(1L);	
	}
	
	@Test
	public void crear () {
		Localidad localidad = new Localidad (1L, "Prueba");
		LocalidadCrearDTO locCDTO = new LocalidadCrearDTO ("Prueba");
		LocalidadDTO locDTO = new LocalidadDTO(1L, "Creada");
		Mockito.when(localidadService.localidadConverter.fromCrearDTO(locCDTO)).thenReturn(localidad);
		Mockito.when(localidadService.localidadDAO.crear(localidad)).thenReturn(localidad);
		Mockito.when(localidadService.localidadConverter.fromEntity(localidad)).thenReturn(locDTO);
		try {
			LocalidadDTO loc = localidadService.crear(locCDTO);
			assertEquals(loc.getNombre(), "Creada");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void editar () {
		LocalidadCrearDTO locCDTO = new LocalidadCrearDTO ("Prueba");
		Localidad localidad = new Localidad (1L, "Prueba");
		Localidad localidad_editada = new Localidad (1L, "Editada");
		LocalidadDTO locDTO = new LocalidadDTO(1L, "Editada");
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		Mockito.when(localidadService.localidadDAO.editar(localidad)).thenReturn(localidad_editada);
		Mockito.when(localidadService.localidadConverter.fromEntity(localidad_editada)).thenReturn(locDTO);
		try {
			LocalidadDTO loc = localidadService.editar(1L, locCDTO);
			assertEquals(loc.getNombre(), "Editada");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_null () throws VacunasUyException {
		Localidad localidad = null;
		LocalidadCrearDTO locCDTO = new LocalidadCrearDTO ("Prueba");
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		@SuppressWarnings("unused")
		LocalidadDTO loc = localidadService.editar(1L, locCDTO);
	}
	
	@Test
	public void eliminar () {
		Localidad localidad = new Localidad (1L, "Prueba");
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		try {
			localidadService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null () throws VacunasUyException {
		Localidad localidad = null;
		Mockito.when(localidadService.localidadDAO.listarPorId(1L)).thenReturn(localidad);
		localidadService.eliminar(1L);
	}
}



