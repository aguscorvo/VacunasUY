package vacunasuy.componentecentral.dto;

import lombok.Builder;
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
	private String nombre;
	private String apellido;
	private String token;
	
}
