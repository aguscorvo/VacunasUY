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
public class EventoCrearDTO {
	
	private String fecha;
	private String detalle;
	private Long cantidad;
	private String estado;
	private Long idLote;
	private Long idTransportista;
	private Long idVacunatorio;

}
