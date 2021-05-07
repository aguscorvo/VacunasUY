package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Puesto;

@Local
public interface IPuestoDAO {
	
	public List<Puesto> listar();
	public Puesto listarPorId(Long id);
	public Puesto listarPorNumero(int numero);
	public Puesto crear(Puesto puesto);
	public Puesto editar(Puesto puesto);
	public void eliminar(Puesto puesto);

}
