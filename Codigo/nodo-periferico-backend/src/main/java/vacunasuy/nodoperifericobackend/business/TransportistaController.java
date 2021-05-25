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
import vacunasuy.nodoperifericobackend.converter.TransportistaConverter;
import vacunasuy.nodoperifericobackend.dto.TransportistaDTO;
import vacunasuy.nodoperifericobackend.service.TransportistaService;
import vacunasuy.nodoperifericobackend.util.WrapperResponse;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/transportistas")
public class TransportistaController {
	
	@Autowired
	private TransportistaService transportistaService;
	
	@Autowired
	private TransportistaConverter transportistaConverter;
	
	@GetMapping
	public ResponseEntity<WrapperResponse<List<TransportistaDTO>>> listar() {
		WrapperResponse<List<TransportistaDTO>> respuesta = null;
		try {
			respuesta = new WrapperResponse<List<TransportistaDTO>>(true, "Transportista listados con éxito.", transportistaConverter.fromEntity(transportistaService.listar()));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<List<TransportistaDTO>>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<WrapperResponse<TransportistaDTO>> crear(@RequestBody TransportistaDTO request){
		WrapperResponse<TransportistaDTO> respuesta = null;
		try {
			respuesta = new WrapperResponse<TransportistaDTO>(true, "Transportista creado con éxito.", transportistaConverter.fromEntity(transportistaService.crear(transportistaConverter.fromDTO(request))));
			return respuesta.createResponse(HttpStatus.OK);
		} catch (Exception e) {
			respuesta = new WrapperResponse<TransportistaDTO>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
