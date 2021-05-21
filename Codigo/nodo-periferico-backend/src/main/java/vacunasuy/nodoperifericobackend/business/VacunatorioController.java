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

import vacunasuy.nodoperifericobackend.converter.VacunatorioConverter;
import vacunasuy.nodoperifericobackend.dao.IVacunatorioDAO;
import vacunasuy.nodoperifericobackend.dto.VacunatorioDTO;
import vacunasuy.nodoperifericobackend.service.VacunatorioService;
import vacunasuy.nodoperifericobackend.util.WrapperResponse;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/vacunatorios")
public class VacunatorioController {

	@Autowired
	private VacunatorioService vacunatorioService;
	
	@Autowired
	private VacunatorioConverter vacunatorioConverter;

	@GetMapping
	public ResponseEntity<WrapperResponse<List<VacunatorioDTO>>> listar() {
		WrapperResponse<List<VacunatorioDTO>> respuesta = null;
		try {
			respuesta = new WrapperResponse<List<VacunatorioDTO>>(true, "Vacunatorios listados con éxito.", vacunatorioConverter.fromEntity(vacunatorioService.listar()));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<List<VacunatorioDTO>>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<WrapperResponse<VacunatorioDTO>> crear(@RequestBody VacunatorioDTO request){
		WrapperResponse<VacunatorioDTO> respuesta = null;
		try {
			respuesta = new WrapperResponse<VacunatorioDTO>(true, "Vacunatorio creado con éxito.", vacunatorioConverter.fromEntity(vacunatorioService.crear(vacunatorioConverter.fromDTO(request))));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<VacunatorioDTO>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
