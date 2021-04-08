package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

@Path("/login")
public class LoginResource {

	@POST
	@Path("/profesor")
	public Response loginProfesor(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "profesor")) {
			return Response.status(Response.Status.OK).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/alumno")
	public Response loginAlumno(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "alumno")) {
			return Response.status(Response.Status.OK).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/responsable")
	public Response loginResponsable(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "responsable")) {
			return Response.status(Response.Status.OK).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	private Boolean authenticate(String username, String password, String role) {
		switch (role) {
		case "profesor":
			// Get profesor by username
			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(username);
			if (profesor == null)
				return false;
			if (!profesor.checkPassword(password))
				return false;
			return true;
		case "alumno":
			// Get alumno by username
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyMatNum(username);
			if (alumno == null)
				return false;
			if (!alumno.checkPassword(password))
				return false;
			return true;
		case "responsable":
			// Get responsable by username
			ResponsableCOVID responsable = ResponsableDAOImpl.getInstance().readResponsablebyNIFNIE(username);
			if (responsable == null)
				return false;
			if (!responsable.checkPassword(password))
				return false;
			return true;
		default:
			return false;
		}
	}
}
