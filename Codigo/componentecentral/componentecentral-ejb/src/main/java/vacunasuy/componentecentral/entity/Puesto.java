package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "puestos")
public class Puesto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "numero", nullable = false)
	private int numero;
	
//	@ManyToOne(	fetch = FetchType.LAZY)
//	@JoinColumn(
//			name="vacunatorio_id",
//			insertable=false,
//			updatable=false
//	)
//	private Vacunatorio vacunatorio;
	
//	@OneToMany(mappedBy="puesto", cascade = CascadeType.ALL, orphanRemoval=true)
//	private List<Agenda> agendas = new ArrayList();
	
	@OneToMany(mappedBy="puesto", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Atiende> atiende = new ArrayList();



}
