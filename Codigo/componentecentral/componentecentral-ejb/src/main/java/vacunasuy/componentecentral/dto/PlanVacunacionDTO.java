package vacunasuy.componentecentral.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanVacunacionDTO {

	private Long id;
	private int edadMinima;
	private int edadMaxima;
	private String fechaInicio;
	private String fechaFin;
	private List<SectorLaboralDTO> sectores;
	private VacunaDTO vacuna;
//	private List<AgendaDTO> agendas;
	
}
