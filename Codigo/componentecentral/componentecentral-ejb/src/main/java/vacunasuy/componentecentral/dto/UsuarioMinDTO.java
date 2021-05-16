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
public class UsuarioMinDTO {

	private Long id;
	private String documento;
	private String nombre;
	private String apellido;
	private String correo;
	private String fechaNacimiento;
	
}
