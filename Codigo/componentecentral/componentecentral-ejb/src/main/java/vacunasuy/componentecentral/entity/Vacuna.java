package vacunasuy.componentecentral.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@NamedNativeQueries({
	/* Vacunas para las cuales el usuario tiene algún acto vacunal */
	@NamedNativeQuery(name="listarVacunasPorUsuario", query="SELECT DISTINCT v.* FROM usuarios_actos_vacunales u "
			+ "INNER JOIN actos_vacunales a ON u.actosvacunales_id = a.id "
			+ "INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id "
			+ "INNER JOIN vacunas v ON p.fk_vacuna = v.id "
			+ "WHERE u.usuario_id = :idUsuario", resultClass = Vacuna.class)
})
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
    @JoinColumn(name = "fk_enfermedad", referencedColumnName = "id")
	private Enfermedad enfermedad;

	/* Métodos generados por Lombok */
}











