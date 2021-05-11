package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.converter.EventoConverter;
import vacunasuy.componentecentral.dao.IEventoDAO;
import vacunasuy.componentecentral.dao.ILoteDAO;
import vacunasuy.componentecentral.dao.ITransportistaDAO;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.exception.VacunasUyException;

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
	public EventoDTO crear(EventoCrearDTO eventoDTO) throws VacunasUyException {
		try {
			Evento evento = eventoConverter.fromCrearDTO(eventoDTO);
			Lote lote = loteDAO.listarPorId(eventoDTO.getIdLote());
			if(lote == null) throw new VacunasUyException("No existe el lote indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			/* Se valida que la cantidad del evento no sea mayor a la del lote */
			if(evento.getCantidad() > lote.getCantidadDisponible()) throw new VacunasUyException("La cantidad del evento no puede superar la cantidad disponible del lote.", VacunasUyException.DATOS_INCORRECTOS);
			Transportista transportista = transportistaDAO.listarPorId(eventoDTO.getIdTransportista());
			if(transportista == null) throw new VacunasUyException("No existe el transportista indicado.", VacunasUyException.NO_EXISTE_REGISTRO);
			evento.setLote(lote);
			evento.setTransportista(transportista);
			eventoDAO.crear(evento);
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
			evento.setLote(lote);
			evento.setTransportista(transportista);
			eventoDAO.crear(evento);
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
