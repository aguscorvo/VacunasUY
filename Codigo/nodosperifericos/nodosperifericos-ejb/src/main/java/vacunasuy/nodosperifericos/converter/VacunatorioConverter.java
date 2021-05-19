package vacunasuy.nodosperifericos.converter;

import javax.ejb.Singleton;
import vacunasuy.nodosperifericos.dto.VacunatorioDTO;
import vacunasuy.nodosperifericos.entity.Vacunatorio;

@Singleton
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
