package vacunasuy.nodosexternosbackend.entity;

import java.time.LocalDate;

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
public class Persona {

	private String cedula;	
	private LocalDate fechaDeNacimiento;
	private String sectorLaboral;
		
}
