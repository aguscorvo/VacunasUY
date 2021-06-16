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
public class MonitorVacunaDTO {

	private List<MonitorVacunaDosisDTO> agendas;
	private List<MonitorVacunaDosisDTO> vacunados;
	
}
