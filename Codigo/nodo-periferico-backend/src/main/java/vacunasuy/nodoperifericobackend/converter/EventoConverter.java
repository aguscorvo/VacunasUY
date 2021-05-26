package vacunasuy.nodoperifericobackend.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import vacunasuy.nodoperifericobackend.dto.EventoDTO;
import vacunasuy.nodoperifericobackend.entity.Evento;

public class EventoConverter extends AbstractConverter<Evento, EventoDTO> {

	@Override
	public EventoDTO fromEntity(Evento e) {
		if(e == null) return null;
		return EventoDTO.builder()
				.id(e.getId())
				.fecha(e.getFecha().toString())
				.cantidad(e.getCantidad())
				.detalle(e.getDetalle())
				.estado(e.getEstado())
				.idLote(e.getIdLote())
				.idTransportista(e.getIdTransportista())
				.idVacunatorio(e.getIdVacunatorio())
				.build();
	}

	@Override
	public Evento fromDTO(EventoDTO d) {
		if(d == null) return null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		return Evento.builder()
				.id(d.getId())
				.fecha(LocalDateTime.parse(d.getFecha(), formato))
				.cantidad(d.getCantidad())
				.detalle(d.getDetalle())
				.estado(d.getEstado())
				.idLote(d.getIdLote())
				.idTransportista(d.getIdTransportista())
				.idVacunatorio(d.getIdVacunatorio())
				.build();
	}
	
}
