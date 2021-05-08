package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.entity.Enfermedad;

@Singleton
public class EnfermedadConverter extends AbstractConverter<Enfermedad, EnfermedadDTO>{

	
	@Override
	public EnfermedadDTO fromEntity(Enfermedad e) {
		if(e == null) return null;
		return EnfermedadDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.build();
	}
	
	@Override
	public Enfermedad fromDTO(EnfermedadDTO d) {
		return null;
	}
	
	public Enfermedad fromCrearDTO(EnfermedadCrearDTO d) {
		if(d == null) return null;
		return Enfermedad.builder()
			.nombre(d.getNombre())
			.build();
	}
	

}
