package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.PlanVacunacion;

@Local
public interface IPlanVacunacionDAO {

	public List<PlanVacunacion> listar();
	public List<PlanVacunacion> listarPlanesVigentes();
	public PlanVacunacion listarPorId(Long id);
	public PlanVacunacion crear(PlanVacunacion plan);
	public PlanVacunacion editar(PlanVacunacion plan);
	public void eliminar(PlanVacunacion plan);
	
}
