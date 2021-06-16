package vacunasuy.nodoperifericobackend.dto;

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
public class AgendaDTO {
	
	private Long id;
    private String fecha;
    private Long idUsuario;
    private String documento;
    private Long idPuesto;
    private Long idPlanVacunacion;
    private int nroDosis;

}
