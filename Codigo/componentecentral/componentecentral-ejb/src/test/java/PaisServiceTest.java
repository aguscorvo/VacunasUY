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

import vacunasuy.componentecentral.business.PaisServiceImpl;
import vacunasuy.componentecentral.converter.PaisConverter;
import vacunasuy.componentecentral.dao.PaisDAOImpl;
import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.entity.Pais;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class PaisServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private PaisServiceImpl paisService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private PaisDAOImpl paisDAO;
	
	@Mock
	private PaisConverter paisConverter;
	
	@Before
	public void init() {
		paisService = new PaisServiceImpl();
		paisService.paisDAO = this.paisDAO;
		paisService.paisConverter = this.paisConverter;
	}
	
	@Test
	public void listar () {
		List<Pais> paises = new ArrayList<Pais>();
		paises.add(new Pais(1L, "Prueba"));
		List<PaisDTO> paisesDTO = new ArrayList<PaisDTO>();
		paisesDTO.add(new PaisDTO(1L, "Prueba"));
		Mockito.when(paisService.paisDAO.listar()).thenReturn(paises);
		Mockito.when(paisService.paisConverter.fromEntity(paises)).thenReturn(paisesDTO);
		try {
			List<PaisDTO> paisesEsperados = paisService.listar();
			assertEquals(1, paisesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId () {
		Pais pais = new Pais(1L, "Prueba");
		PaisDTO paisDTO = new PaisDTO(1L, "Prueba");
		Mockito.when(paisService.paisDAO.listarPorId(1L)).thenReturn(pais);
		Mockito.when(paisService.paisConverter.fromEntity(pais)).thenReturn(paisDTO);
		try {
			PaisDTO paisito = paisService.listarPorId(1L);
			assertEquals(paisDTO.getId(), paisito.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Pais pais = null;
		Mockito.when(paisService.paisDAO.listarPorId(1L)).thenReturn(pais);
		@SuppressWarnings("unused")
		PaisDTO paisDTO = paisService.listarPorId(1L);	
	}
}



