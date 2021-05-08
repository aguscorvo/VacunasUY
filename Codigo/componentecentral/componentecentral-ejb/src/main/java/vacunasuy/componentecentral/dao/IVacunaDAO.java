package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Vacuna;

@Local
public interface IVacunaDAO {
	
	public List<Vacuna> listar();
	public Vacuna listarPorId(Long id);
	public Vacuna crear(Vacuna vacuna);
	public Vacuna editar(Vacuna vacuna);
	public void eliminar(Vacuna vacuna);
	
}
