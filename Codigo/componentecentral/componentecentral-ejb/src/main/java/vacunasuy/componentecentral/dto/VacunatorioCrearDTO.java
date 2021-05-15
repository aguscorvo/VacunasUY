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
public class VacunatorioCrearDTO {

	private String nombre;
	private Double latitud;
	private Double longitud;
	private String direccion;
	private Long localidad;
	private Long departamento;
	
}
