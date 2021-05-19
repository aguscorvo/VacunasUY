package vacunasuy.nodosperifericos.converter;

import java.time.LocalDate;
import javax.ejb.Singleton;
import vacunasuy.nodosperifericos.dto.VacunadorDTO;
import vacunasuy.nodosperifericos.entity.Vacunador;

@Singleton
public class VacunadorConverter extends AbstractConverter<Vacunador, VacunadorDTO> {

	@Override
	public VacunadorDTO fromEntity(Vacunador e) {
		if(e == null) return null;
		return VacunadorDTO.builder()
				.id(e.getId())
				.documento(e.getDocumento())
				.nombre(e.getNombre())
				.apellido(e.getApellido())
				.correo(e.getCorreo())
				.fechaNacimiento(e.getFechaNacimiento().toString())
				.fechaAsignacion(e.getFechaAsignacion().toString())
				.idVacunatorio(e.getIdVacunatorio())
				.build();
	}

	@Override
	public Vacunador fromDTO(VacunadorDTO d) {
		if(d == null) return null;
		return Vacunador.builder()
				.id(d.getId())
				.documento(d.getDocumento())
				.nombre(d.getNombre())
				.apellido(d.getApellido())
				.correo(d.getCorreo())
				.fechaNacimiento(LocalDate.parse(d.getFechaNacimiento()))
				.build();
	}
	
}
