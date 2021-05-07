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
public class PuestoDTO {
	
	private Long id;
	private int numero;
//	private VacunatorioDTO vacunatorio;
//	private List<AgendaDTO> agendas;
	

}
