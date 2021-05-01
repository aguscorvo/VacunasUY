package vacunasuy.componentecentral.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "usuarios_administradores")
@DiscriminatorValue(value="Administrador")
public class Administrador extends Usuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Builder
	public Administrador(Long id, String nombre, String apellido, String correo, String password) {
		super(id, nombre, apellido, correo);
		this.password = password;
	}
	
}
