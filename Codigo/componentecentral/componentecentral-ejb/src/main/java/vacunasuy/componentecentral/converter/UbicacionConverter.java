package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.entity.Ubicacion;

@Singleton
public class UbicacionConverter extends AbstractConverter<Ubicacion, UbicacionDTO>{
	
	@Override
	public UbicacionDTO fromEntity(Ubicacion u) {
		if (u==null) return null;
		return UbicacionDTO.builder()
				.latitud(u.getLatitud())
				.longitud(u.getLongitud())
				.distancia(u.getDistancia())
				.build();
	}
	
	@Override
	public Ubicacion fromDTO(UbicacionDTO u) {
		if(u==null) return null;
		return Ubicacion.builder()
				.latitud(u.getLatitud())
				.longitud(u.getLongitud())
				.distancia(u.getDistancia())
				.build();
	}

}
