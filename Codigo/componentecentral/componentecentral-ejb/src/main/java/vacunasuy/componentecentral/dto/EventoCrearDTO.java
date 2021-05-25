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
public class EventoCrearDTO {
	
	private String fecha;
	private String detalle;
	private Long cantidad;
	private String estado;
	private Long idLote;
	private Long idTransportista;
	private Long idVacunatorio;

}
