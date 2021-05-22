package vacunasuy.nodoperifericobackend.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vacunasuy.nodoperifericobackend.converter.AgendaConverter;
import vacunasuy.nodoperifericobackend.dto.AgendaDTO;
import vacunasuy.nodoperifericobackend.service.AgendaService;
import vacunasuy.nodoperifericobackend.util.WrapperResponse;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/agendas")
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	private AgendaConverter agendaConverter;
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<AgendaDTO>>> listar() {
		WrapperResponse<List<AgendaDTO>> respuesta = null;
		try {
			respuesta = new WrapperResponse<List<AgendaDTO>>(true, "Agendas listadas con éxito.", agendaConverter.fromEntity(agendaService.listar()));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<List<AgendaDTO>>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
