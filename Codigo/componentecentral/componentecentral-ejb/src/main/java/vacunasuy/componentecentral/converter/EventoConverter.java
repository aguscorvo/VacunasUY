package vacunasuy.componentecentral.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.dto.EventoPerifericoDTO;
import vacunasuy.componentecentral.dto.VacunatorioMinDTO;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.util.EstadoEvento;

@Singleton
public class EventoConverter extends AbstractConverter<Evento, EventoDTO>{

	@EJB
	private LoteConverter loteConverter;
	
	@EJB
	private TransportistaConverter transportistaConverter;
	
	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@Override
	public EventoDTO fromEntity(Evento e) {
		if(e == null) return null;
		String estado = "Iniciado";
		if(e.getEstado() != null) {
			if(e.getEstado().equals(EstadoEvento.TRANSITO)) {
				estado = "Transito";
			}else if(e.getEstado().equals(EstadoEvento.RECIBIDO)) {
				estado = "Recibido";
			}
		}
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return EventoDTO.builder()
				.id(e.getId())
				.fecha(e.getFecha().format(formato))
				.detalle(e.getDetalle())
				.cantidad(e.getCantidad())
				.estado(estado)
				.lote(loteConverter.fromEntity(e.getLote()))
				.transportista(transportistaConverter.fromEntity(e.getTransportista()))
				.vacunatorio(vacunatorioConverter.fromEntity(e.getVacunatorio()))
				.build();
	}

	@Override
	public Evento fromDTO(EventoDTO d) {
		if(d == null) return null;
		return Evento.builder()
				.id(d.getId())
				.fecha(LocalDateTime.parse(d.getFecha()))
				.detalle(d.getDetalle())
				.cantidad(d.getCantidad())
				.lote(loteConverter.fromDTO(d.getLote()))
				.transportista(transportistaConverter.fromDTO(d.getTransportista()))
				.build();
	}
	
	public Evento fromCrearDTO(EventoCrearDTO d) {
		if(d == null) return null;
		Enum<EstadoEvento> estado = EstadoEvento.INICIADO;
		if(d.getEstado().equalsIgnoreCase("transito")) {
			estado = EstadoEvento.TRANSITO;
		}else if(d.getEstado().equalsIgnoreCase("recibido")) {
			estado = EstadoEvento.RECIBIDO;
		}
		return Evento.builder()
				.fecha(LocalDateTime.now())
				.detalle(d.getDetalle())
				.cantidad(d.getCantidad())
				.estado(estado)
				.build();
	}
	
	public EventoPerifericoDTO toEventoPeriferico(Evento e) {
		if(e == null) return null;
		String estado = "Iniciado";
		if(e.getEstado().equals(EstadoEvento.TRANSITO)) {
			estado = "Transito";
		} else if(e.getEstado().equals(EstadoEvento.RECIBIDO)) {
			estado = "Recibido";
		}
		Long idTransportista = null;
		if(e.getTransportista() != null) {
			idTransportista = e.getTransportista().getId();
		}
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return EventoPerifericoDTO.builder()
				.id(e.getId())
				.fecha(e.getFecha().format(formato))
				.detalle(e.getDetalle())
				.cantidad(e.getCantidad())
				.estado(estado)
				.idLote(e.getLote().getId())
				.idTransportista(idTransportista)
				.idVacunatorio(e.getVacunatorio().getId())
				.build();
	}
	
	public List<EventoPerifericoDTO> toEventoPeriferico(List<Evento> entities){
		if(entities == null) return null;
		return entities.stream()
			.map(e -> toEventoPeriferico(e))
			.collect(Collectors.toList());
	}

}
