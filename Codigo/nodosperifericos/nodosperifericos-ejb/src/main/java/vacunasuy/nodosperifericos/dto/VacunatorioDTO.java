package vacunasuy.nodosperifericos.dto;

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
public class VacunatorioDTO {

	private Long id;
	private String nombre;
	private String direccion;
	private String clave;
	
}
