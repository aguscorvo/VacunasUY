package vacunasuy.componentecentral.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.PlanVacunacionCrearDTO;
import vacunasuy.componentecentral.dto.PlanVacunacionDTO;
import vacunasuy.componentecentral.entity.PlanVacunacion;

@Singleton
public class PlanVacunacionConverter extends AbstractConverter<PlanVacunacion, PlanVacunacionDTO> {

	@EJB
	private SectorLaboralConverter sectorLaboralConverter;
	
	@EJB
	private VacunaConverter vacunaConverter;
	
	@EJB
	private AgendaConverter agendaConverter;
	
	@Override
	public PlanVacunacionDTO fromEntity(PlanVacunacion e) {
		if(e == null) return null;
		return PlanVacunacionDTO.builder()
				.id(e.getId())
				.edadMaxima(e.getEdadMaxima())
				.edadMinima(e.getEdadMinima())
				.fechaInicio(e.getFechaInicio().toString())
				.fechaFin(e.getFechaFin().toString())
				.sectores(sectorLaboralConverter.fromEntity(e.getSectores()))
				.vacuna(vacunaConverter.fromEntity(e.getVacuna()))
				.build();
	}

	@Override
	public PlanVacunacion fromDTO(PlanVacunacionDTO d) {
		if(d == null) return null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return PlanVacunacion.builder()
				.id(d.getId())
				.edadMaxima(d.getEdadMaxima())
				.edadMinima(d.getEdadMinima())
				.fechaInicio(LocalDateTime.parse(d.getFechaInicio(), formato))
				.fechaFin(LocalDateTime.parse(d.getFechaFin(), formato))
				.sectores(sectorLaboralConverter.fromDTO(d.getSectores()))
				.vacuna(vacunaConverter.fromDTO(d.getVacuna()))
				.build();
	}
	
	public PlanVacunacion fromCrearDTO(PlanVacunacionCrearDTO d) {
		if(d == null) return null;
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return PlanVacunacion.builder()
				.edadMaxima(d.getEdadMaxima())
				.edadMinima(d.getEdadMinima())
				.fechaInicio(LocalDateTime.parse(d.getFechaInicio(), formato))
				.fechaFin(LocalDateTime.parse(d.getFechaFin(), formato))
				.build();
	}

	
}
