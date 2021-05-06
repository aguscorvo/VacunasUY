package vacunasuy.componentecentral.dao;

import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Rol;

@Local
public interface IRolDAO {

	public Rol listarPorId(Long id);
}
