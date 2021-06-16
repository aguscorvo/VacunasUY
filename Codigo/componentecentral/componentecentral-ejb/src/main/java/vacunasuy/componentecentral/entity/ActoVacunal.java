package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.ConstructorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "actos_vacunales")
@SqlResultSetMapping(
	name="ActoVacunalCertificadoDTOMapping", 
	classes = @ConstructorResult(
		targetClass = ActoVacunalCertificadoDTO.class,
		columns = {
				@ColumnResult(name = "fecha", type=String.class),
				@ColumnResult(name = "nombre", type=String.class)
		}	
	)
)
public class ActoVacunal implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha")
	LocalDate fecha;
	
	@Column(name = "nro_dosis")
	private int nroDosis;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_plan_vacunacion", referencedColumnName = "id")
	private PlanVacunacion planVacunacion;

}
