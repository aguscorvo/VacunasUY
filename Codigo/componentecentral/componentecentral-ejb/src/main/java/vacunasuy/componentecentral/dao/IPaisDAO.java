package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Pais;

@Local
public interface IPaisDAO {
	
	public List<Pais> listar();
	public Pais listarPorId(Long id);

}
