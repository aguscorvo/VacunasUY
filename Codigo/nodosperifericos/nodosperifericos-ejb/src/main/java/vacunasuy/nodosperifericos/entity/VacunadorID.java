package vacunasuy.nodosperifericos.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
public class VacunadorID implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private LocalDate fechaAsignacion;
	private Long idVacunatorio;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaAsignacion == null) ? 0 : fechaAsignacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VacunadorID other = (VacunadorID) obj;
		if (fechaAsignacion == null) {
			if (other.fechaAsignacion != null)
				return false;
		} else if (!fechaAsignacion.equals(other.fechaAsignacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
