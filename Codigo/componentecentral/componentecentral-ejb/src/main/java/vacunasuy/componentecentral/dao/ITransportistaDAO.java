package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Transportista;

@Local
public interface ITransportistaDAO {
	
	public List<Transportista> listar();
	public Transportista listarPorId(Long id);
	public Transportista crear(Transportista transportista);
	public Transportista editar(Transportista transportista);
	public void eliminar(Transportista transportista);

}
