package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Localidad;

@Local
public interface ILocalidadDAO {
	
	public List<Localidad> listar();
	public Localidad listarPorId(Long id);
	public Localidad crear(Localidad localidad);
	public Localidad editar(Localidad localidad);
	public void eliminar(Localidad localidad);

}
