import static org.junit.Assert.assertEquals;
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
	
	@Test (expected = VacunasUyException.class)
	public void listarPorIdEventoNull() throws VacunasUyException {
		Mockito.when(eventoService.eventoDAO.listarPorId(1L)).thenReturn(null);
		EventoDTO eventoResultado = eventoService.listarPorId(1L);
		assertNull(eventoResultado);		
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
		Lote lote = new Lote(1L, 500L, 51L, null, null);
		Vacunatorio vacunatorio = new Vacunatorio(1L, null, null, null, null, null, null, null, null, null, null);
		Evento evento = new Evento(1L, null, null, 50L, null, lote, null, vacunatorio);
		
		Long diferencia = lote.getCantidadDisponible()-evento.getCantidad();
		evento.setLote(lote);
		evento.setEstado(EstadoEvento.INICIADO);
		evento.setVacunatorio(vacunatorio);
		lote.setCantidadDisponible(diferencia);
		Lote loteEditado = new Lote(1L, 500L, 1L, null, null);
		LoteDTO loteEditadoDTO = new LoteDTO (1L, 500L, 1L, null, null);
		VacunatorioDTO vacunatorioDTO = new VacunatorioDTO(1L, null, null, null, null, null, null, null, null);
		EventoDTO eventoDTO = new EventoDTO(1L, null, null, 1L, null, loteEditadoDTO, null, vacunatorioDTO);
		Mockito.when(eventoService.eventoConverter.fromCrearDTO(eventoCrearDTO)).thenReturn(evento);
		Mockito.when(eventoService.loteDAO.listarPorId(eventoCrearDTO.getIdLote())).thenReturn(lote);
		Mockito.when(eventoService.vacunatorioDAO.listarPorId(eventoCrearDTO.getIdVacunatorio())).thenReturn(vacunatorio);
		Mockito.when(eventoService.eventoDAO.crear(evento)).thenReturn(evento);		
		Mockito.when(eventoService.loteDAO.editar(lote)).thenReturn(loteEditado);		
		Mockito.when(eventoService.eventoConverter.fromEntity(evento)).thenReturn(eventoDTO);
		try {
			Evento evento2 = new Evento(1L, null, null, 50L, null, lote, null, vacunatorio);
			EventoDTO eventoResultadoDTO = eventoService.crear(eventoCrearDTO);
			assertEquals(eventoResultadoDTO.getId(), evento2);
		}catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	//crearLoteNull
	//crearVacunatorioNull
	//editar
	//editarEventoNull
	//editarLoteNull
	//editarTransportistaNull
	//editarVacunatorioNull
	//eliminar
	//eliminar eventoNull
	
}
