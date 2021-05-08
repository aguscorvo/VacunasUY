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
public class VacunaDTO {
	
	private Long id;
	private String nombre;
	private int cant_dosis;
	private int periodo;
	private int inmunidad;
	private EnfermedadDTO enfermedad;
	
}
