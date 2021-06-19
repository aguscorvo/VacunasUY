import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import vacunasuy.componentecentral.business.RolServiceImpl;
import vacunasuy.componentecentral.converter.RolConverter;
import vacunasuy.componentecentral.dao.RolDAOImpl;
import vacunasuy.componentecentral.dto.LocalidadDTO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.entity.Localidad;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RunWith(MockitoJUnitRunner.class)
public class RolServiceTest {

	/* Servicio que se quiere probar */
	@InjectMocks
	private RolServiceImpl rolService;
	
	/* 
	 * Mocks que se desean realizar, serían los @EJB que se necesiten en los métodos a probar
	 * Deben declararse como public en el service 
	*/
	@Mock
	private RolDAOImpl rolDAO;
	
	@Mock
	private RolConverter rolConverter;
	
	@Before
	public void init() {
		rolService = new RolServiceImpl();
		rolService.rolDAO = this.rolDAO;
		rolService.rolConverter = this.rolConverter;
	}
	
	/* Se prueba el método listar */
	@Test
	public void listar() {
		List<Rol> roles = new ArrayList<Rol>();
		roles.add(new Rol(1L, "Prueba"));
		List<RolDTO> rolesDTO = new ArrayList<RolDTO>();
		rolesDTO.add(new RolDTO(1L, "Prueba"));
		Mockito.when(rolService.rolDAO.listar()).thenReturn(roles);
		Mockito.when(rolService.rolConverter.fromEntity(Mockito.anyList())).thenReturn(rolesDTO);
		try {
			List<RolDTO> rolesEsperados = rolService.listar();
			assertEquals(1, rolesEsperados.size());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	/* Se prueba el método listar por id */
	@Test
	public void listarPorId() {
		Rol rol = new Rol(1L, "Prueba");
		RolDTO r3 = new RolDTO(1L, "Prueba");
		Mockito.when(rolService.rolDAO.listarPorId(1L)).thenReturn(rol);
		Mockito.when(rolService.rolConverter.fromEntity(rol)).thenReturn(r3);
		try {
			RolDTO rol1 = rolService.listarPorId(1L);
			assertEquals(rol.getId(), rol1.getId());
		} catch (VacunasUyException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = VacunasUyException.class)
	public void listarPorId_null () throws VacunasUyException {
		Rol rol = null;
		Mockito.when(rolService.rolDAO.listarPorId(1L)).thenReturn(rol);
		@SuppressWarnings("unused")
		RolDTO rolDTO = rolService.listarPorId(1L);	
	}
}
