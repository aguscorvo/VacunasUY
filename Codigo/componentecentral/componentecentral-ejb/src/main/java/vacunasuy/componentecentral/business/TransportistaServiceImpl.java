package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.TransportistaConverter;
import vacunasuy.componentecentral.dao.ITransportistaDAO;
import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class TransportistaServiceImpl implements ITransportistaService {

    @EJB
    private ITransportistaDAO transportistaDAO;
    
    @EJB
    private TransportistaConverter transportistaConverter;
    
    @Override
    public List<TransportistaDTO> listar( ) throws VacunasUyException{
    	try {
    		return transportistaConverter.fromEntity(transportistaDAO.listar());
    	}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
    }
    
    @Override
	public TransportistaDTO listarPorId(Long id) throws VacunasUyException{
		//se valida que el transportista exista
    	Transportista transportista = transportistaDAO.listarPorId(id);
    	if(transportista==null) {
			throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				return transportistaConverter.fromEntity(transportista);
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
    @Override
	public TransportistaDTO crear(TransportistaCrearDTO transportistaDTO) throws VacunasUyException{
		try {
			Transportista transportista = transportistaConverter.fromCrearDTO(transportistaDTO);
			return transportistaConverter.fromEntity(transportistaDAO.crear(transportista));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
    @Override
	public TransportistaDTO editar(Long id, TransportistaCrearDTO transportistaDTO) throws VacunasUyException{
		// se valida que el transportista exista
    	Transportista transportista = transportistaDAO.listarPorId(id);
    	if(transportista==null) {
			throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				transportista.setNombre(transportistaDTO.getNombre());
				return transportistaConverter.fromEntity(transportistaDAO.editar(transportista));
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}
	
    @Override
	public void eliminar (Long id) throws VacunasUyException{
		//se valida que el transportista exista
    	Transportista transportista = transportistaDAO.listarPorId(id);
    	if(transportista==null) {
			throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		}else {
			try {
				transportistaDAO.eliminar(transportista);
			}catch(Exception e) {
				throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
			}
		}
	}

}
