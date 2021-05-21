package vacunasuy.componentecentral.dto;

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
public class AgendaVacunatorioDTO {

	private Long id;
	private String fecha;
	private Long idPuesto;
	private Long idPlanVacunacion;
	
}
