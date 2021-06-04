package vacunasuy.componentecentral.dao;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.ActoVacunalCertificadoDTO;
import vacunasuy.componentecentral.entity.ActoVacunal;

@Local
public interface IActoVacunalDAO {
	
	public List<ActoVacunal> listar();
	public ActoVacunal listarPorId(Long id);
	public List<ActoVacunalCertificadoDTO> listarActosVacunalesPorUsuarioEnfermedad(Long idUsuario, Long idEnfermedad);
	public ActoVacunal crear(ActoVacunal actoVacunal);
	public ActoVacunal editar(ActoVacunal actoVacunal);
	public void eliminar(ActoVacunal actoVacunal);

}
