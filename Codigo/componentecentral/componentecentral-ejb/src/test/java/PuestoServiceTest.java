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

import vacunasuy.componentecentral.business.PuestoServiceImpl;
import vacunasuy.componentecentral.converter.PuestoConverter;
import vacunasuy.componentecentral.dao.AgendaDAOImpl;
import vacunasuy.componentecentral.dao.PuestoDAOImpl;
import vacunasuy.componentecentral.dao.VacunatorioDAOImpl;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class PuestoServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private PuestoServiceImpl puestoService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private PuestoDAOImpl puestoDAO;
	
	@Mock
	private AgendaDAOImpl agendaDAO;
	
	@Mock
	private VacunatorioDAOImpl vacunatorioDAO;
	
	@Mock
	private PuestoConverter puestoConverter;
	
	@Before
	public void init() {
		puestoService = new PuestoServiceImpl();
		puestoService.puestoDAO = this.puestoDAO;
		puestoService.agendaDAO = this.agendaDAO;
		puestoService.vacunatorioDAO = this.vacunatorioDAO;
		puestoService.puestoConverter = this.puestoConverter;
	}

	@Test
	public void listar() {
		Puesto puesto = new Puesto (1L, 1, null, null, null );
		List<Puesto> puestos = new ArrayList<Puesto>();
		puestos.add(puesto);
		PuestoDTO puestoDTO = new PuestoDTO (1L, 1, null, null);
		List<PuestoDTO> puestosDTO = new ArrayList<PuestoDTO>();
		puestosDTO.add(puestoDTO);
		Mockito.when(puestoService.puestoDAO.listar()).thenReturn(puestos);
		Mockito.when(puestoService.puestoConverter.fromEntity(puestos)).thenReturn(puestosDTO);
		try {
			List<PuestoDTO> puestosEsperados = puestoService.listar();
			assertEquals(1, puestosEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Puesto puesto = new Puesto (1L, 1, null, null, null );
		PuestoDTO puestoDTO = new PuestoDTO (1L, 1, null, null);
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(puesto);
		Mockito.when(puestoService.puestoConverter.fromEntity(puesto)).thenReturn(puestoDTO);
		try {
			PuestoDTO p = puestoService.listarPorId(1L);
			assertEquals(puestoDTO.getId(), p.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Puesto puesto = null;
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(puesto);
		@SuppressWarnings("unused")
		PuestoDTO puestoDTO = puestoService.listarPorId(1L);	
	}
	
	@Test
	public void listarPorNumero() {
		Puesto puesto = new Puesto (1L, 1, null, null, null );
		PuestoDTO puestoDTO = new PuestoDTO (1L, 1, null, null);
		Mockito.when(puestoService.puestoDAO.listarPorNumero(1)).thenReturn(puesto);
		Mockito.when(puestoService.puestoConverter.fromEntity(puesto)).thenReturn(puestoDTO);
		try {
			PuestoDTO p = puestoService.listarPorNumero(1);
			assertEquals(puestoDTO.getId(), p.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorNumero_null () throws VacunasUyException {
		Puesto puesto = null;
		Mockito.when(puestoService.puestoDAO.listarPorNumero(1)).thenReturn(puesto);
		@SuppressWarnings("unused")
		PuestoDTO puestoDTO = puestoService.listarPorNumero(1);	
	}
	
	@Test
	public void crear() {
		List<Puesto> puestos = new ArrayList<Puesto>();
		Vacunatorio vac = new Vacunatorio (1L, null, null, null, null, null, null, null, puestos, null, null);
		Puesto puesto = new Puesto (1L, 1, null, null, null );
		PuestoMinDTO puestoMinDTO = new PuestoMinDTO (1L, 1, null);
		PuestoCrearDTO pCDTO = new PuestoCrearDTO(1, 1L);
		Mockito.when(puestoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vac);
		Mockito.when(puestoService.puestoConverter.fromCrearDTO(pCDTO)).thenReturn(puesto);
		Mockito.when(puestoService.puestoDAO.crear(puesto)).thenReturn(puesto);
		Mockito.when(puestoService.puestoConverter.fromEntityToMin(puesto)).thenReturn(puestoMinDTO);
		try {
			PuestoMinDTO pMDTO = puestoService.crear(pCDTO);
			assertEquals(pMDTO.getNumero(), 1);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}		
	}
		
	@Test(expected = VacunasUyException.class)
	public void crear_vacNull() throws VacunasUyException {
		PuestoCrearDTO pCDTO = new PuestoCrearDTO(1, 1L);
		Mockito.when(puestoService.vacunatorioDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		PuestoMinDTO pMDTO = puestoService.crear(pCDTO);
	}
	
	@Test
	public void editar() {
		Puesto puesto1 = new Puesto (1L, 1, null, null, null );
		Puesto puesto2 = new Puesto (1L, 2, null, null, null );
		PuestoDTO puestoDTO = new PuestoDTO(1L, 2, null, null);
		PuestoCrearDTO pCDTO = new PuestoCrearDTO(2, 1L);
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(puesto1);
		Mockito.when(puestoService.puestoDAO.editar(puesto1)).thenReturn(puesto2);
		Mockito.when(puestoService.puestoConverter.fromEntity(puesto2)).thenReturn(puestoDTO);
		try {
			PuestoDTO pDTO = puestoService.editar(1L, pCDTO);
			assertEquals(pDTO.getNumero(), 2);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}		
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_puestoNull() throws VacunasUyException {
		PuestoCrearDTO pCDTO = new PuestoCrearDTO(2, 1L);
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		PuestoDTO pDTO = puestoService.editar(1L, pCDTO);
			
	}
	
	@Test
	public void eliminar() {
		Puesto p = new Puesto (1L, 1, null, null, null);
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(p);
		try {
			puestoService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(puestoService.puestoDAO.listarPorId(1L)).thenReturn(null);
		puestoService.eliminar(1L);
	}
	
	
		
}