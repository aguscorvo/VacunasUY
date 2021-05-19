package vacunasuy.nodosperifericos.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.nodosperifericos.entity.Vacunador;

@Local
public interface IVacunadorDAO {

	public List<Vacunador> listar();
	public Vacunador crear(Vacunador vacunador);
	public void eliminarRegistros(String fecha, Long idVacunatorio);
	
}
