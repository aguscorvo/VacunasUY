package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Lote;

@Local
public interface ILoteDAO {

	public List<Lote> listar();
	public Lote listarPorId(Long id);
	public Lote crear(Lote lote);
	public Lote editar(Lote lote);
	public void eliminar(Lote lote);
	
}
