package vacunasuy.nodosperifericos.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.nodosperifericos.entity.Vacunatorio;

@Local
public interface IVacunatorioDAO {
	
	public List<Vacunatorio> listar();
	public Vacunatorio crear(Vacunatorio vacunatorio);
	
}
