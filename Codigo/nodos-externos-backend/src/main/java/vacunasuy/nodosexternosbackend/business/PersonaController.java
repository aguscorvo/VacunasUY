package vacunasuy.nodosexternosbackend.business;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public ResponseEntity<PersonaDTO> getPersona(@PathVariable("cedula") String cedula){
		PersonaDTO respuesta = null;
		try {
			respuesta = personaConverter.fromEntity(personaService.getPersona(cedula));
			return ResponseEntity.ok(respuesta);	
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
	}
	
}
