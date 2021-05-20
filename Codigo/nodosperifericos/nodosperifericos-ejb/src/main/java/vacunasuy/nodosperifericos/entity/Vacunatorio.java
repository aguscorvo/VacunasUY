package vacunasuy.nodosperifericos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "vacunatorios")
public class Vacunatorio {

	@Id
	private Long id;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	@Column(name = "direccion", nullable = false, length = 150)
	private String direccion;
	@Column(name = "clave", nullable = false, length = 100)
	private String clave;
	
}
