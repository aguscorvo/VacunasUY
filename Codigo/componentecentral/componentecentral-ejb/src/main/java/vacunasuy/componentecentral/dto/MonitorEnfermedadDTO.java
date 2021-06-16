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
public class MonitorEnfermedadDTO {

	private Long cantidadAgendasHoy;
	private Long cantidadVacunadosHoy;
	private List<MonitorEnfermedadVacunasDTO> vacunas;
	private List<MonitorEnfermedadPlanesDTO> planes;
	
}
