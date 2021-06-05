package vacunasuy.componentecentral.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.dto.PuestoSinAgendasDTO;
import vacunasuy.componentecentral.entity.Agenda;
import vacunasuy.componentecentral.entity.Puesto;

@Singleton
public class PuestoConverter extends AbstractConverter<Puesto, PuestoDTO>{

	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@Override
	public PuestoDTO fromEntity(Puesto p) {
		if (p== null) return null;
		return PuestoDTO.builder()
				.id(p.getId())
				.numero(p.getNumero())
				.vacunatorio(vacunatorioConverter.fromEntityToMin(p.getVacunatorio()))
				.agendas(agendaConverter.fromEntityToMin(p.getAgendas()))
				.build();
	}
	
	@Override
	public Puesto fromDTO(PuestoDTO p) {
		return null;
	}
	
	public Puesto fromCrearDTO(PuestoCrearDTO p) {
		if(p==null) return null;
		return Puesto.builder()
				.numero(p.getNumero())
				.build();
	}
	
	public PuestoMinDTO fromEntityToMin(Puesto p) {
		if(p==null) return null;
		return PuestoMinDTO.builder()
				.id(p.getId())
				.numero(p.getNumero())
				.agendas(agendaConverter.fromEntityToMin(p.getAgendas()))
				.build();
		
	} 
	
	public List<PuestoMinDTO> fromEntityToMin(List<Puesto> entities){
		if(entities == null) return null;
		return entities.stream()
			.map(e -> fromEntityToMin(e))
			.collect(Collectors.toList());
	}

	public PuestoSinAgendasDTO fromEntityToSinAgendas(Puesto p) {
		if(p==null) return null;
		return PuestoSinAgendasDTO.builder()
				.id(p.getId())
				.numero(p.getNumero())
				.vacunatorio(vacunatorioConverter.fromEntityToMin(p.getVacunatorio()))
				.build();		
	} 
	

}
