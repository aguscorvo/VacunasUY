package vacunasuy.componentecentral.converter;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.DepartamentoCrearDTO;
import vacunasuy.componentecentral.dto.DepartamentoDTO;
import vacunasuy.componentecentral.dto.DepartamentoMinDTO;
import vacunasuy.componentecentral.entity.Departamento;

@Singleton
public class DepartamentoConverter extends AbstractConverter<Departamento, DepartamentoDTO> {

	@EJB
	private LocalidadConverter localidadConverter;
	
	@Override
	public DepartamentoDTO fromEntity(Departamento d) {
		if(d == null) return null;
		return DepartamentoDTO.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.localidades(localidadConverter.fromEntity(d.getLocalidades()))
				.build();
	}
	
	@Override
	public Departamento fromDTO(DepartamentoDTO d) {
		return null;
	}
	
	public Departamento fromCrearDTO(DepartamentoCrearDTO d) {
		if(d == null) return null;
		return Departamento.builder()
				.nombre(d.getNombre())
				.build();
	}
	
	public Departamento fromMinDTO (DepartamentoMinDTO d) {
		if(d == null) return null;
		return Departamento.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}
	
	public DepartamentoMinDTO fromEntityToMin (Departamento d) {
		if(d ==null) return null;
		return DepartamentoMinDTO.builder()
				.id(d.getId())
				.nombre(d.getNombre())
				.build();
	}
}
