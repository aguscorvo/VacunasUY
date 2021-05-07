package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Vacunatorio;

@Local
public interface IVacunatorioDAO {
	
	public List<Vacunatorio> listar();
	public Vacunatorio listarPorId(Long id);
	public Vacunatorio crear(Vacunatorio vacunatorio);
	public Vacunatorio editar(Vacunatorio vacunatorio);
	public void eliminar(Vacunatorio vacunatorio);

}
