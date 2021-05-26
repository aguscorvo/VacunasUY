package vacunasuy.componentecentral.dao;

import java.util.List;
import javax.ejb.Local;
import vacunasuy.componentecentral.entity.Evento;
import vacunasuy.componentecentral.util.EstadoEvento;

@Local
public interface IEventoDAO {
	
	public List<Evento> listar();
	public Evento listarPorId(Long id);
	public List<Evento> listarPorEstado(EstadoEvento estado);
	public Evento crear(Evento evento);
	public Evento editar(Evento evento);
	public void eliminar(Evento evento);

}
