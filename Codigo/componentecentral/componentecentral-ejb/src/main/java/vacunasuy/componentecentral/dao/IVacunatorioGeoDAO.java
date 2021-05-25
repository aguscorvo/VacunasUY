package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Ubicacion;
import vacunasuy.componentecentral.entity.Vacunatorio;
import vacunasuy.componentecentral.entity.VacunatorioGeo;

@Local
public interface IVacunatorioGeoDAO {

	public List<VacunatorioGeo> listar();
	public VacunatorioGeo listarPorId(Long id);
	public VacunatorioGeo crear(VacunatorioGeo vacunatorio);
	public VacunatorioGeo editar(VacunatorioGeo vacunatorio);
	public void eliminar(VacunatorioGeo vacunatorio);
	
	public List<Long> listarCercanos(Ubicacion ubicacion);

}
