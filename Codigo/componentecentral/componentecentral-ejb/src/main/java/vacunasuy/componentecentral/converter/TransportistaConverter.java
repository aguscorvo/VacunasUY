package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.entity.Transportista;

@Singleton
public class TransportistaConverter extends AbstractConverter<Transportista, TransportistaDTO> {

	@Override
	public TransportistaDTO fromEntity(Transportista t) {
		if(t==null) return null;
		return TransportistaDTO.builder()
				.id(t.getId())
				.nombre(t.getNombre())
				.build();
	}
	
	@Override
	public Transportista fromDTO(TransportistaDTO t) {
		return null;
	}
	
	public Transportista fromCrearDTO(TransportistaCrearDTO t) {
		if(t==null) return null;
		return Transportista.builder()
				.nombre(t.getNombre())
				.build();
	}
	
}
