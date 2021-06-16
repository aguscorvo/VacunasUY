package vacunasuy.componentecentral.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.AgendaCrearDTO;
import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.dto.AgendaVacunatorioDTO;
import vacunasuy.componentecentral.entity.Agenda;

@Singleton
public class AgendaConverter extends AbstractConverter<Agenda, AgendaDTO>{
	
	@EJB
	private PuestoConverter puestoConverter;
	
	@EJB
	private PlanVacunacionConverter planVacunacionConverter;
	
	@Override
	public AgendaDTO fromEntity(Agenda a) {
		if(a==null) return null;
		return AgendaDTO.builder()
				.id(a.getId())
				.fecha(a.getFecha().toString())
				.puesto(puestoConverter.fromEntityToSinAgendas(a.getPuesto()))
				.planVacunacion(planVacunacionConverter.fromEntity(a.getPlanVacunacion()))
				.build();
	}
	
	@Override
	public Agenda fromDTO(AgendaDTO a) {
		return null;
	}

	public Agenda fromCrearDTO(AgendaCrearDTO a) {
		if(a==null) return null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return Agenda.builder()
				.fecha(LocalDateTime.parse(a.getFecha(), formato))
				.build();
	}
	
	public AgendaMinDTO fromEntityToMin(Agenda a) {
		if(a==null) return null;
		return AgendaMinDTO.builder()
				.id(a.getId())
				.fecha(a.getFecha().toString())
				.planVacunacion(planVacunacionConverter.fromEntity(a.getPlanVacunacion()))
				.build();
		
	} 
	
	public List<AgendaMinDTO> fromEntityToMin(List<Agenda> entities){
		if(entities == null) return null;
		return entities.stream()
			.map(e -> fromEntityToMin(e))
			.collect(Collectors.toList());
	}
	
	public AgendaVacunatorioDTO fromEntityToAgendaVacunatorio(Agenda a) {
		if(a == null) return null;
		return AgendaVacunatorioDTO.builder()
				.id(a.getId())
				.fecha(a.getFecha().toString())
				.idUsuario(a.getUsuario().getId())
				.documento(a.getUsuario().getDocumento())
				.idPuesto(a.getPuesto().getId())
				.idPlanVacunacion(a.getPlanVacunacion().getId())
				.nroDosis(a.getNroDosis())
				.build();
	}
	
}
