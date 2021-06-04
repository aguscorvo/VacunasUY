package vacunasuy.componentecentral.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.VacunaCrearDTO;
import vacunasuy.componentecentral.dto.VacunaDTO;
import vacunasuy.componentecentral.dto.VacunaMinDTO;
import vacunasuy.componentecentral.entity.Vacuna;

@Singleton
public class VacunaConverter extends AbstractConverter<Vacuna, VacunaDTO>{

	@EJB
	EnfermedadConverter eConverter;
	
	@Override
	public VacunaDTO fromEntity(Vacuna v) {
		if(v == null) return null;
		return VacunaDTO.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.cant_dosis(v.getCant_dosis())
				.periodo(v.getPeriodo())
				.inmunidad(v.getInmunidad())
				.enfermedad(eConverter.fromEntity(v.getEnfermedad()))
				.build();
	}

	@Override
	public Vacuna fromDTO(VacunaDTO d) {
		if(d==null) return null;
		return Vacuna.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.cant_dosis(d.getCant_dosis())
				.periodo(d.getPeriodo())
				.inmunidad(d.getInmunidad())
				.enfermedad(eConverter.fromDTO(d.getEnfermedad()))
				.build();
				
	}
	
	public Vacuna fromCrearDTO(VacunaCrearDTO d) {
		if(d == null) return null;
		return Vacuna.builder()
			.nombre(d.getNombre())
			.cant_dosis(d.getCant_dosis())
			.periodo(d.getPeriodo())
			.inmunidad(d.getInmunidad())
			.build();
	}
	
	public VacunaMinDTO fromEntityToMin(Vacuna e) {
		if(e == null) return null;
		return VacunaMinDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.cant_dosis(e.getCant_dosis())
				.periodo(e.getPeriodo())
				.inmunidad(e.getInmunidad())
				.build();
	}
	
	public List<VacunaMinDTO> fromEntityToMin(List<Vacuna> entities){
		if(entities == null) return null;
		return entities.stream()
			.map(e -> fromEntityToMin(e))
			.collect(Collectors.toList());
	}
	
}
