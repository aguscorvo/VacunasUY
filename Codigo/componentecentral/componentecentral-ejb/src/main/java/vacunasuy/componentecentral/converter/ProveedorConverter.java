package vacunasuy.componentecentral.converter;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.entity.Proveedor;

@Singleton
public class ProveedorConverter extends AbstractConverter<Proveedor, ProveedorDTO>{
	
	@EJB
	private PaisConverter paisConverter;
	
	@Override
	public ProveedorDTO fromEntity(Proveedor p) {
		if(p==null) return null;
		return ProveedorDTO.builder()
				.id(p.getId())
				.nombre(p.getNombre())
				.pais(paisConverter.fromEntity(p.getPais()))
				.build();
	}
	
	@Override
	public Proveedor fromDTO(ProveedorDTO p) {
		return null;
	}
	
	public Proveedor fromCrearDTO(ProveedorCrearDTO p) {
		if(p==null) return null;
		return Proveedor.builder()
				.nombre(p.getNombre())
				.build();
	}	

}
