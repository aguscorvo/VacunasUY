import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vacunasuy.componentecentral.business.LoteServiceImpl;
import vacunasuy.componentecentral.converter.LoteConverter;
import vacunasuy.componentecentral.dao.LoteDAOImpl;
import vacunasuy.componentecentral.dao.ProveedorDAOImpl;
import vacunasuy.componentecentral.dao.VacunaDAOImpl;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.Proveedor;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class LoteServiceTest {

	@InjectMocks
	private LoteServiceImpl loteService;
	
	@Mock
	private LoteDAOImpl loteDAO;
	
	@Mock
	private ProveedorDAOImpl proveedorDAO;
	
	@Mock
	private VacunaDAOImpl vacunaDAO;
	
	@Mock
	private LoteConverter loteConverter;
	
	
	@Before
	public void init() {
		loteService = new LoteServiceImpl();
		loteService.loteDAO = this.loteDAO;
		loteService.proveedorDAO = this.proveedorDAO;
		loteService.vacunaDAO = this.vacunaDAO;
		loteService.loteConverter = this.loteConverter;
	}
	
	@Test
	public void listar() {
		List<Lote> lotes = new ArrayList<Lote>();
		lotes.add(new Lote(1L, 1L, 1L, null, null));
		List<LoteDTO> lotesDTO = new ArrayList<LoteDTO>();
		lotesDTO.add(new LoteDTO(1L, 1L, 1L, null, null));
		Mockito.when(loteService.loteDAO.listar()).thenReturn(lotes);
		Mockito.when(loteService.loteConverter.fromEntity(Mockito.anyList())).thenReturn(lotesDTO);
		try {
			List<LoteDTO> lotesEsperados = loteService.listar();
			assertEquals(1, lotesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Lote lote = new Lote(1L, 1L, 1L, null, null);
		LoteDTO loteDTO = new LoteDTO(1L, 1L, 1L, null, null);
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Mockito.when(loteService.loteConverter.fromEntity(lote)).thenReturn(loteDTO);
		try {
			LoteDTO lote2 = loteService.listarPorId(1L);
			assertEquals(lote.getId(), lote2.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void listarPorIdLoteNull() throws VacunasUyException {
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(null);
		LoteDTO loteResultado = loteService.listarPorId(1L);
		assertNull(loteResultado);		
	}
	
	@Test
	public void crear() {
		Lote lote = new Lote(1L, 1L, 1L, null, null);
		LoteCrearDTO loteCrearDTO = new LoteCrearDTO(1L, 1L, 1L);
		Proveedor proveedor = new Proveedor(1L, "Proveedor", null);
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		lote.setProveedor(proveedor);
		lote.setVacuna(vacuna);
		LoteDTO loteDTO = new LoteDTO(1L,1L, 1L, null, null);
		Mockito.when(loteService.loteConverter.fromCrearDTO(loteCrearDTO)).thenReturn(lote);
		Mockito.when(loteService.proveedorDAO.listarPorId(1L)).thenReturn(proveedor);
		Mockito.when(loteService.vacunaDAO.listarPorId(1L)).thenReturn(vacuna);
		Mockito.when(loteService.loteDAO.crear(lote)).thenReturn(lote);
		Mockito.when(loteService.loteConverter.fromEntity(lote)).thenReturn(loteDTO);
		try {
			LoteDTO resultado = loteService.crear(loteCrearDTO);
			assertEquals(resultado.getId(), lote.getId());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void crearProveedorNull() throws VacunasUyException {
		LoteCrearDTO loteCrearDTO = new LoteCrearDTO(1L, 2L, 2L);
		LoteDTO loteResultado = loteService.crear(loteCrearDTO);
		assertNull(loteResultado);		
	}
	
	@Test (expected = VacunasUyException.class)
	public void crearVacunaNull() throws VacunasUyException {
		Proveedor proveedor = new Proveedor(1L, "Proveedor", null);
		LoteCrearDTO loteCrearDTO = new LoteCrearDTO(1L, 1L, 2L);
		Mockito.when(loteService.proveedorDAO.listarPorId(1L)).thenReturn(proveedor);
		LoteDTO loteResultado = loteService.crear(loteCrearDTO);
		assertNull(loteResultado);		
	}
	
	@Test
	public void editar() {
		Proveedor proveedor = new Proveedor(1L, "Proveedor", null);
		Proveedor proveedorNuevo= new Proveedor(2L, "Proveedor", null);
		Vacuna vacuna = new Vacuna(1L, "Vacuna", 1, 1, 1, null);
		Lote lote = new Lote(1L, 1L, 1L, proveedor, vacuna);
		LoteDTO loteDTO = loteService.loteConverter.fromEntity(lote);
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(lote);
		LoteCrearDTO loteEditadoCrearDTO  = new LoteCrearDTO(30L, 2L, 1L);
		Mockito.when(loteService.proveedorDAO.listarPorId(loteEditadoCrearDTO.getIdProveedor())).thenReturn(proveedorNuevo);
		Mockito.when(loteService.vacunaDAO.listarPorId(loteEditadoCrearDTO.getIdVacuna())).thenReturn(vacuna);
		lote.setProveedor(proveedorNuevo);
		lote.setVacuna(vacuna);
		Mockito.when(loteService.loteDAO.editar(lote)).thenReturn(lote);
		ProveedorDTO proveedorNuevoDTO= new ProveedorDTO(2L, "Pais", null);
		VacunaDTO vacunaDTO = new VacunaDTO(1L, "Vacuna", 1, 1, 1, null);
		LoteDTO loteEditado = new LoteDTO (1L, 30L, 1L, proveedorNuevoDTO, vacunaDTO);
		Mockito.when(loteService.loteConverter.fromEntity(lote)).thenReturn(loteEditado);
		try {
			LoteDTO resultado = loteService.editar(1L, loteEditadoCrearDTO);
			assertNotEquals(loteDTO, resultado);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void editarLoteNull() throws VacunasUyException {
		Mockito.when(loteService.loteDAO.listarPorId(2L)).thenReturn(null);
		loteService.editar(2L, null);		
	}
	
	@Test (expected = VacunasUyException.class)
	public void editarProveedorNull() throws VacunasUyException {
		Lote lote = new Lote(1L, 1L, 1L, null, null);
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Mockito.when(loteService.proveedorDAO.listarPorId(1L)).thenReturn(null);
		LoteCrearDTO loteCrearDTO = new LoteCrearDTO(1L, 1L, 1L);
		loteService.editar(1L, loteCrearDTO);		
	}
	
	@Test (expected = VacunasUyException.class)
	public void editarVacunaNull() throws VacunasUyException {
		Lote lote = new Lote(1L, 1L, 1L, null, null);
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Proveedor proveedor = new Proveedor(1L, "Proveedor", null);
		Mockito.when(loteService.proveedorDAO.listarPorId(1L)).thenReturn(proveedor);
		Mockito.when(loteService.vacunaDAO.listarPorId(1L)).thenReturn(null);
		LoteCrearDTO loteCrearDTO = new LoteCrearDTO(1L, 1L, 1L);
		loteService.editar(1L, loteCrearDTO);	
	}
	
	@Test
	public void eliminar() {
		Lote lote = new Lote(1L, 1L, 1L, null, null);
		Mockito.when(loteService.loteDAO.listarPorId(1L)).thenReturn(lote);
		try {
			loteService.eliminar(1L);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = VacunasUyException.class)
	public void eliminarLoteNull() throws VacunasUyException{
		Mockito.when(loteService.loteDAO.listarPorId(2L)).thenReturn(null);
		loteService.eliminar(2L);		
	}
	
}
