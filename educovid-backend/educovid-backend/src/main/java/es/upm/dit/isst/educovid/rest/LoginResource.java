package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
	
	@POST
	@Path("/profesor")
	public Response loginProfesor(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		if (authenticate(username, password, "profesor")) {
			return Response.status(Response.Status.OK).build();
		}
		
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/alumno")
	public Response loginAlumno(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		if (authenticate(username, password, "alumno")) {
			return Response.status(Response.Status.OK).build();
		}
		
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/responsable")
	public Response loginResponsable(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		if (authenticate(username, password, "responsable")) {
			return Response.status(Response.Status.OK).build();
		}
		
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	// TODO: Search user and return true if exists, false otherwise. DAOs needed
	private Boolean authenticate(String username, String password, String role) {
		switch(role) {
		case "profesor":
			return true;
		case "alumno":
			return false;
		case "responsable":
			return true;
		default:
			return false;
		}
	}
}
