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

import vacunasuy.componentecentral.business.EventoServiceImpl;
import vacunasuy.componentecentral.business.StockServiceImpl;
import vacunasuy.componentecentral.converter.EventoConverter;
import vacunasuy.componentecentral.dao.EventoDAOImpl;
import vacunasuy.componentecentral.dao.LoteDAOImpl;
import vacunasuy.componentecentral.dao.TransportistaDAOImpl;
import vacunasuy.componentecentral.dao.VacunatorioDAOImpl;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.EventoPerifericoDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.util.EstadoEvento;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class EventoServiceTest {

	@InjectMocks
	private EventoServiceImpl eventoService;
	
	@Mock
	private StockServiceImpl stockService;
	
	@Mock
	private EventoDAOImpl eventoDAO;
	
	@Mock
	private LoteDAOImpl loteDAO;
	
	@Mock
	private TransportistaDAOImpl transportistaDAO;
	
	@Mock
	private VacunatorioDAOImpl vacunatorioDAO;
	
	@Mock
	private EventoConverter eventoConverter;
	
	
	@Before
	public void init() {
		eventoService = new EventoServiceImpl();
		eventoService.stockService = this.stockService;
		eventoService.eventoDAO = this.eventoDAO;
		eventoService.loteDAO = this.loteDAO;
		eventoService.transportistaDAO = this.transportistaDAO;
		eventoService.vacunatorioDAO = this.vacunatorioDAO;
		eventoService.eventoConverter = this.eventoConverter;
	}
	
	@Test
	public void listar() {
		Evento evento = new Evento(1L, null, "Detalle", 1L, null, null, null, null);
		List<Evento> eventos = new ArrayList<Evento>();
		eventos.add(evento);
		Mockito.when(eventoService.eventoDAO.listar()).thenReturn(eventos);
		EventoDTO eventoDTO = new EventoDTO(1L, "Fecha", "Detalle", 1L, "Estado", null, null, null);
		List<EventoDTO> eventosDTO = new ArrayList<EventoDTO>();
		eventosDTO.add(eventoDTO);
		Mockito.when(eventoService.eventoConverter.fromEntity(eventos)).thenReturn(eventosDTO);
		try {
			List<EventoDTO> eventosResultado = eventoService.listar();
			assertEquals(eventosDTO, eventosResultado);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarPorId() {
		Evento evento = new Evento(1L, null, "Detalle", 1L, null, null, null, null);
		EventoDTO eventoDTO = new EventoDTO(1L, null, "Detalle", 1L, null, null, null, null);
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Mockito.when(eventoService.eventoConverter.fromEntity(evento)).thenReturn(eventoDTO);
		try {
			EventoDTO evento2DTO = eventoService.listarPorId(1L);
			assertEquals(eventoDTO.getId(), evento2DTO.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorIdEventoNull() throws VacunasUyException {
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(null);
		@SuppressWarnings("unused")
		EventoDTO eventoResultado = eventoService.listarPorId(1L);		
	}
	
	@Test
	public void listarPorEstado() {
		EstadoEvento estado = EstadoEvento.INICIADO;
		Evento evento = new Evento(1L, null, "Detalle", 1L, estado, null, null, null);
		List<Evento> eventos = new ArrayList<Evento>();
		eventos.add(evento);
		Mockito.when(eventoService.eventoDAO.listarPorEstado(estado)).thenReturn(eventos);
		EventoPerifericoDTO eventoPerifericoDTO = new EventoPerifericoDTO(1L, null, "Detalle", 1L, "INICIADO", null, null, null);
		List<EventoPerifericoDTO> eventosPerifericosDTO = new ArrayList<EventoPerifericoDTO>();
		eventosPerifericosDTO.add(eventoPerifericoDTO);
		Mockito.when(eventoService.eventoConverter.toEventoPeriferico(eventos)).thenReturn(eventosPerifericosDTO);
		try {
			List<EventoPerifericoDTO> eventosPerifericosDTOResultado = eventoService.listarPorEstado(estado);
			assertEquals(eventosPerifericosDTO, eventosPerifericosDTOResultado);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void crear() {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, null, 50L, null, 1L, null, 1L);
		Lote lote = new Lote(1L, 500L, 500L, null, null);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Evento evento = new Evento(1L, null, null, 50L, null, null, null, null);
		Mockito.when(eventoService.eventoConverter.fromCrearDTO(eventoCrearDTO)).thenReturn(evento);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		Mockito.when(eventoService.eventoDAO.crear(evento)).thenReturn(evento);	
		Long diferencia = lote.getCantidadDisponible()-evento.getCantidad();
		lote.setCantidadDisponible(diferencia);
		Mockito.when(eventoService.loteDAO.editar(lote)).thenReturn(lote);
		VacunatorioDTO vacunatorioDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		LoteDTO loteDTO = new LoteDTO (1L, 500L, diferencia, null, null);
		EventoDTO eventoDTO = new EventoDTO(1L, null, null, 50L, null, loteDTO, null, vacunatorioDTO);
		Mockito.when(eventoService.eventoConverter.fromEntity(evento)).thenReturn(eventoDTO);
		try {
			EventoDTO eventoResultadoDTO = eventoService.crear(eventoCrearDTO);
			assertEquals(eventoResultadoDTO.getCantidad(), eventoDTO.getCantidad());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_loteNull() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, null, 50L, null, 1L, null, 1L);
		Lote lote = null;
		Evento evento = new Evento(1L, null, null, 50L, null, null, null, null);
		Mockito.when(eventoService.eventoConverter.fromCrearDTO(eventoCrearDTO)).thenReturn(evento);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.crear(eventoCrearDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_vacunatorioNull() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, null, 50L, null, 1L, null, 1L);
		Lote lote = new Lote(1L, 500L, 500L, null, null);
		Vacunatorio vacunatorio = null;
		Evento evento = new Evento(1L, null, null, 50L, null, null, null, null);
		Mockito.when(eventoService.eventoConverter.fromCrearDTO(eventoCrearDTO)).thenReturn(evento);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.crear(eventoCrearDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void crear_cantInvalida() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, null, 50L, null, 1L, null, 1L);
		Lote lote = new Lote(1L, 500L, 500L, null, null);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Evento evento = new Evento(1L, null, null, 501L, null, null, null, null);
		Mockito.when(eventoService.eventoConverter.fromCrearDTO(eventoCrearDTO)).thenReturn(evento);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.crear(eventoCrearDTO);
	}
	
	@Test
	public void editar_recibido() {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "recibido", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 0, 0, 0, null);
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Enum<EstadoEvento> estado = EstadoEvento.RECIBIDO;
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Lote lote = new Lote(1L, 500L, 500L, null, vacuna);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Transportista t = new Transportista(1L, "nombre");
		Mockito.when(eventoService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		Evento evento_editado = new Evento(1L,null, "detalleNuevo", 50L, estado, lote, t, vacunatorio);
		Mockito.when(eventoService.eventoDAO.editar(evento)).thenReturn(evento_editado);
		try {
			Mockito.doNothing().when(eventoService.stockService).sumarStock(vacunatorio, vacuna, 50L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
		EventoDTO eventoDTO = new EventoDTO(1L, null, "detalleNuevo", 50L, "recibido", null, null, null);
		Mockito.when(eventoService.eventoConverter.fromEntity(evento)).thenReturn(eventoDTO);
		try {
			EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
			assertEquals(eventoResultadoDTO.getCantidad(), eventoDTO.getCantidad());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void editar_transito() {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "transito", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 0, 0, 0, null);
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Enum<EstadoEvento> estado = EstadoEvento.TRANSITO;
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Lote lote = new Lote(1L, 500L, 500L, null, vacuna);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Transportista t = new Transportista(1L, "nombre");
		Mockito.when(eventoService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		Evento evento_editado = new Evento(1L,null, "detalleNuevo", 50L, estado, lote, t, vacunatorio);
		Mockito.when(eventoService.eventoDAO.editar(evento)).thenReturn(evento_editado);
		EventoDTO eventoDTO = new EventoDTO(1L, null, "detalleNuevo", 50L, "transito", null, null, null);
		Mockito.when(eventoService.eventoConverter.fromEntity(evento)).thenReturn(eventoDTO);
		try {
			EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
			assertEquals(eventoResultadoDTO.getCantidad(), eventoDTO.getCantidad());
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_eventoNull () throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "recibido", 1L, 1L, 1L);
		Evento evento = null;
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
	}
	
	
	@Test(expected = VacunasUyException.class)
	public void editar_loteNull() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "recibido", 1L, 1L, 1L);
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Lote lote = null;
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_transportistaNull() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "recibido", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 0, 0, 0, null);
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Lote lote = new Lote(1L, 500L, 500L, null, vacuna);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Transportista t = null;
		Mockito.when(eventoService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
	}
	
	@Test(expected = VacunasUyException.class)
	public void editar_VacunatorioNull() throws VacunasUyException {
		EventoCrearDTO eventoCrearDTO = new EventoCrearDTO(null, "detalleNuevo", 50L, "recibido", 1L, 1L, 1L);
		Vacuna vacuna = new Vacuna (1L, null, 0, 0, 0, null);
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		Lote lote = new Lote(1L, 500L, 500L, null, vacuna);
		Mockito.when(eventoService.loteDAO.listarPorId(1L)).thenReturn(lote);
		Transportista t = new Transportista(1L, "nombre");
		Mockito.when(eventoService.transportistaDAO.listarPorId(1L)).thenReturn(t);
		Vacunatorio vacunatorio = null;
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(1L)).thenReturn(vacunatorio);
		@SuppressWarnings("unused")
		EventoDTO eventoResultadoDTO = eventoService.editar(1L, eventoCrearDTO);
	}
	
	@Test
	public void eliminar() {
		Evento evento = new Evento(1L, null, "detalle", 50L, null, null, null, null);
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(evento);
		try {
			eventoService.eliminar(1L);
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void eliminar_null() throws VacunasUyException {
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(null);
		eventoService.eliminar(1L);
	}
	
}
