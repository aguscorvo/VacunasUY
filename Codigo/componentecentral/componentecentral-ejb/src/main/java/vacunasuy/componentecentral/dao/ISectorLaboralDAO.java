package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.SectorLaboral;

@Local
public interface ISectorLaboralDAO {

	public List<SectorLaboral> listar();
	public SectorLaboral listarPorId(Long id);
	public SectorLaboral listarRandom();
	
}
