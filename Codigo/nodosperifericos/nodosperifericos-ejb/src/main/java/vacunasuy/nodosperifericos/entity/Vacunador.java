package vacunasuy.nodosperifericos.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
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
@Entity
@IdClass(VacunadorID.class)
@Table(name = "vacunadores")
public class Vacunador {

	@Id
	private Long id;
	@Id
	private LocalDate fechaAsignacion;
	@Id
	private Long idVacunatorio;
	@Column(name = "documento", nullable = false, length = 50)
	private String documento;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	@Column(name = "apellido", nullable = false, length = 50)
	private String apellido;
	@Column(name = "correo", length = 100)
	private String correo;
	@Column(name = "fechaNacimiento")
	private LocalDate fechaNacimiento;
	
}
