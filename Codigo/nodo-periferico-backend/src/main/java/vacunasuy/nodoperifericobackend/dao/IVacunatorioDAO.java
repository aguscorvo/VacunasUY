package vacunasuy.nodoperifericobackend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.entity.Vacunatorio;

@Repository
public interface IVacunatorioDAO extends MongoRepository<Vacunatorio, Long> {

}
