package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import vacunasuy.componentecentral.converter.PaisConverter;
import vacunasuy.componentecentral.dao.IPaisDAO;
import vacunasuy.componentecentral.dto.PaisDTO;
import vacunasuy.componentecentral.entity.Pais;
import vacunasuy.componentecentral.exception.VacunasUyException;

@Stateless
public class PaisServiceImpl implements IPaisService {
    
   @EJB
public IPaisDAO paisDAO;
   
   @EJB
public PaisConverter paisConverter;
   
   @Override
   public List<PaisDTO> listar() throws VacunasUyException{
	   try {
		   return paisConverter.fromEntity(paisDAO.listar());
	   }catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
   }
   
   @Override
   public PaisDTO listarPorId(Long id) throws VacunasUyException{
	   //se valida que exista el pais
	   Pais pais = paisDAO.listarPorId(id);
	   if(pais==null) throw new VacunasUyException("El pais indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
	   try {
		   return paisConverter.fromEntity(pais);
	   }catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
	   }
   }

}
