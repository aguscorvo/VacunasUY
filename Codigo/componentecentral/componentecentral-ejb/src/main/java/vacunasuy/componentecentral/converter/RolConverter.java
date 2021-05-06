package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.entity.Rol;

@Singleton
public class RolConverter extends AbstractConverter<Rol, RolDTO>{

	@Override
	public RolDTO fromEntity(Rol e) {
		if(e == null) return null;
		return RolDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.build();
	}

	@Override
	public Rol fromDTO(RolDTO d) {
		if(d == null) return null;
		return Rol.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}

}
