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

import vacunasuy.componentecentral.business.EnfermedadServiceImpl;
import vacunasuy.componentecentral.converter.EnfermedadConverter;
import vacunasuy.componentecentral.dao.EnfermedadDAOImpl;
import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class EnfermedadServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private EnfermedadServiceImpl enfermedadService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private EnfermedadDAOImpl enfermedadDAO;
	
	@Mock
	private EnfermedadConverter enfermedadConverter;
	
	@Before
	public void init() {
		enfermedadService = new EnfermedadServiceImpl();
		enfermedadService.enfermedadDAO = this.enfermedadDAO;
		enfermedadService.eConverter = this.enfermedadConverter;
	}
	
	@Test
	public void listar() {
		List<Enfermedad> enfermedades = new ArrayList<Enfermedad>();
		enfermedades.add(new Enfermedad(1L, "Prueba"));
		List<EnfermedadDTO> enfermedadesDTO = new ArrayList<EnfermedadDTO>();
		enfermedadesDTO.add(new EnfermedadDTO(1L, "Prueba"));
		Mockito.when(enfermedadService.enfermedadDAO.listar()).thenReturn(enfermedades);
		Mockito.when(enfermedadService.eConverter.fromEntity(Mockito.anyList())).thenReturn(enfermedadesDTO);
		try {
			List<EnfermedadDTO> enfesperadas = enfermedadService.listar();
			assertEquals(1, enfesperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void listarPorId() {
		Enfermedad enfermedad = new Enfermedad(1L, "Prueba");
		Mockito.when(enfermedadService.enfermedadDAO.listarPorId(1L)).thenReturn(enfermedad);
		Enfermedad e = enfermedadService.listarPorId(1L);
		assertEquals(enfermedad.getId(), e.getId());

	}
	
	@Test
	public void listarEnfermedadesPorUsuario() {
		List<EnfermedadDTO> enfermedadesDTO = new ArrayList<EnfermedadDTO>();
		enfermedadesDTO.add(new EnfermedadDTO(1L, "Prueba"));
		Mockito.when(enfermedadService.eConverter.fromEntity(enfermedadService.enfermedadDAO.listarEnfermedadesPorUsuario(Mockito.anyLong()))).thenReturn(enfermedadesDTO);
		try {
			List<EnfermedadDTO> enfesperadas = enfermedadService.listarEnfermedadesPorUsuario(Mockito.anyLong());
			assertEquals(1, enfesperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void crear() {
		EnfermedadDTO eDTO_creada = new EnfermedadDTO(1L, "nueva");
		Mockito.when(enfermedadService.eConverter.fromEntity(enfermedadService.enfermedadDAO.crear(Mockito.any()))).thenReturn(eDTO_creada);
		try {
			EnfermedadDTO enfermedad = enfermedadService.crear(new EnfermedadCrearDTO("nueva"));
			assertEquals(enfermedad.getNombre(), "nueva");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void editar() {
		EnfermedadCrearDTO enfCDTO = new EnfermedadCrearDTO ("Editada");
		Enfermedad enfermedad = new Enfermedad(1L, "Prueba");
		Enfermedad enfermedad_edit = new Enfermedad(1L, "Editada");
		EnfermedadDTO enfermedadDTO = new EnfermedadDTO(1L, "Editada");
		Mockito.when(enfermedadService.enfermedadDAO.listarPorId(1L)).thenReturn(enfermedad);
		Mockito.when(enfermedadService.enfermedadDAO.editar(enfermedad)).thenReturn(enfermedad_edit);
		Mockito.when(enfermedadService.eConverter.fromEntity(enfermedad_edit)).thenReturn(enfermedadDTO);
		try {
			EnfermedadDTO e = enfermedadService.editar(1L, enfCDTO);
			assertEquals(e.getNombre(), "Editada");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(enfermedadService.enfermedadDAO.listarPorId(1L)).thenReturn(null);
		enfermedadService.eliminar(1L);
	}
	
	
}
