package vacunasuy.nodoperifericobackend.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import vacunasuy.nodoperifericobackend.dto.AgendaDTO;
import vacunasuy.nodoperifericobackend.entity.Agenda;

public class AgendaConverter extends AbstractConverter<Agenda, AgendaDTO>{

	@Override
	public AgendaDTO fromEntity(Agenda e) {
		if(e == null) return null;
		return AgendaDTO.builder()
				.id(e.getId())
				.fecha(e.getFecha().toString())
				.idPuesto(e.getIdPuesto())
				.idPlanVacunacion(e.getIdPlanVacunacion())
				.nroDosis(e.getNroDosis())
				.build();
	}

	@Override
	public Agenda fromDTO(AgendaDTO d) {
		if(d == null) return null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		return Agenda.builder()
				.id(d.getId())
				.fecha(LocalDateTime.parse(d.getFecha(), formato))
				.idUsuario(d.getIdUsuario())
				.documento(d.getDocumento())
				.idPuesto(d.getIdPuesto())
				.idPlanVacunacion(d.getIdPlanVacunacion())
				.nroDosis(d.getNroDosis())
				.build();
	}
	
}
