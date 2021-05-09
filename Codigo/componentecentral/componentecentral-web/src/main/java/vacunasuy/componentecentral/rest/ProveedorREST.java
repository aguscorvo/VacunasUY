package vacunasuy.componentecentral.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import vacunasuy.componentecentral.business.IProveedorService;
import vacunasuy.componentecentral.dto.ProveedorCrearDTO;
import vacunasuy.componentecentral.dto.ProveedorDTO;
import vacunasuy.componentecentral.exception.VacunasUyException;

@RequestScoped
@Path("/proveedores")
@Consumes("application/json")
@Produces("application/json")
public class ProveedorREST {
	
	@EJB
	IProveedorService proveedorService;
	
	@GET
//	@RecursoProtegidoJWT
	public Response listar() {
		RespuestaREST<List<ProveedorDTO>> respuesta = null;
		try {
			List<ProveedorDTO> proveedores = proveedorService.listar();
			respuesta = new RespuestaREST<List<ProveedorDTO>>(true, "Proveedores listados con éxito.", proveedores);
			return Response.ok(respuesta).build();
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<>(false, e.getLocalizedMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
		}		
	}
	
	@GET
	@Path("/{id}")
//	@RecursoProtegidoJWT
	public Response listarPorId(@PathParam("id") Long id) {
		RespuestaREST<ProveedorDTO> respuesta = null;
		try {
			ProveedorDTO proveedor = proveedorService.listarPorId(id);
			respuesta = new RespuestaREST<ProveedorDTO>(true, "Proveedor listado con éxito.", proveedor);
			return Response.ok(respuesta).build();
		}catch(VacunasUyException e) {
			respuesta = new RespuestaREST<ProveedorDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@POST
	public Response crear(ProveedorCrearDTO request) {
		RespuestaREST<ProveedorDTO> respuesta = null;
		try {
			ProveedorDTO proveedor = proveedorService.crear(request);
			respuesta = new RespuestaREST<ProveedorDTO>(true, "Proveedor creado con éxito.", proveedor);
			return Response.ok(respuesta).build();
			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ProveedorDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}
	}
	
	@PUT
	@Path("/editar/{id}")
//	@RecursoProtegidoJWT
	public Response editar (@PathParam("id") Long id, ProveedorCrearDTO request) {
		RespuestaREST<ProveedorDTO> respuesta = null;
		try {
			ProveedorDTO proveedor = proveedorService.editar(id, request);
			respuesta = new RespuestaREST<ProveedorDTO>(true, "Proveedor editado con éxito.", proveedor);
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ProveedorDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}
	
	@DELETE
	@Path("/eliminar/{id}")
//	@RecursoProtegidoJWT
	public Response eliminar(@PathParam("id") Long id) {
		RespuestaREST<ProveedorDTO> respuesta = null;
		try {
			proveedorService.eliminar(id);
			respuesta = new RespuestaREST<ProveedorDTO>(true, "Proveedor eliminado con éxito");
			return Response.ok(respuesta).build();			
		}catch (VacunasUyException e) {
			respuesta = new RespuestaREST<ProveedorDTO>(false, e.getLocalizedMessage());
			if(e.getCodigo() == VacunasUyException.NO_EXISTE_REGISTRO) {
				return Response.status(Response.Status.BAD_REQUEST).entity(respuesta).build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(respuesta).build();
			}
		}	
	}

}
