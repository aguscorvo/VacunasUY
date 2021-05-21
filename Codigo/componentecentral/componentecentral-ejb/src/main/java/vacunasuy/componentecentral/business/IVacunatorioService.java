package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.Local;

import vacunasuy.componentecentral.dto.AgendaMinDTO;
import vacunasuy.componentecentral.dto.AgendaVacunatorioDTO;
import vacunasuy.componentecentral.dto.UbicacionDTO;
import vacunasuy.componentecentral.dto.UsuarioMinDTO;
import vacunasuy.componentecentral.dto.VacunatorioCrearDTO;
import vacunasuy.componentecentral.dto.VacunatorioDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Local
public interface IVacunatorioService {
	
	public List<VacunatorioDTO> listar() throws VacunasUyException;
	public VacunatorioDTO listarPorId(Long id) throws VacunasUyException;
	public VacunatorioDTO crear(VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException;
	public VacunatorioDTO editar(Long id, VacunatorioCrearDTO vacunatorioDTO) throws VacunasUyException;
	public void eliminar(Long id) throws VacunasUyException;
	
	public List<VacunatorioDTO> listarVacunatoriosDadoPlan(Long id_plan) throws VacunasUyException;
	public List<VacunatorioDTO> listarCercanos(UbicacionDTO ubicacionDTO) throws VacunasUyException;
	public VacunatorioDTO agregarEvento(Long vacunatorio, Long evento) throws VacunasUyException;
	public VacunatorioDTO agregarActoVacunal(Long vacunatorio, Long actoVacunal) throws VacunasUyException;
	public List<UsuarioMinDTO> obtenerAsignacionVacunadores(Long vacunatorio, String clave, String fecha) throws VacunasUyException;
	public List<VacunatorioDTO> listarPorUbicacion(Long localidad, Long departamento) throws VacunasUyException;
	public List<VacunatorioDTO> listarPorDepartamento(Long departamento) throws VacunasUyException;
	public void crearGeometrias() throws VacunasUyException;
	public Double distancia(Long vacunatorio1, Long vacunatorio2) throws VacunasUyException;
	public List<AgendaVacunatorioDTO> listarAgendasPorVacunatorio(Long id, String fecha) throws VacunasUyException;

}
