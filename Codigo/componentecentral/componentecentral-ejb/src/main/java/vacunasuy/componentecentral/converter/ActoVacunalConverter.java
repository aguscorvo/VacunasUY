package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.ActoVacunalCrearDTO;
import vacunasuy.componentecentral.dto.ActoVacunalDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;

@Singleton
public class ActoVacunalConverter extends AbstractConverter<ActoVacunal, ActoVacunalDTO>{

//	@EJB
//	private PlanVacunacionConverter planVacunacionConverter;
	
	@Override
	public ActoVacunalDTO fromEntity(ActoVacunal a) {
		if(a==null) return null;
		return ActoVacunalDTO.builder()
				.id(a.getId())
				.fecha(a.getFecha().toString())
//				.planVacunacion(planVacunacionConverter.fromEntity(a.getPlanVacunacion()))
				.build();
	}
	
	@Override
	public ActoVacunal fromDTO(ActoVacunalDTO a) {
		return null;
	}
	
	public ActoVacunal fromCrearDTO(ActoVacunalCrearDTO a) {
		if(a==null) return null;
		return ActoVacunal.builder()
				.fecha(LocalDate.parse(a.getFecha()))
//				.planVacunacion(planVacunacionConverter.fromCrearDTO(a.getPlanVacunacion()))
				.build();
	}
	
}
