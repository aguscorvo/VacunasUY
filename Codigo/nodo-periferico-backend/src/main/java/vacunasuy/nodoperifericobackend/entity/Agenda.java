package vacunasuy.nodoperifericobackend.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "Agendas")
public class Agenda {

	@Id
    private Long id;
    private LocalDateTime fecha;
    private Long idUsuario;
    private String documento;
    private Long idPuesto;
    private Long idPlanVacunacion;
    private Long idVacunatorio;
    private Boolean vacunado;
	
}
