import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import vacunasuy.componentecentral.business.IRolService;
import vacunasuy.componentecentral.business.RolServiceImpl;
import vacunasuy.componentecentral.dao.IRolDAO;
import vacunasuy.componentecentral.dto.RolDTO;
import vacunasuy.componentecentral.entity.Rol;
import vacunasuy.componentecentral.exception.VacunasUyException;

public class PruebaTest {

	/* ¿Se prueba la intefaz o la clase? */
	@InjectMocks
	private RolServiceImpl rolService;
	
	/* ¿Se mockea la interfaz? */
	@Mock
	private IRolDAO rolDAO;
	
	@Before
	public void init() {
		rolService = new RolServiceImpl();
	}
	
	@Test
	public void listarPorId() {
		Rol rol = new Rol(1L, "Prueba");
		Mockito.when(rolDAO.listarPorId(1L)).thenReturn(rol);
		RolDTO rol1;
		try {
			rol1 = rolService.listarPorId(1L);
			assertEquals(rol.getId(), rol1.getId());
		} catch (VacunasUyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
