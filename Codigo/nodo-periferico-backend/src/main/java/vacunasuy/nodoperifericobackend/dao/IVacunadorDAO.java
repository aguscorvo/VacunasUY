package vacunasuy.nodoperifericobackend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.entity.Vacunador;
import vacunasuy.nodoperifericobackend.entity.Vacunador.CompositeKey;

@Repository
public interface IVacunadorDAO extends MongoRepository<Vacunador, CompositeKey>{

}
