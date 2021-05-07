package vacunasuy.componentecentral.converter;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.PuestoCrearDTO;
import vacunasuy.componentecentral.dto.PuestoDTO;
import vacunasuy.componentecentral.entity.Puesto;

@Singleton
public class PuestoConverter extends AbstractConverter<Puesto, PuestoDTO>{

	@EJB
	private VacunatorioConverter vacunatorioConverter;
	
//	@EJB
//	private AgendaConverter agendaConverter;
	
	@Override
	public PuestoDTO fromEntity(Puesto p) {
		if (p== null) return null;
		return PuestoDTO.builder()
				.id(p.getId())
				.numero(p.getNumero())
//				.vacunatorio(vacunatorioConverter.fromEntity(p.getVacunatorio()))
//				.agendas(agendaConverter.fromEntity(p.getAgendas()))
				.build();
	}
	
	@Override
	public Puesto fromDTO(PuestoDTO p) {
		if(p==null) return null;
		return Puesto.builder()
				.id(p.getId())
				.numero(p.getNumero())
//				.vacunatorio(vacunatorioConverter.fromDTO(p.getVacunatorio()))
//				.agendas(agendaConverter.fromDTO(p.getAgendas()))
				.build();
	}
	
	public Puesto fromCrearDTO(PuestoCrearDTO p) {
		if(p==null) return null;
		return Puesto.builder()
				.numero(p.getNumero())
				.build();
	}
	
   

}