package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.Local;

import vacunasuy.componentecentral.dto.AgendaDTO;
import vacunasuy.componentecentral.dto.AtiendeCrearDTO;
import vacunasuy.componentecentral.dto.AtiendeDTO;
import vacunasuy.componentecentral.dto.RespuestaUserInfoDTO;
import vacunasuy.componentecentral.dto.UsuarioCrearDTO;
import vacunasuy.componentecentral.dto.UsuarioDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginBackofficeDTO;
import vacunasuy.componentecentral.dto.UsuarioLoginExitosoDTO;
import vacunasuy.componentecentral.dto.UsuarioRegistrarTFDTO;
import vacunasuy.componentecentral.entity.Usuario;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IUsuarioService {
	
	public List<UsuarioDTO> listar() throws VacunasUyException;
	public UsuarioDTO listarPorId(Long id) throws VacunasUyException;
	public UsuarioDTO listarPorCorreo(String correo) throws VacunasUyException;
	public UsuarioDTO crear(UsuarioCrearDTO usuarioDTO) throws VacunasUyException;
	public UsuarioDTO editar(Long id, UsuarioCrearDTO usuarioDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	public UsuarioLoginExitosoDTO loginBackoffice(UsuarioLoginBackofficeDTO usuarioDTO) throws VacunasUyException;
	public UsuarioLoginExitosoDTO loginGubUy(RespuestaUserInfoDTO usuarioDTO) throws VacunasUyException;
	public boolean existeAgenda (Long id_usuario, Long id_plan) throws VacunasUyException;
	public void asignarVacunadorAPuesto(AtiendeCrearDTO atiendeDTO) throws VacunasUyException;
	public void agregarActoVacunal(Long usuario, Long actoVacunal) throws VacunasUyException;
	public void agregarAgenda(Long usuario, Long agenda) throws VacunasUyException;
	public void cancelarAgenda(Long usuario, Long agenda) throws VacunasUyException;
	public void registrarTokenFirebase(UsuarioRegistrarTFDTO usuarioDTO) throws VacunasUyException;
	public List<AgendaDTO> listarAgendasCiudadano(Long id) throws VacunasUyException;
	public List<AtiendeDTO> listarAtiendeVacunador(Long id) throws VacunasUyException;
	
}
