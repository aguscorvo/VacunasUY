package vacunasuy.componentecentral.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.converter.EventoConverter;
import vacunasuy.componentecentral.dao.IEventoDAO;
import vacunasuy.componentecentral.dao.ILoteDAO;
import vacunasuy.componentecentral.dao.ITransportistaDAO;
import vacunasuy.componentecentral.dao.IVacunatorioDAO;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.EventoPerifericoDTO;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.EstadoEvento;

@Stateless
public class EventoServiceImpl implements IEventoService {

	@EJB
	private IEventoDAO eventoDAO;
	
	@EJB
	private ILoteDAO loteDAO;
	
	@EJB
	private ITransportistaDAO transportistaDAO;
	
	@EJB
	private EventoConverter eventoConverter;
	
	@EJB
	private IVacunatorioDAO vacunatorioDAO;

	@Override
	public List<EventoDTO> listar() throws VacunasUyException {
		try {
			return eventoConverter.fromEntity(eventoDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public EventoDTO listarPorId(Long id) throws VacunasUyException {
		try {
			Evento evento = eventoDAO.listarPorId(id);
			if(evento == null) throw new VacunasUyException("No existe el registro solicitado.", VacunasUyException.NO_EXISTE_REGISTRO);
			return eventoConverter.fromEntity(evento);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
	@Override
	public List<EventoPerifericoDTO> listarPorEstado(EstadoEvento estado) throws VacunasUyException {
		try {
			return eventoConverter.toEventoPeriferico(eventoDAO.listarPorEstado(estado));
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public EventoDTO crear(EventoCrearDTO eventoDTO) throws VacunasUyException {
		try {
			Evento evento = eventoConverter.fromCrearDTO(eventoDTO);
			Lote lote = loteDAO.listarPorId(eventoDTO.getIdLote());
			if(lote == null) throw new VacunasUyException("No existe el lote indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(eventoDTO.getIdVacunatorio());
			if(vacunatorio == null) throw new VacunasUyException("No existe el vacunatorio indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			/* Se valida que la cantidad del evento no sea mayor a la del lote */
			if(evento.getCantidad() > lote.getCantidadDisponible()) throw new VacunasUyException("La cantidad del evento no puede superar la cantidad disponible del lote.", VacunasUyException.DATOS_INCORRECTOS);
			evento.setLote(lote);
			evento.setEstado(EstadoEvento.INICIADO);
			evento.setVacunatorio(vacunatorio);
			eventoDAO.crear(evento);
			/* Se resta la cantidad del evento al lote */
			lote.setCantidadDisponible(lote.getCantidadDisponible() - evento.getCantidad());
			loteDAO.editar(lote);
			return eventoConverter.fromEntity(evento);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public EventoDTO editar(Long id, EventoCrearDTO eventoDTO) throws VacunasUyException {
		try {
			Evento evento = eventoDAO.listarPorId(id);
			if(evento == null) throw new VacunasUyException("No existe el evento indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Lote lote = loteDAO.listarPorId(eventoDTO.getIdLote());
			if(lote == null) throw new VacunasUyException("No existe el lote indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Transportista transportista = transportistaDAO.listarPorId(eventoDTO.getIdTransportista());
			if(transportista == null) throw new VacunasUyException("No existe el transportista indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Vacunatorio vacunatorio = vacunatorioDAO.listarPorId(eventoDTO.getIdVacunatorio());
			if(vacunatorio == null) throw new VacunasUyException("No existe el vacunatorio indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			evento.setLote(lote);
			evento.setDetalle(eventoDTO.getDetalle());
			evento.setTransportista(transportista);
			Enum<EstadoEvento> estado = EstadoEvento.TRANSITO;
			if(eventoDTO.getEstado().equalsIgnoreCase("recibido")) {
				estado = EstadoEvento.RECIBIDO;
			}
			evento.setEstado(estado);
			evento.setFecha(LocalDateTime.now());
			eventoDAO.editar(evento);
			return eventoConverter.fromEntity(evento);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public void eliminar(Long id) throws VacunasUyException {
		try {
			Evento evento = eventoDAO.listarPorId(id);
			if(evento == null) throw new VacunasUyException("No existe el evento indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			eventoDAO.eliminar(evento);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

}
