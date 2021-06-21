package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.dto.UsuarioMinDTO;
import vacunasuy.componentecentral.entity.Usuario;

@Singleton
public class UsuarioConverter extends AbstractConverter<Usuario, UsuarioDTO>{

	@EJB
	private RolConverter rolConverter;
	
	@EJB
	private ActoVacunalConverter actoVacunalConverter;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@EJB
	private AtiendeConverter atiendeConverter;
	
	@EJB
	private SectorLaboralConverter sectorLaboralConverter;
	
	@Override
	public UsuarioDTO fromEntity(Usuario e) {
		if(e == null) return null;
		
		if(e.getFechaNacimiento() == null) {
			return UsuarioDTO.builder()
					.id(e.getId())
					.nombre(e.getNombre())
					.correo(e.getCorreo())
					.roles(rolConverter.fromEntity(e.getRoles()))
					.build();
		} else {
			return UsuarioDTO.builder()
					.id(e.getId())
					.documento(e.getDocumento())
					.nombre(e.getNombre())
					.apellido(e.getApellido())
					.correo(e.getCorreo())
					.fechaNacimiento(e.getFechaNacimiento().toString())
					.roles(rolConverter.fromEntity(e.getRoles()))
					.sectorLaboral(sectorLaboralConverter.fromEntity(e.getSectorLaboral()))
					.actosVacunales(actoVacunalConverter.fromEntity(e.getActosVacunales()))
					.agendas(agendaConverter.fromEntity(e.getAgendas()))
					.atiende(atiendeConverter.fromEntity(e.getAtiende()))
					.build();
		}
	}
	
	@Override
	public Usuario fromDTO(UsuarioDTO d) {
		return null;
	}
	
	public Usuario fromCrearDTO(UsuarioCrearDTO d) {
		if(d == null) return null;
		if(d.getFechaNacimiento() == null) {
			return Usuario.builder()
					.documento(d.getDocumento())
					.nombre(d.getNombre())
					.apellido(d.getApellido())
					.correo(d.getCorreo())
					.password(d.getPassword())
					.fechaNacimiento(null)
					.build();
		} else {
			return Usuario.builder()
					.documento(d.getDocumento())
					.nombre(d.getNombre())
					.apellido(d.getApellido())
					.correo(d.getCorreo())
					.password(d.getPassword())
					.fechaNacimiento(LocalDate.parse(d.getFechaNacimiento()))
					.build();
		}
		
	}
	
	public UsuarioLoginExitosoDTO fromLogin(Usuario e, String token) {
		if(e == null) return null;
		String fechaNacimiento = "";
		if(e.getFechaNacimiento() != null) {
			fechaNacimiento = e.getFechaNacimiento().toString();
		}
		return UsuarioLoginExitosoDTO.builder()
			.id(e.getId())
			.documento(e.getDocumento())
			.nombre(e.getNombre())
			.apellido(e.getApellido())
			.correo(e.getCorreo())
			.fechaNacimiento(fechaNacimiento)
			.roles(rolConverter.fromEntity(e.getRoles()))
			.sectorLaboral(sectorLaboralConverter.fromEntity(e.getSectorLaboral()))
			.token(token)
			.tokenFirebase(e.getTokenFirebase())
			.build();
	}
	
	public Usuario fromMinDTO(UsuarioMinDTO u) {
		if(u==null) return null;
		return Usuario.builder()
				.id(u.getId())
				.documento(u.getDocumento())
				.nombre(u.getNombre())
				.apellido(u.getApellido())
				.correo(u.getCorreo())
				.fechaNacimiento(LocalDate.parse(u.getFechaNacimiento()))
				.build();
	}
	
	public UsuarioMinDTO fromEntityToMin(Usuario u) {
		if(u == null) return null;
		return UsuarioMinDTO.builder()
				.id(u.getId())
				.documento(u.getDocumento())
				.nombre(u.getNombre())
				.apellido(u.getApellido())
				.correo(u.getCorreo())
				.fechaNacimiento(u.getFechaNacimiento().toString())
				.build();
	}

}
