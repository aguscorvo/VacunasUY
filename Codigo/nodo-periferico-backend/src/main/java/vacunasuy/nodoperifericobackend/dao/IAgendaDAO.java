package vacunasuy.nodoperifericobackend.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.dto.AgendaDTO;
import vacunasuy.nodoperifericobackend.entity.Agenda;

@Repository
public interface IAgendaDAO extends MongoRepository<Agenda, AgendaDTO> {

}
