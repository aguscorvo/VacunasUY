package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Departamento;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.entity.Ubicacion;
import vacunasuy.componentecentral.entity.Vacunatorio;

@Local
public interface IVacunatorioDAO {
	
	public List<Vacunatorio> listar();
	public Vacunatorio listarPorId(Long id);
	public Vacunatorio crear(Vacunatorio vacunatorio);
	public Vacunatorio editar(Vacunatorio vacunatorio);
	public void eliminar(Vacunatorio vacunatorio);
	
	public List<Vacunatorio> listarPorUbicacion(Long localidad, Long departamento);
	public List<Vacunatorio> listarPorDepartamento(Long departamento);
	
}
