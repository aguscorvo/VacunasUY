package vacunasuy.componentecentral.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {
	
	private Long id;
	private String fecha;
	private PuestoDTO puesto;
	private PlanVacunacionDTO planVacunacion;

}
