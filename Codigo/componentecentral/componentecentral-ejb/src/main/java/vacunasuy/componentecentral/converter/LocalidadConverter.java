package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.LocalidadCrearDTO;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.entity.Localidad;

@Singleton
public class LocalidadConverter extends AbstractConverter<Localidad, LocalidadDTO>{
		
	@Override
	public LocalidadDTO fromEntity(Localidad l) {
		if(l==null) return null;
		return LocalidadDTO.builder()
				.id(l.getId())
				.nombre(l.getNombre())
				.build();
	}
	
	@Override
	public Localidad fromDTO(LocalidadDTO l) {
		return null;		
	}
	
	public Localidad fromCrearDTO(LocalidadCrearDTO l) {
		if(l==null) return null;
		return Localidad.builder()
				.nombre(l.getNombre())
				.build();
	}
	


}
