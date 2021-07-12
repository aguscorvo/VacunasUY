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

import vacunasuy.componentecentral.business.TransportistaServiceImpl;
import vacunasuy.componentecentral.converter.TransportistaConverter;
import vacunasuy.componentecentral.dao.TransportistaDAOImpl;
import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class TransportistaServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private TransportistaServiceImpl transportistaService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	
	@Mock
	private TransportistaDAOImpl transportistaDAO;
	
	@Mock
	private TransportistaConverter transportistaConverter;
	
	@Before
	public void init() {
		transportistaService = new TransportistaServiceImpl();
		transportistaService.transportistaDAO = this.transportistaDAO;
		transportistaService.transportistaConverter = this.transportistaConverter;
	}
	
	@Test
	public void listar() {
		List<Transportista> transportistas = new ArrayList<Transportista>();
		transportistas.add(new Transportista(1L, "Nombre"));
		List<TransportistaDTO> transportistasDTO = new ArrayList<TransportistaDTO>();
		transportistasDTO.add(new TransportistaDTO(1L, "Nombre"));
		Mockito.when(transportistaService.transportistaDAO.listar()).thenReturn(transportistas);
		Mockito.when(transportistaService.transportistaConverter.fromEntity(transportistas)).thenReturn(transportistasDTO);
		try {
			List<TransportistaDTO> transportistasesperados = transportistaService.listar();
			assertEquals(1, transportistasesperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId () {
		Transportista t = new Transportista(1L, "nombre");
		TransportistaDTO tDTO = new TransportistaDTO(1L, "nombre");
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		Mockito.when(transportistaService.transportistaConverter.fromEntity(t)).thenReturn(tDTO);
		try {
			TransportistaDTO tEsperado = transportistaService.listarPorId(1L);
			assertEquals(tEsperado.getNombre(), "nombre");
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_transportistaNull () throws VacunasUyException {
		Transportista t = null;
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		@SuppressWarnings("unused")
		TransportistaDTO tEsperado = transportistaService.listarPorId(1L);
	}
	
	@Test(expected =  VacunasUyException.class)
	public void crear() throws VacunasUyException {
		TransportistaCrearDTO tCDTO = new TransportistaCrearDTO("nombre");
		Transportista t = new Transportista(1L, "nombre");
		Mockito.when(transportistaService.transportistaConverter.fromCrearDTO(tCDTO)).thenReturn(t);
		Mockito.when(transportistaService.transportistaDAO.crear(t)).thenReturn(t);
		@SuppressWarnings("unused")
		TransportistaDTO tEsperado = transportistaService.crear(tCDTO);
	}
	
	@Test
	public void editar() {
		TransportistaCrearDTO tCDTO = new TransportistaCrearDTO("nombre");
		Transportista t = new Transportista(1L, "nombre");
		Transportista t_edit = new Transportista(1L, "nombreEdit");
		TransportistaDTO tDTO = new TransportistaDTO(1L, "nombreEdit");
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		Mockito.when(transportistaService.transportistaDAO.editar(t)).thenReturn(t_edit);
		Mockito.when(transportistaService.transportistaConverter.fromEntity(t_edit)).thenReturn(tDTO);
		try {
			TransportistaDTO tEsperado = transportistaService.editar(1L, tCDTO);
			assertEquals(tEsperado.getNombre(), "nombreEdit");
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_transportistaNull() throws VacunasUyException {
		TransportistaCrearDTO tCDTO = new TransportistaCrearDTO("nombre");
		Transportista t = null;
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		@SuppressWarnings("unused")
		TransportistaDTO tEsperado = transportistaService.editar(1L, tCDTO);
	}
	

	@Test
	public void eliminar() {
		Transportista t = new Transportista(1L, "Prueba");
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		try {
			transportistaService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(transportistaService.transportistaDAO.listarPorId(1L)).thenReturn(null);
		transportistaService.eliminar(1L);
	}
	
}