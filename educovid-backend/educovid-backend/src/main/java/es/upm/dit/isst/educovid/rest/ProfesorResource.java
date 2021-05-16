package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.Profesor;

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
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readProfesorByNIFNIE(@PathParam("id") String id) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyId(id);
		if (p == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(p, MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateProfesor(@PathParam("id") String id, Profesor p) {
		Profesor pold = ProfesorDAOImpl.getInstance().readProfesorbyId(id);
	    if ((pold == null)) {
	    	return Response.notModified().build();
	    }
		System.out.println("UPDATING PROFESOR");
		String salt = Security.getSalt();
		String hash = Security.getHash(p.getNifNie(), salt);
		p.setSalt(salt);
		p.setHash(hash);
	    ProfesorDAOImpl.getInstance().updateProfesor(p);
	    return Response.ok(p, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteProfesor(@PathParam("id") String id) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyId(id);
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/professors/{nombreCentro}/{nombreClase}")
	public Set<Profesor> readAllProfesorbyClase(@PathParam("nombreCentro") String nombreCentro, @PathParam("nombreClase") String nombreClase) {
		List<Clase> clasesCentro = ClaseDAOImpl.getInstance().readAllClases(nombreCentro);
		for (Clase c : clasesCentro) {
			if (c.getNombre().equals(nombreClase)) return c.getProfesores();
		}
		return new HashSet<Profesor>();
	}
	
}
