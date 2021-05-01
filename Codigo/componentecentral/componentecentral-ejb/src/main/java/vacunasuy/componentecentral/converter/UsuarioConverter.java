package vacunasuy.componentecentral.converter;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.entity.Administrador;
import vacunasuy.componentecentral.entity.Autoridad;
import vacunasuy.componentecentral.entity.Usuario;

@Singleton
public class UsuarioConverter extends AbstractConverter<Usuario, UsuarioDTO>{

	@Override
	public UsuarioDTO fromEntity(Usuario e) {
		if(e == null) return null;
		return UsuarioDTO.builder()
				.id(e.getId())
				.nombre(e.getNombre())
				.apellido(e.getApellido())
				.correo(e.getCorreo())
				.build();
	}
	
	@Override
	public Usuario fromDTO(UsuarioDTO d) {
		return null;
	}
	
	public Usuario fromCrearDTO(UsuarioCrearDTO d) {
		if(d == null) return null;
		if( d.getRol().equalsIgnoreCase("Administrador")) {
			return Administrador.builder()
				.nombre(d.getNombre())
				.apellido(d.getApellido())
				.correo(d.getCorreo())
				.password(d.getPassword())
				.build();
		}else {
			return Autoridad.builder()
					.nombre(d.getNombre())
					.apellido(d.getApellido())
					.correo(d.getCorreo())
					.password(d.getPassword())
					.build();
		}
	}

}
