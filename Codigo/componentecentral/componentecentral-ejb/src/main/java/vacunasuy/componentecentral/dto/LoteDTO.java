package vacunasuy.componentecentral.dto;

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
public class LoteDTO {

	private Long id;
	private Long cantidad;
	private ProveedorDTO proveedor;
	private VacunaDTO vacuna;
	
}
