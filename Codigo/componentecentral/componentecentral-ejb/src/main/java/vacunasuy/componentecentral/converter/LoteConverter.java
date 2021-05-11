package vacunasuy.componentecentral.converter;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.entity.Lote;

@Singleton
public class LoteConverter extends AbstractConverter<Lote, LoteDTO> {

	@EJB
	private ProveedorConverter proveedorConverter;
	
	@EJB
	private VacunaConverter vacunaConverter;
	
	@Override
	public LoteDTO fromEntity(Lote e) {
		if(e == null) return null;
		return LoteDTO.builder()
				.id(e.getId())
				.cantidad(e.getCantidad())
				.proveedor(proveedorConverter.fromEntity(e.getProveedor()))
				.vacuna(vacunaConverter.fromEntity(e.getVacuna()))
				.build();
	}

	@Override
	public Lote fromDTO(LoteDTO d) {
		if(d == null) return null;
		return Lote.builder()
				.id(d.getId())
				.cantidad(d.getCantidad())
				.proveedor(proveedorConverter.fromDTO(d.getProveedor()))
				.vacuna(vacunaConverter.fromDTO(d.getVacuna()))
				.build();
	}
	
	public Lote fromCrearDTO(LoteCrearDTO d) {
		if(d == null) return null;
		return Lote.builder()
				.cantidad(d.getCantidad())
				.build();
	}

}
