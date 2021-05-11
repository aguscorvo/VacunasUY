package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.SectorLaboralDTO;
import vacunasuy.componentecentral.entity.SectorLaboral;

@Singleton
public class SectorLaboralConverter extends AbstractConverter<SectorLaboral, SectorLaboralDTO> {

	@Override
	public SectorLaboralDTO fromEntity(SectorLaboral e) {
		if(e == null) return null;
		return SectorLaboralDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.build();
	}

	@Override
	public SectorLaboral fromDTO(SectorLaboralDTO d) {
		if(d == null) return null;
		return SectorLaboral.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}
	
}
