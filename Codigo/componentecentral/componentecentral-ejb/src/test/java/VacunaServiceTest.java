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

import vacunasuy.componentecentral.business.VacunaServiceImpl;
import vacunasuy.componentecentral.converter.VacunaConverter;
import vacunasuy.componentecentral.dao.EnfermedadDAOImpl;
import vacunasuy.componentecentral.dao.VacunaDAOImpl;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunaMinDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;


@RunWith(MockitoJUnitRunner.class)
public class VacunaServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private VacunaServiceImpl vacunaService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	@Mock
	private VacunaDAOImpl vacunaDAO;
	
	@Mock
	private EnfermedadDAOImpl enfermedadDAO;
	
	@Mock
	private VacunaConverter vacunaConverter;
	
	@Before
	public void init() {
		vacunaService = new VacunaServiceImpl();
		vacunaService.vacunaDAO = this.vacunaDAO;
		vacunaService.vacunaConverter = this.vacunaConverter;
		vacunaService.enfermedadDAO = this.enfermedadDAO;
	}
	
	@Test
	public void listar() {
		List<Vacuna> vacunas = new ArrayList<Vacuna>();
		vacunas.add(new Vacuna(1L, "Prueba", 1, 1, 1, new Enfermedad (1L, "Prueba")));
		List<VacunaDTO> vacunasDTO = new ArrayList<VacunaDTO>();
		vacunasDTO.add(new VacunaDTO(1L, "Prueba", 1, 1, 1, new EnfermedadDTO (1L, "Prueba")));
		Mockito.when(vacunaService.vacunaDAO.listar()).thenReturn(vacunas);
		Mockito.when(vacunaService.vacunaConverter.fromEntity(Mockito.anyList())).thenReturn(vacunasDTO);
		try {
			List<VacunaDTO> vacunasesperadas = vacunaService.listar();
			assertEquals(1, vacunasesperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Vacuna vacuna = new Vacuna(1L, "Prueba", 1, 1, 1, new Enfermedad (1L, "Prueba"));
		Mockito.when(vacunaService.vacunaDAO.listarPorId(1L)).thenReturn(vacuna);
		Vacuna v = vacunaService.listarPorId(1L);
		assertEquals(vacuna.getId(), v.getId());

	}
	
	@Test
	public void listarVacunasPorUsuario() {
		List<VacunaMinDTO> vacunasMDTO = new ArrayList<VacunaMinDTO>();
		vacunasMDTO.add(new VacunaMinDTO(1L, "Prueba", 1, 1, 1));
		Mockito.when(vacunaService.vacunaConverter.fromEntityToMin(vacunaService.vacunaDAO.listarVacunasPorUsuario(Mockito.anyLong()))).thenReturn(vacunasMDTO);
		try {
			List<VacunaMinDTO> vacunasesperadas = vacunaService.listarVacunasPorUsuario(Mockito.anyLong());
			assertEquals(1, vacunasesperadas.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void crear() {
		VacunaCrearDTO vCDTO = new VacunaCrearDTO("nueva", 1, 1, 1, 1L);
		VacunaDTO vDTO_creada = new VacunaDTO(1L, "nueva", 1, 1, 1, new EnfermedadDTO (1L, "Prueba"));
		Enfermedad enfermedad = new Enfermedad();
		Vacuna vacuna = new Vacuna (1L, "nueva", 1, 1, 1, null);
		Mockito.when(vacunaService.enfermedadDAO.listarPorId(1L)).thenReturn(enfermedad);
		Mockito.when(vacunaService.vacunaConverter.fromCrearDTO(vCDTO)).thenReturn(vacuna);
		Mockito.when(vacunaService.vacunaDAO.crear(vacuna)).thenReturn(vacuna);
		Mockito.when(vacunaService.vacunaConverter.fromEntity(vacuna)).thenReturn(vDTO_creada);
		try {
			VacunaDTO vacunaDTO = vacunaService.crear(vCDTO);
			assertEquals(vacunaDTO.getNombre(), "nueva");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void editar () {
		VacunaCrearDTO vCDTO = new VacunaCrearDTO("Editada", 1, 1, 1, 1L);
		Vacuna vacuna = new Vacuna (1L, "Prueba", 1, 1, 1, null);
		Enfermedad enfermedad = new Enfermedad();
		VacunaDTO vDTO_creada = new VacunaDTO(1L, "Editada", 1, 1, 1, new EnfermedadDTO (1L, "Prueba"));
		Mockito.when(vacunaService.vacunaDAO.listarPorId(1L)).thenReturn(vacuna);
		Mockito.when(vacunaService.enfermedadDAO.listarPorId(1L)).thenReturn(enfermedad);
		Mockito.when(vacunaService.vacunaDAO.editar(vacuna)).thenReturn(vacuna);
		Mockito.when(vacunaService.vacunaConverter.fromEntity(vacuna)).thenReturn(vDTO_creada);
		try {
			VacunaDTO v = vacunaService.editar(1L, vCDTO);
			assertEquals(v.getNombre(), "Editada");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void eliminar() {
		Vacuna v = new Vacuna (1L, "Prueba", 1, 1, 1, null);
		Mockito.when(vacunaService.vacunaDAO.listarPorId(1L)).thenReturn(v);
		try {
			vacunaService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(vacunaService.vacunaDAO.listarPorId(1L)).thenReturn(null);
		vacunaService.eliminar(1L);
	}
	
}