package vacunasuy.componentecentral.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
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
@Embeddable
public class StockID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idVacunatorio;
	private Long idVacuna;
	
}
