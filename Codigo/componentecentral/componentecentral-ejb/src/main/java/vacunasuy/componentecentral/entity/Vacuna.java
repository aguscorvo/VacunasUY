package vacunasuy.componentecentral.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "vacunas")
public class Vacuna {
	
	/* Atributos */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "cant_dosis")
	private int cant_dosis;
	
	@Column(name = "periodo")
	private int periodo;
	
	@Column(name = "inmunidad")
	private int inmunidad;
	
	@OneToOne
	private Enfermedad enfermedad;

	/* Métodos generados por Lombok */
}











