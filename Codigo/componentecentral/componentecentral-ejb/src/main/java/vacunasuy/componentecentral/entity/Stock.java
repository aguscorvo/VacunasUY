package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "stock")
@IdClass(Stock.class)
public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	@JoinColumn( name="fk_vacunatorio",
			insertable=false,
			updatable=false
	)
	private Vacunatorio vacunatorio;
	
	@Id 
	@ManyToOne
	@JoinColumn( name="fk_vacuna",
			insertable=false,
			updatable=false
	)
	private Vacuna vacuna;
	
	private Long cantidad;
	
}
