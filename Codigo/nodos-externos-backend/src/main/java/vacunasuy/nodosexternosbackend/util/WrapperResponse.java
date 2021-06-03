package vacunasuy.nodosexternosbackend.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperResponse <T> {

	private Boolean ok;
	private String mensaje;
	private T cuerpo;
	
	public ResponseEntity<WrapperResponse<T>> createResponse(HttpStatus status){
		return new ResponseEntity<>(this, status);
	}

	public WrapperResponse(Boolean ok, String mensaje) {
		super();
		this.ok = ok;
		this.mensaje = mensaje;
	}
	
}
