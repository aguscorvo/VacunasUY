package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Enfermedad;

@Local
public interface IEnfermedadDAO {

	public List<Enfermedad> listar();
	public Enfermedad listarPorId(Long id);
	public Enfermedad crear(Enfermedad enfermedad);
	public Enfermedad editar(Enfermedad enfermedad);
	public void eliminar(Enfermedad enfermedad);
	
}
