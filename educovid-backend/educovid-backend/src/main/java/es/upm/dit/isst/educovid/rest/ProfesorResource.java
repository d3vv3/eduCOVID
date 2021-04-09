package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;

@Path("/professor")
public class ProfesorResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProfesor(Profesor newProfesor) throws URISyntaxException{
		Profesor p = ProfesorDAOImpl.getInstance().createProfesor(newProfesor);
		if (p != null) {
			URI uri = new URI("/educovid-backend/rest/profesor/" + p.getId());
			return Response.created(uri).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@Path("/{NIFNIE}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readProfesorByNIFNIE(@PathParam("NIFNIE") String NIFNIE) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(NIFNIE);
		if (p == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(p, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/{NIFNIE}")
	public Response updateProfesor(@PathParam("NIFNIE") String NIFNIE, Profesor p) {
		Profesor pold = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(NIFNIE);
	    if ((pold == null) || (! pold.getNifNie().equals(p.getNifNie()))) {
	    	return Response.notModified().build();
	    }
	    ProfesorDAOImpl.getInstance().updateProfesor(p);
	    return Response.ok().build();
	}
	
	@DELETE
	@Path("/{NIFNIE}")
	public Response deleteProfesor(@PathParam("NIFNIE") String NIFNIE) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(NIFNIE);
		if (p == null) {
			return Response.notModified().build();
		}
		ProfesorDAOImpl.getInstance().deleteProfesor(p);
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/estadoSanitario/{estadoSanitario}")
	public List<Profesor> readAllProfesorbyEstadoSanitario(@PathParam("estadoSanitario") String estadoSanitario) {
		return ProfesorDAOImpl.getInstance().readAllProfesorbyEstadoSanitario(estadoSanitario);
	}

}
