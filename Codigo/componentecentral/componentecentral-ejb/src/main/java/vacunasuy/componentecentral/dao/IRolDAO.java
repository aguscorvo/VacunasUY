package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Rol;

@Local
public interface IRolDAO {

	public Rol listarPorId(Long id);

	public List<Rol> listar();
}
