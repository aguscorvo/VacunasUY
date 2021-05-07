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
public class VacunatorioCrearDTO {

	private String nombre;
	private String latitud;
	private String longitud;
	private String direccion;
	private Long localidad;
	private Long departamento;
	private List<Long> puestos;
	
}
