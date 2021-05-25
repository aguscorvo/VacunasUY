package vacunasuy.nodoperifericobackend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.dto.TransportistaDTO;
import vacunasuy.nodoperifericobackend.entity.Transportista;

@Repository
public interface ITransportistaDAO extends MongoRepository<Transportista, TransportistaDTO>{

}
