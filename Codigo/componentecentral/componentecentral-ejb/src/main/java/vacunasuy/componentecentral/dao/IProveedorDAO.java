package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Proveedor;

@Local
public interface IProveedorDAO {

	public List<Proveedor> listar();
	public Proveedor listarPorId(Long id);
	public Proveedor crear(Proveedor proveedor);
	public Proveedor editar(Proveedor proveedor);
	public void eliminar(Proveedor proveedor);
	
}
