package vacunasuy.nodoperifericobackend.entity;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Vacunadores")
public class Vacunador {
	
	@Id
	private CompositeKey id;
	
	private String documento;
	private String nombre;
	private String apellido;
	private String correo;
	private LocalDate fechaNacimiento;
	
	@Value
	static public class CompositeKey implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Long id;
		private LocalDate fechaAsignacion;
		private Long idVacunatorio;
	}

}
