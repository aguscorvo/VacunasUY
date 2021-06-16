package vacunasuy.componentecentral.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorPlanDTO {

	private Long cantidadAgendasHoy;
	private Long cantidadVacunadosHoy;
	private Long cantidadTotalVacunados;
	private PlanVacunacionDTO plan;
	
}
