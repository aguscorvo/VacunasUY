package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.entity.Agenda;

@Local
public interface IAgendaDAO {
	
	public List<Agenda> listar();
	public Agenda listarPorId(Long id);
	public Agenda crear(Agenda agenda);
	public Agenda editar(Agenda agenda);
	public void eliminar(Agenda agenda);

}
