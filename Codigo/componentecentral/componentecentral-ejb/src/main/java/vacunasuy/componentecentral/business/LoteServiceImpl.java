package vacunasuy.componentecentral.business;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import vacunasuy.componentecentral.converter.LoteConverter;
import vacunasuy.componentecentral.dao.ILoteDAO;
import vacunasuy.componentecentral.dao.IProveedorDAO;
import vacunasuy.componentecentral.dao.IVacunaDAO;
import vacunasuy.componentecentral.dto.LoteCrearDTO;
import vacunasuy.componentecentral.dto.LoteDTO;
import vacunasuy.componentecentral.entity.Lote;
import vacunasuy.componentecentral.entity.Proveedor;
import vacunasuy.componentecentral.entity.Vacuna;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class LoteServiceImpl implements ILoteService {

	@EJB
	private ILoteDAO loteDAO;
	
	@EJB
	private IProveedorDAO proveedorDAO;
	
	@EJB
	private IVacunaDAO vacunaDAO;
	
	@EJB
	private LoteConverter loteConverter;
	
	@Override
	public List<LoteDTO> listar() throws VacunasUyException {
		try {
			return loteConverter.fromEntity(loteDAO.listar());
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public LoteDTO listarPorId(Long id) throws VacunasUyException {
		try {
			Lote lote = loteDAO.listarPorId(id);
			if(lote == null) throw new VacunasUyException("No existe el registro solicitado.", VacunasUyException.NO_EXISTE_REGISTRO);
			return loteConverter.fromEntity(lote);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public LoteDTO crear(LoteCrearDTO loteDTO) throws VacunasUyException {
		try {
			Lote lote = loteConverter.fromCrearDTO(loteDTO);
			Proveedor proveedor = proveedorDAO.listarPorId(loteDTO.getIdProveedor());
			if(proveedor == null) throw new VacunasUyException("No existe el proveedor ingresado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Vacuna vacuna = vacunaDAO.listarPorId(loteDTO.getIdVacuna());
			if(vacuna == null) throw new VacunasUyException("No existe la vacuna ingresada.", VacunasUyException.NO_EXISTE_REGISTRO);
			lote.setProveedor(proveedor);
			lote.setVacuna(vacuna);
			loteDAO.crear(lote);
			return loteConverter.fromEntity(lote);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public LoteDTO editar(Long id, LoteCrearDTO loteDTO) throws VacunasUyException {
		try {
			Lote lote = loteDAO.listarPorId(id);
			if(lote == null) throw new VacunasUyException("No existe el lote ingresado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Proveedor proveedor = proveedorDAO.listarPorId(loteDTO.getIdProveedor());
			if(proveedor == null) throw new VacunasUyException("No existe el proveedor ingresado.", VacunasUyException.NO_EXISTE_REGISTRO);
			Vacuna vacuna = vacunaDAO.listarPorId(loteDTO.getIdVacuna());
			if(vacuna == null) throw new VacunasUyException("No existe la vacuna ingresada.", VacunasUyException.NO_EXISTE_REGISTRO);
			lote.setProveedor(proveedor);
			lote.setVacuna(vacuna);
			loteDAO.editar(lote);
			return loteConverter.fromEntity(lote);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

	@Override
	public void eliminar(Long id) throws VacunasUyException {
		try {
			Lote lote = loteDAO.listarPorId(id);
			if(lote == null) throw new VacunasUyException("No existe el lote ingresado.", VacunasUyException.NO_EXISTE_REGISTRO);
			loteDAO.eliminar(lote);
		} catch (Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}

}
