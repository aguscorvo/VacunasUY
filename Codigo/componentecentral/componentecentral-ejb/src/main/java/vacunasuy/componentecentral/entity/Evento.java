package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import vacunasuy.componentecentral.util.EstadoEvento;

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
	@Column(name = "estado")
	private Enum<EstadoEvento> estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_lote")
	private Lote lote;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_transportista")
	private Transportista transportista;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_vacunatorio")
	private Vacunatorio vacunatorio;
	
}
