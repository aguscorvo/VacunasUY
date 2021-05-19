package vacunasuy.nodosperifericos.dto;

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
public class VacunadorDTO {

    private Long id;
    private String documento;
    private String nombre;
    private String apellido;
    private String correo;
    private String fechaNacimiento;
    private Long idVacunatorio;
    private String fechaAsignacion;
	
}
