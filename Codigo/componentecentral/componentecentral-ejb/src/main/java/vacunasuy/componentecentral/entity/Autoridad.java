package vacunasuy.componentecentral.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@Entity
@Table(name = "usuarios_autoridades")
@DiscriminatorValue(value="Autoridad")
public class Autoridad extends Usuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	@Builder
	public Autoridad(Long id, String nombre, String apellido, String correo, String password) {
		super(id, nombre, apellido, correo);
		this.password = password;
	}

}
