package vacunasuy.componentecentral.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ubicacion {
	
	Double latitud; 
	Double longitud;
	Double distancia;
	
}
