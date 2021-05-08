package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.entity.Agenda;

@Singleton
public class AgendaConverter extends AbstractConverter<Agenda, AgendaDTO>{
	
	@EJB
	private PuestoConverter puestoConverter;
	
	@Override
	public AgendaDTO fromEntity(Agenda a) {
		if(a==null) return null;
		return AgendaDTO.builder()
				.id(a.getId())
				.fecha(a.getFecha().toString())
				.puesto(puestoConverter.fromEntity(a.getPuesto()))
				.build();
	}
	
	@Override
	public Agenda fromDTO(AgendaDTO a) {
		return null;
	}

	public Agenda fromCrearDTO(AgendaCrearDTO a) {
		if(a==null) return null;
		return Agenda.builder()
				.fecha(LocalDate.parse(a.getFecha()))
				.build();
	}
}
