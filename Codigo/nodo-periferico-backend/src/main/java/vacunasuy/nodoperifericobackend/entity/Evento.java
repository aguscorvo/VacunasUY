package vacunasuy.nodoperifericobackend.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "Eventos")
public class Evento {
	
	private Long id;
	private LocalDateTime fecha;
	private String detalle;
	private Long cantidad;
	private String estado;
	private Long idLote;
	private Long idTransportista;
	private Long idVacunatorio;

}
