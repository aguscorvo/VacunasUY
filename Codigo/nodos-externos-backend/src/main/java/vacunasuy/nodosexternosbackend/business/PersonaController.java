package vacunasuy.nodosexternosbackend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vacunasuy.nodosexternosbackend.converter.PersonaConverter;
import vacunasuy.nodosexternosbackend.dto.PersonaDTO;
import vacunasuy.nodosexternosbackend.service.PersonaService;
import vacunasuy.nodosexternosbackend.util.WrapperResponse;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET})
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private PersonaConverter personaConverter;
	
	@GetMapping(path= "/{cedula}")
	public ResponseEntity<WrapperResponse<PersonaDTO>> getPersona(@PathVariable("cedula") int cedula){
		WrapperResponse<PersonaDTO> respuesta = null;
		try {
			respuesta = new WrapperResponse<PersonaDTO>(true, "Persona listada con éxito.", 
					personaConverter.fromEntity(personaService.getPersona( cedula )) );
			return respuesta.createResponse(HttpStatus.OK);			
		}catch(Exception e) {
			respuesta = new WrapperResponse<PersonaDTO>(false, e.getLocalizedMessage());
			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	//	
//	@PostMapping
//	public ResponseEntity<WrapperResponse<PersonaDTO>> crear(@RequestBody PersonaDTO request){
//		WrapperResponse<PersonaDTO> respuesta = null;
//		try {
//			respuesta = new WrapperResponse<PersonaDTO>(true, "Personas creada con éxito.", 
//					personaConverter.fromEntity(personaService.crear(personaConverter.fromDTO(request))));
//			return respuesta.createResponse(HttpStatus.OK);
//		}catch(Exception e) {
//			respuesta = new WrapperResponse<PersonaDTO>(false, e.getLocalizedMessage());
//			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
//		}		
//	}
	
//	@GetMapping
//	public ResponseEntity<WrapperResponse<List<PersonaDTO>>> listar(){
//		WrapperResponse<List<PersonaDTO>> respuesta = null;
//		try {
//			respuesta = new WrapperResponse<List<PersonaDTO>>(true, "Personas listadas con éxito.", 
//					personaConverter.fromEntity(personaService.listar()));
//			return respuesta.createResponse(HttpStatus.OK);
//		}catch (Exception e) {
//			respuesta = new WrapperResponse<List<PersonaDTO>>(false, e.getLocalizedMessage());
//			return respuesta.createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
}
