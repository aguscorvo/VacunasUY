package vacunasuy.nodosexternosbackend.converter;

import java.time.LocalDate;

import vacunasuy.nodosexternosbackend.dto.PersonaDTO;
import vacunasuy.nodosexternosbackend.entity.Persona;

public class PersonaConverter extends AbstractConverter<Persona, PersonaDTO> {

	@Override
	public PersonaDTO fromEntity(Persona p) {
		if (p==null) return null;
		return PersonaDTO.builder()
				.cedula(p.getCedula())
				.fechaDeNacimiento(p.getFechaDeNacimiento().toString())
				.sectorLaboral(p.getSectorLaboral())
				.build();
	}
	
	@Override
	public Persona fromDTO(PersonaDTO p) {
		if (p==null) return null;
		return Persona.builder()
				.cedula(p.getCedula())
				.fechaDeNacimiento(LocalDate.parse(p.getFechaDeNacimiento()))
				.build();
	}
	

	
}
