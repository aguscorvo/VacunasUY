package vacunasuy.componentecentral.dto;

import java.util.List;

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
public class PuestoMinDTO {

	private Long id;
	private int numero;
	private List<AgendaMinDTO> agendas;	
}
