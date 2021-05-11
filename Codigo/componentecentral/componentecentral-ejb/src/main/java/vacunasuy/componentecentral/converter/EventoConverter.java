package vacunasuy.componentecentral.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.EventoCrearDTO;
import vacunasuy.componentecentral.dto.EventoDTO;
import vacunasuy.componentecentral.entity.Evento;

@Singleton
public class EventoConverter extends AbstractConverter<Evento, EventoDTO>{

	@EJB
	private LoteConverter loteConverter;
	
	@EJB
	private TransportistaConverter transportistaConverter;
	
	@Override
	public EventoDTO fromEntity(Evento e) {
		if(e == null) return null;
		return EventoDTO.builder()
				.id(e.getId())
				.fecha(e.getFecha().toString())
				.detalle(e.getDetalle())
				.cantidad(e.getCantidad())
				.lote(loteConverter.fromEntity(e.getLote()))
				.transportista(transportistaConverter.fromEntity(e.getTransportista()))
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
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return Evento.builder()
				.fecha(LocalDateTime.parse(d.getFecha(), formato))
				.detalle(d.getDetalle())
				.cantidad(d.getCantidad())
				.build();
	}

}
