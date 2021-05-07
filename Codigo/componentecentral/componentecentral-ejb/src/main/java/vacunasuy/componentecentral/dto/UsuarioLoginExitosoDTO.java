package vacunasuy.componentecentral.dto;

import lombok.Builder;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioLoginExitosoDTO {

	private Long id;
	private String documento;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private List<RolDTO> roles;
	private String token;
	
}
