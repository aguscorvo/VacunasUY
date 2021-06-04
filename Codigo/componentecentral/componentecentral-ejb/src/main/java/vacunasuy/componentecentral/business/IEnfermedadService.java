package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;

import vacunasuy.componentecentral.dto.EnfermedadCrearDTO;
import vacunasuy.componentecentral.dto.EnfermedadDTO;
import vacunasuy.componentecentral.entity.Enfermedad;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IEnfermedadService {

	public List<EnfermedadDTO> listar() throws VacunasUyException;
	public Enfermedad listarPorId(Long id);
	public List<EnfermedadDTO> listarEnfermedadesPorUsuario(Long idUsuario) throws VacunasUyException;
	public EnfermedadDTO crear(EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException;
	public EnfermedadDTO editar(Long id, EnfermedadCrearDTO enfermedadCrearDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
}
