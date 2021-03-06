package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private StockID id;
	
	@Column(name = "cantidad", nullable = false)
	private Long cantidad;
	
}
