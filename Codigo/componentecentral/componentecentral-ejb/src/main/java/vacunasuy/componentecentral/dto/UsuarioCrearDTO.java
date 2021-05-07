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
public class UsuarioCrearDTO {

	private String documento;
	private String nombre;
	private String apellido;
	private String correo;
	private String fechaNacimiento;
	private String password;
	private List<Long> roles;
	
}
