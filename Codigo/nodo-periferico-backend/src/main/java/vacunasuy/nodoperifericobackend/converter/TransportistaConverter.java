package vacunasuy.nodoperifericobackend.converter;

import vacunasuy.nodoperifericobackend.dto.TransportistaDTO;
import vacunasuy.nodoperifericobackend.entity.Transportista;

public class TransportistaConverter extends AbstractConverter<Transportista, TransportistaDTO> {

	@Override
	public TransportistaDTO fromEntity(Transportista e) {
		if(e == null) return null;
		return TransportistaDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.build();
	}

	@Override
	public Transportista fromDTO(TransportistaDTO d) {
		if(d == null) return null;
		return Transportista.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}

}
