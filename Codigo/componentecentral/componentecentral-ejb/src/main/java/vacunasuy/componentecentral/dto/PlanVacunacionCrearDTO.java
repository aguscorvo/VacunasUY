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
public class PlanVacunacionCrearDTO {

	private int edadMinima;
	private int edadMaxima;
	private String fechaInicio;
	private String fechaFin;
	private List<Long> sectores;
	private Long idVacuna;
	
}
