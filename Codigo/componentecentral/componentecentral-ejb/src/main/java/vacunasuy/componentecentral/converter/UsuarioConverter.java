package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.entity.Usuario;

@Singleton
public class UsuarioConverter extends AbstractConverter<Usuario, UsuarioDTO>{

	@EJB
	private RolConverter rolConverter;
	
	@Override
	public UsuarioDTO fromEntity(Usuario e) {
		if(e == null) return null;
		return UsuarioDTO.builder()
				.id(e.getId())
				.documento(e.getDocumento())
				.nombre(e.getNombre())
				.apellido(e.getApellido())
				.correo(e.getCorreo())
				.fechaNacimiento(e.getFechaNacimiento().toString())
				.roles(rolConverter.fromEntity(e.getRoles()))
				.build();
	}
	
	@Override
	public Usuario fromDTO(UsuarioDTO d) {
		return null;
	}
	
	public Usuario fromCrearDTO(UsuarioCrearDTO d) {
		if(d == null) return null;
		return Usuario.builder()
			.documento(d.getDocumento())
			.nombre(d.getNombre())
			.apellido(d.getApellido())
			.correo(d.getCorreo())
			.password(d.getPassword())
			.fechaNacimiento(LocalDate.parse(d.getFechaNacimiento()))
			.build();
	}
	
	public UsuarioLoginExitosoDTO fromLogin(Usuario e, String token) {
		if(e == null) return null;
		return UsuarioLoginExitosoDTO.builder()
			.id(e.getId())
			.documento(e.getDocumento())
			.nombre(e.getNombre())
			.apellido(e.getApellido())
			.correo(e.getCorreo())
			//.fechaNacimiento(e.getFechaNacimiento().toString())
			.roles(rolConverter.fromEntity(e.getRoles()))
			.token(token)
			.build();
	}

}
