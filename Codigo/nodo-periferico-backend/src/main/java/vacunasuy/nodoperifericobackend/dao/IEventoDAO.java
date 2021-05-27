package vacunasuy.nodoperifericobackend.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.entity.Evento;

@Repository
public interface IEventoDAO extends MongoRepository<Evento, Long> {

	public List<Evento> findByEstado(String estado);
	
}
