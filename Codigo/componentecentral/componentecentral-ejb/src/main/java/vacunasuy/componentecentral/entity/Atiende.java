package vacunasuy.componentecentral.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Atiende implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	private Puesto puesto;
	
	private LocalDateTime fecha;
}
