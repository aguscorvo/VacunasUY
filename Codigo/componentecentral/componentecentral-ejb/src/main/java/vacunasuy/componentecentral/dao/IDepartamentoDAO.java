package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Departamento;

@Local
public interface IDepartamentoDAO {

	public List<Departamento> listar();
	public Departamento listarPorId(Long id);
	public Departamento crear(Departamento departamento);
	public Departamento editar (Departamento departamento);
	public void eliminar (Departamento departamento);
}
