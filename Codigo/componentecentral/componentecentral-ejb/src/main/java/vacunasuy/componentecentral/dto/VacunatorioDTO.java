package vacunasuy.componentecentral.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacunatorioDTO {
	
	private Long id;
	private String nombre;
	private Double latitud;
	private Double longitud;
	private String direccion;
	private LocalidadDTO localidad;
	private DepartamentoDTO departamento;
	private List<PuestoDTO> puestos;
//	private List<EventoDTO> eventos;
//	private List<ActoVacunalDTO> actosVacunales;


}
