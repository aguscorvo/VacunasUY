package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "eventos")
public class Evento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "fecha", nullable = false)
	private LocalDateTime fecha;
	@Column(name = "detalle", length = 255)
	private String detalle;
	@Column(name = "cantidad", nullable = false)
	private Long cantidad;
	
	@ManyToOne
	@JoinColumn(name = "fk_lote")
	private Lote lote;
	
	@ManyToOne
	@JoinColumn(name = "fk_transportista")
	private Transportista transportista;
	
}
