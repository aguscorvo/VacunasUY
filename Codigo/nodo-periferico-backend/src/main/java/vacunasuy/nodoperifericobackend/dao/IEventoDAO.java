package vacunasuy.nodoperifericobackend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.entity.Evento;

@Repository
public interface IEventoDAO extends MongoRepository<Evento, Long> {

}
