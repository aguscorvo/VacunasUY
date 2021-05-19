package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.geolatte.geom.Point;
import org.hibernate.annotations.Type;

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
@Table(name = "vacunatorios")
public class Vacunatorio  implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "latitud", nullable = false, length = 100)
	private Double latitud;	
	
	@Column(name = "longitud", nullable = false, length = 100)
	private Double longitud;	
	
	@Column(name = "direccion", nullable = false, length = 150)
	private String direccion;
	
	@Column(name = "clave", nullable = false, length = 100)
	private String clave;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_localidad", referencedColumnName = "id")
	private Localidad localidad;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_departamento", referencedColumnName = "id")
	private Departamento departamento;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="vacunatorio", orphanRemoval=true) 
	private List<Puesto> puestos = new ArrayList<Puesto>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Evento> eventos = new ArrayList<Evento>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ActoVacunal> actosVacunales = new ArrayList<ActoVacunal>();
	
	@Column(name = "geom")
	private Point geom;
	
	
	
	
}
