package vacunasuy.nodosexternosbackend.dto;

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
public class PersonaDTO {

	private String cedula;	
	private String fechaDeNacimiento;
	private String sectorLaboral;
	
}
