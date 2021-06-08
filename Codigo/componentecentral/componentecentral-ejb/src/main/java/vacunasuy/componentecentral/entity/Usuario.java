package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "documento", nullable = false, length = 50)
	private String documento;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	@Column(name = "apellido", nullable = false, length = 50)
	private String apellido;
	@Column(name = "correo", length = 100, unique = true)
	private String correo;
	@Column(name = "fechaNacimiento")
	private LocalDate fechaNacimiento;
	@Column(name = "password", length = 255)
	private String password;
	@Column(name = "token_firebase", length = 255)
	private String tokenFirebase;
	
	@ManyToMany
	@JoinTable(name = "usuarios_roles", 
		joinColumns = {@JoinColumn(name="fk_usuario_id")}, 
		inverseJoinColumns = {@JoinColumn(name="fk_rol_id")}
	)
	private List<Rol> roles = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "fk_sector_laboral")
	private SectorLaboral sectorLaboral;
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Atiende> atiende = new ArrayList();
		
	@OneToMany(cascade = CascadeType.ALL)
	private List<ActoVacunal> actosVacunales = new ArrayList();
	
	@OneToMany(mappedBy="usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Agenda> agendas = new ArrayList();
	
	/* Métodos generados por Lombok */
	
}
