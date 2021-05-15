package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@IdClass(Atiende.class)
public class Atiende implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id 
	@ManyToOne
	@JoinColumn( name="fk_usuario",
			insertable=false,
			updatable=false
	)
	private Usuario usuario;
	
	@Id 
	@ManyToOne
	@JoinColumn( name="fk_puesto",
			insertable=false,
			updatable=false
	)
	private Puesto puesto;
	
	@Id
	@Column(name = "fecha", nullable = false)
	private LocalDate fecha;	
	
}
