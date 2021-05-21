package vacunasuy.nodoperifericobackend.converter;

import java.time.LocalDate;
import vacunasuy.nodoperifericobackend.dto.VacunadorDTO;
import vacunasuy.nodoperifericobackend.entity.Vacunador;
import vacunasuy.nodoperifericobackend.entity.Vacunador.CompositeKey;

public class VacunadorConverter extends AbstractConverter<Vacunador, VacunadorDTO>{

	@Override
	public VacunadorDTO fromEntity(Vacunador e) {
		if(e == null) return null;
		return VacunadorDTO.builder()
				.id(e.getId().getId())
				.fechaAsignacion(e.getFechaNacimiento().toString())
				.idVacunatorio(e.getId().getIdVacunatorio())
				.documento(e.getDocumento())
				.nombre(e.getNombre())
				.apellido(e.getApellido())
				.correo(e.getCorreo())
				.fechaNacimiento(e.getFechaNacimiento().toString())
				.build();
	}

	@Override
	public Vacunador fromDTO(VacunadorDTO d) {
		if(d == null) return null;
		return Vacunador.builder()
				.documento(d.getDocumento())
				.nombre(d.getNombre())
				.apellido(d.getApellido())
				.correo(d.getCorreo())
				.fechaNacimiento(LocalDate.parse(d.getFechaNacimiento()))
				.build();
	}

}
