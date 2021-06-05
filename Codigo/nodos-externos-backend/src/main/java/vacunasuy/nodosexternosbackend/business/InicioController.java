package vacunasuy.nodosexternosbackend.business;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/")
public class InicioController {

	@GetMapping
	public String inicio() {
		return "<html><head><meta charset=\"UFT-8\"/></head><body><h2>Componente: Nodos externos</h2><h3>Status: <span style=\"color:green\">activo<span></h3></body></html>";
	}
	
}
