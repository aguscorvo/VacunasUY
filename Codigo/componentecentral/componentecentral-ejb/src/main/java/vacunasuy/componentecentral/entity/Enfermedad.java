package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@Table(name = "enfermedades")
@NamedNativeQueries({
	/* Enfermedades para las cuales el usuario tiene algún acto vacunal */
	@NamedNativeQuery(name="listarEnfermedadesPorUsuario", query="SELECT DISTINCT e.* FROM usuarios_actos_vacunales u INNER JOIN actos_vacunales a ON u.actosvacunales_id = a.id INNER JOIN planes_vacunacion p ON a.fk_plan_vacunacion = p.id INNER JOIN vacunas v ON p.fk_vacuna = v.id INNER JOIN enfermedades e ON v.fk_enfermedad = e.id WHERE u.usuario_id = :idUsuario", resultClass = Enfermedad.class)
})
public class Enfermedad implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	/* Métodos generados por Lombok */
}