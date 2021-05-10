package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "planes_vacunacion")
public class PlanVacunacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "edadMinima")
	private int edadMinima;
	@Column(name = "edadMaxima")
	private int edadMaxima;
	@Column(name = "fechaInicio")
	private LocalDateTime fechaInicio;
	@Column(name = "fechaFin")
	private LocalDateTime fechaFin;
	
	/* Un plan de vacunación puede involucrar varios sectores */
	@ManyToMany
	@JoinTable(name = "planes_vacunacion_sectores", 
			joinColumns = {@JoinColumn(name="fk_plan_vacunacion")}, 
			inverseJoinColumns = {@JoinColumn(name="fk_sector")}
		)
	private List<SectorLaboral> sectores = new ArrayList<SectorLaboral>();
	
	@ManyToOne
	@JoinColumn(name = "fk_vacuna")
	private Vacuna vacuna;
	
	@OneToMany
	@JoinColumn(name = "fk_plan_vacunacion")
	private List<Agenda> agendas = new ArrayList<Agenda>();

}
