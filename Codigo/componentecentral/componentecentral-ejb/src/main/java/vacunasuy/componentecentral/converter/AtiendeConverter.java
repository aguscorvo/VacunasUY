package vacunasuy.componentecentral.converter;

import java.time.LocalDate;

import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.entity.Atiende;

@Singleton
public class AtiendeConverter extends AbstractConverter<Atiende, AtiendeCrearDTO>{

	@Override
	public AtiendeCrearDTO fromEntity(Atiende a) {
		return null;
	}
	
	@Override
	public Atiende fromDTO(AtiendeCrearDTO a) {
		if(a==null)return null;
		return Atiende.builder()
				.fecha(LocalDate.parse(a.getFecha()))
				.build();				
	}
	
}
