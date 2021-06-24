package vacunasuy.componentecentral.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import vacunasuy.componentecentral.converter.TransportistaConverter;
import vacunasuy.componentecentral.dao.ITransportistaDAO;
import vacunasuy.componentecentral.dto.TransportistaCrearDTO;
import vacunasuy.componentecentral.dto.TransportistaDTO;
import vacunasuy.componentecentral.entity.Transportista;
import vacunasuy.componentecentral.exception.VacunasUyException;
import vacunasuy.componentecentral.util.Constantes;

@Stateless
public class TransportistaServiceImpl implements ITransportistaService {

    @EJB
	public ITransportistaDAO transportistaDAO;
    
    @EJB
	public TransportistaConverter transportistaConverter;
    
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
    	if(transportista==null) throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			return transportistaConverter.fromEntity(transportista);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
    @Override
	public TransportistaDTO crear(TransportistaCrearDTO transportistaDTO) throws VacunasUyException{
		try {
			Transportista transportista = transportistaConverter.fromCrearDTO(transportistaDTO);
			transportistaDAO.crear(transportista);
			registrarTransportistaPeriferico(transportista);
			return transportistaConverter.fromEntity(transportista);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}
	}
	
    @Override
	public TransportistaDTO editar(Long id, TransportistaCrearDTO transportistaDTO) throws VacunasUyException{
		// se valida que el transportista exista
    	Transportista transportista = transportistaDAO.listarPorId(id);
    	if(transportista==null) throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);		
		try {
			transportista.setNombre(transportistaDTO.getNombre());
			return transportistaConverter.fromEntity(transportistaDAO.editar(transportista));
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
	
    @Override
	public void eliminar (Long id) throws VacunasUyException{
		//se valida que el transportista exista
    	Transportista transportista = transportistaDAO.listarPorId(id);
    	if(transportista==null) throw new VacunasUyException("El transportista indicado no existe.", VacunasUyException.NO_EXISTE_REGISTRO);
		try {
			transportistaDAO.eliminar(transportista);
		}catch(Exception e) {
			throw new VacunasUyException(e.getLocalizedMessage(), VacunasUyException.ERROR_GENERAL);
		}		
	}
    
    /* Registra al transportista en el nodo periférico */
    private void registrarTransportistaPeriferico(Transportista transportista) {
		Client cliente = ClientBuilder.newClient();
		WebTarget target = cliente.target(Constantes.NODOS_PERIFERICOS_REST_URL+"/transportistas");
		TransportistaDTO transportistaPeriferico = TransportistaDTO.builder()
				.id(transportista.getId())
				.nombre(transportista.getNombre())
				.build();
		String response = target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.json(transportistaPeriferico), String.class);
		System.out.println(response);
	}

}
