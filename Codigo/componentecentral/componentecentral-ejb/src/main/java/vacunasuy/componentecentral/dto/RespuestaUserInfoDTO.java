package vacunasuy.componentecentral.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaUserInfoDTO {
	
	private String numero_documento;
	private String primer_nombre;
	private String primer_apellido;
	private String email;

}
