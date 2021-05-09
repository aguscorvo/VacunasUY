package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.entity.Pais;

@Singleton
public class PaisConverter extends AbstractConverter<Pais, PaisDTO>{

	@Override
	public PaisDTO fromEntity(Pais p) {
		if(p==null) return null;
		return PaisDTO.builder()
				.id(p.getId())
				.nombre(p.getNombre())
				.build();
	}
	
	@Override
	public Pais fromDTO(PaisDTO p) {
		if(p==null) return null;
		return Pais.builder()
				.id(p.getId())
				.nombre(p.getNombre())
				.build();
	}
	
}
