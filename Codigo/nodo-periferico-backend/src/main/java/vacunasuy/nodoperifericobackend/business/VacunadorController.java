package vacunasuy.nodoperifericobackend.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vacunasuy.nodoperifericobackend.converter.VacunadorConverter;
import vacunasuy.nodoperifericobackend.dto.VacunadorDTO;
import vacunasuy.nodoperifericobackend.dto.VacunatorioDTO;
import vacunasuy.nodoperifericobackend.service.VacunadorService;
import vacunasuy.nodoperifericobackend.util.WrapperResponse;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/vacunadores")
public class VacunadorController {

	@Autowired
	private VacunadorService vacunadorService;
	
	@Autowired
	private VacunadorConverter vacunadorConverter;
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<VacunadorDTO>>> listar() {
		WrapperResponse<List<VacunadorDTO>> respuesta = null;
		try {
			respuesta = new WrapperResponse<List<VacunadorDTO>>(true, "Vacunadores listados con éxito.", vacunadorConverter.fromEntity(vacunadorService.listar()));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<List<VacunadorDTO>>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<WrapperResponse<VacunadorDTO>> crear(@RequestBody VacunadorDTO request){
		WrapperResponse<VacunadorDTO> respuesta = null;
		try {
			respuesta = new WrapperResponse<VacunadorDTO>(true, "Vacunador creado con éxito.", vacunadorConverter.fromEntity(vacunadorService.crear(vacunadorConverter.fromDTO(request))));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<VacunadorDTO>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
