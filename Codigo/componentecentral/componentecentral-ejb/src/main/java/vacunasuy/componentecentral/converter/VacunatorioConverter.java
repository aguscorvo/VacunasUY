package vacunasuy.componentecentral.converter;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.PuestoMinDTO;
import vacunasuy.componentecentral.dto.VacunatorioCercanoDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.dto.VacunatorioMinDTO;
import vacunasuy.componentecentral.entity.Puesto;
import vacunasuy.componentecentral.entity.Vacunatorio;

@Singleton
public class VacunatorioConverter extends AbstractConverter<Vacunatorio, VacunatorioDTO>{
	
	@EJB
	private LocalidadConverter localidadConverter;
	
	@EJB
	private DepartamentoConverter departamentoConverter;
	
	@EJB
	private PuestoConverter puestoConverter;
	
	@EJB
	private EventoConverter eventoConverter;
	
	@EJB
	private ActoVacunalConverter actoVacunalConverter;
	
	@Override
	public VacunatorioDTO fromEntity(Vacunatorio v) {
		if(v==null) return null;
		return VacunatorioDTO.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.latitud(v.getLatitud())
				.longitud(v.getLongitud())
				.direccion(v.getDireccion())
				.localidad(localidadConverter.fromEntity(v.getLocalidad()))
				.departamento(departamentoConverter.fromEntityToMin(v.getDepartamento()))
				.puestos(puestoConverter.fromEntity(v.getPuestos()))
				.eventos(eventoConverter.fromEntity(v.getEventos()))
				.actosVacunales(actoVacunalConverter.fromEntity(v.getActosVacunales()))
				.build();								
	}
	
	@Override
	public Vacunatorio fromDTO(VacunatorioDTO v) {
		if(v==null) return null;
		return Vacunatorio.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.latitud(v.getLatitud())
				.longitud(v.getLongitud())
				.direccion(v.getDireccion())
				.localidad(localidadConverter.fromDTO(v.getLocalidad()))
				.departamento(departamentoConverter.fromMinDTO(v.getDepartamento()))
				.puestos(puestoConverter.fromDTO(v.getPuestos()))
				.eventos(eventoConverter.fromDTO(v.getEventos()))
				.actosVacunales(actoVacunalConverter.fromDTO(v.getActosVacunales()))
				.build();
	}
	
	public Vacunatorio fromCrearDTO(VacunatorioCrearDTO v) {
		if(v == null) return null;
		return Vacunatorio.builder()
				.nombre(v.getNombre())
				.latitud(v.getLatitud())
				.longitud(v.getLongitud())
				.direccion(v.getDireccion())
				.build();
	}
	
	public Vacunatorio fromCercanoDTO(VacunatorioCercanoDTO v) {
		if(v== null) return null;
		return Vacunatorio.builder()
				.latitud(v.getLatitud())
				.longitud(v.getLongitud())
				.build();
	}
	
	public VacunatorioMinDTO fromEntityToMin(Vacunatorio v) {
		if(v==null) return null;
		return VacunatorioMinDTO.builder()
				.id(v.getId())
				.nombre(v.getNombre())
				.latitud(v.getLatitud())
				.longitud(v.getLongitud())
				.direccion(v.getDireccion())
				.localidad(localidadConverter.fromEntity(v.getLocalidad()))
				.departamento(departamentoConverter.fromEntityToMin(v.getDepartamento()))				
				.build();
		
	} 
	
	public List<VacunatorioMinDTO> fromEntityToMin(List<Vacunatorio> entities){
		if(entities == null) return null;
		return entities.stream()
			.map(e -> fromEntityToMin(e))
			.collect(Collectors.toList());
	}

}
