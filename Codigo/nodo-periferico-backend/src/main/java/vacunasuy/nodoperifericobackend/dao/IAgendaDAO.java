package vacunasuy.nodoperifericobackend.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vacunasuy.nodoperifericobackend.dto.AgendaDTO;
import vacunasuy.nodoperifericobackend.entity.Agenda;

@Repository
public interface IAgendaDAO extends MongoRepository<Agenda, AgendaDTO> {

	public List<Agenda> findByFechaLessThanAndIdVacunatorioAndVacunado(LocalDateTime fecha, Long idVacunatorio, Boolean vacunado);
	public List<Agenda> findByFechaGreaterThanAndVacunado(LocalDateTime fecha, Boolean vacunado);
	
}
