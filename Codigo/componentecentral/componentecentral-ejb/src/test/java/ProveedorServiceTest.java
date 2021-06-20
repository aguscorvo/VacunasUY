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

import vacunasuy.componentecentral.business.ProveedorServiceImpl;
import vacunasuy.componentecentral.converter.ProveedorConverter;
import vacunasuy.componentecentral.dao.PaisDAOImpl;
import vacunasuy.componentecentral.dao.ProveedorDAOImpl;
import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.entity.Pais;
import vacunasuy.componentecentral.entity.Proveedor;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class ProveedorServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private ProveedorServiceImpl proveedorService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private PaisDAOImpl paisDAO;
	
	@Mock
	private ProveedorDAOImpl proveedorDAO;
	
	@Mock
	private ProveedorConverter proveedorConverter;
	
	@Before
	public void init() {
		proveedorService = new ProveedorServiceImpl();
		proveedorService.proveedorDAO = this.proveedorDAO;
		proveedorService.paisDAO = this.paisDAO;
		proveedorService.proveedorConverter = this.proveedorConverter;
	}
	
	@Test
	public void listar () {
		List<Proveedor> proveedores = new ArrayList<Proveedor>();
		proveedores.add(new Proveedor(1L, "Prueba", null));
		List<ProveedorDTO> proveedoresDTO = new ArrayList<ProveedorDTO>();
		proveedoresDTO.add(new ProveedorDTO(1L, "Prueba", null));
		Mockito.when(proveedorService.proveedorDAO.listar()).thenReturn(proveedores);
		Mockito.when(proveedorService.proveedorConverter.fromEntity(proveedores)).thenReturn(proveedoresDTO);
		try {
			List<ProveedorDTO> pEsperados = proveedorService.listar();
			assertEquals(1, pEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId () {
		Proveedor prov= new Proveedor(1L, "Prueba", null);
		ProveedorDTO provDTO = new ProveedorDTO(1L, "Prueba", null);
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(prov);
		Mockito.when(proveedorService.proveedorConverter.fromEntity(prov)).thenReturn(provDTO);
		try {
			ProveedorDTO p = proveedorService.listarPorId(1L);
			assertEquals(provDTO.getId(), p.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Proveedor p = null;
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(p);
		@SuppressWarnings("unused")
		ProveedorDTO pDTO = proveedorService.listarPorId(1L);	
	}
	
	@Test
	public void crear() {
 		Pais pais = new Pais (1L, "Brasil");
		Proveedor p = new Proveedor (1L, "nuevo", pais);
		ProveedorDTO pDTO = new ProveedorDTO (1L, "nuevo", new PaisDTO(1L, "Brasil"));
		ProveedorCrearDTO pCDTO = new ProveedorCrearDTO("nuevo", 1L);
		Mockito.when(proveedorService.paisDAO.listarPorId(1L)).thenReturn(pais);
		Mockito.when(proveedorService.proveedorConverter.fromCrearDTO(pCDTO)).thenReturn(p);
		Mockito.when(proveedorService.proveedorDAO.crear(p)).thenReturn(p);
		Mockito.when(proveedorService.proveedorConverter.fromEntity(p)).thenReturn(pDTO);
		try {
			ProveedorDTO prov = proveedorService.crear(pCDTO);
			assertEquals(prov.getNombre(), "nuevo");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_paisNull() throws VacunasUyException {
		ProveedorCrearDTO pCDTO = new ProveedorCrearDTO("nuevo", 1L);
		Mockito.when(proveedorService.paisDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		ProveedorDTO prov = proveedorService.crear(pCDTO);
	
	}
	
	@Test
	public void editar() {
		Pais pais = new Pais (1L, "PaisPrueba");
		ProveedorCrearDTO provCDTO = new ProveedorCrearDTO ("NomEditado", 1L);
		Proveedor prov = new Proveedor(1L, "Prueba", pais);
		Proveedor proveedor_edit = new Proveedor(1L, "NomEditado", new Pais (2L, "PaisEditado"));
		ProveedorDTO proveedorDTO = new ProveedorDTO(1L, "NomEditado", new PaisDTO (1L, "PaisEditado"));
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(prov);
		Mockito.when(proveedorService.paisDAO.listarPorId(1L)).thenReturn(pais);
		Mockito.when(proveedorService.proveedorDAO.editar(prov)).thenReturn(proveedor_edit);
		Mockito.when(proveedorService.proveedorConverter.fromEntity(proveedor_edit)).thenReturn(proveedorDTO);
		try {
			ProveedorDTO p = proveedorService.editar(1L, provCDTO);
			assertEquals(p.getNombre(), "NomEditado");
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_provNull() throws VacunasUyException {
		ProveedorCrearDTO provCDTO = new ProveedorCrearDTO ("NomEditado", 1L);
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		ProveedorDTO p = proveedorService.editar(1L, provCDTO);
		
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_paisNull() throws VacunasUyException {
		Pais pais = new Pais (1L, "PaisPrueba");
		ProveedorCrearDTO provCDTO = new ProveedorCrearDTO ("NomEditado", 2L);
		Proveedor prov = new Proveedor(1L, "Prueba", pais);
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(prov);
		Mockito.when(proveedorService.paisDAO.listarPorId(2L)).thenReturn(null);
		@SuppressWarnings("unused")
		ProveedorDTO p = proveedorService.editar(1L, provCDTO);	
	}
	
	@Test
	public void eliminar() {
		Proveedor prov = new Proveedor (1L, "Prueba", null);
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(prov);
		try {
			proveedorService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(proveedorService.proveedorDAO.listarPorId(1L)).thenReturn(null);
		proveedorService.eliminar(1L);
	}
}