package vacunasuy.nodoperifericobackend.dto;

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
public class ActoVacunalDTO {

	private String fecha;
	private Long planVacunacion;
	private Long usuario;
	private Long idVacunatorio;
	private int nroDosis;
	
}
