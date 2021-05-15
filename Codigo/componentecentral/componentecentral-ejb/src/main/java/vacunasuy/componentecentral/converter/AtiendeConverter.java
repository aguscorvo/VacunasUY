package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.entity.Atiende;

@Singleton
public class AtiendeConverter extends AbstractConverter<Atiende, AtiendeDTO>{

	@EJB
	private PuestoConverter puestoConverter;
	
	@Override
	public AtiendeDTO fromEntity(Atiende a) {
		if(a==null)return null;
		return AtiendeDTO.builder()
				.puesto(puestoConverter.fromEntity(a.getPuesto()))
				.fecha(a.getFecha().toString())
				.build();			
	}
	
	@Override
	public Atiende fromDTO(AtiendeDTO a) {
		if(a==null)return null;
		return Atiende.builder()
				.puesto(puestoConverter.fromDTO(a.getPuesto()))
				.fecha(LocalDate.parse(a.getFecha()))
				.build();				
	}
	
	public Atiende fromCrearDTO(AtiendeCrearDTO a) {
		if(a==null)return null;
		return Atiende.builder()
				.fecha(LocalDate.parse(a.getFecha()))
				.build();
	}
	
}
