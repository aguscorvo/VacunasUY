package vacunasuy.componentecentral.converter;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.VacunatorioGeoDTO;
import vacunasuy.componentecentral.entity.VacunatorioGeo;

@Singleton
public class VacunatorioGeoConverter extends AbstractConverter<VacunatorioGeo, VacunatorioGeoDTO> {

	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
	@Override
	public VacunatorioGeoDTO fromEntity(VacunatorioGeo v) {
		if (v==null) return null;
		return VacunatorioGeoDTO.builder()
				.id(v.getId())
				.vacunatorio(vacunatorioConverter.fromEntity(v.getVacunatorio()))
				.build();
	}
	
	@Override
	public VacunatorioGeo fromDTO(VacunatorioGeoDTO v) {
		if (v==null) return null;
		return VacunatorioGeo.builder()
				.id(v.getId())
				.vacunatorio(vacunatorioConverter.fromDTO(v.getVacunatorio()))
				.build();
	}
	
	
}
