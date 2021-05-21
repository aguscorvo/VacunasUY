package vacunasuy.nodoperifericobackend.converter;

import vacunasuy.nodoperifericobackend.dto.VacunatorioDTO;
import vacunasuy.nodoperifericobackend.entity.Vacunatorio;

public class VacunatorioConverter extends AbstractConverter<Vacunatorio, VacunatorioDTO> {
	
	@Override
	public VacunatorioDTO fromEntity(Vacunatorio v) {
		if(v==null) return null;
		return VacunatorioDTO.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.direccion(v.getDireccion())
				.clave(v.getClave())
				.build();								
	}
	
	@Override
	public Vacunatorio fromDTO(VacunatorioDTO v) {
		if(v==null) return null;
		return Vacunatorio.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.direccion(v.getDireccion())
				.clave(v.getClave())
				.build();
	}

}
