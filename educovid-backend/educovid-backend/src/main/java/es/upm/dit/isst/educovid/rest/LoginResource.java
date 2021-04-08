package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Calendar;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/login")
public class LoginResource {

	@POST
	@Path("/profesor")
	public Response loginProfesor(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "profesor")) {
			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(username);
			String token = this.issueToken(profesor.getId().toString());
			return Response.status(Response.Status.OK).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/alumno")
	public Response loginAlumno(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "alumno")) {
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyMatNum(username);
			String token = this.issueToken(alumno.getId().toString());
			return Response.status(Response.Status.OK).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/responsable")
	public Response loginResponsable(@FormParam("username") String username, @FormParam("password") String password)
			throws URISyntaxException {
		if (authenticate(username, password, "responsable")) {
			ResponsableCOVID responsable = ResponsableDAOImpl.getInstance().readResponsablebyNIFNIE(username);
			String token = this.issueToken(responsable.getId().toString());
			return Response.status(Response.Status.OK).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
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

	private String issueToken(String login) {
		// Calculamos la fecha de expiración del token
		Date issueDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(issueDate);
		calendar.add(Calendar.MINUTE, 60);
		Date expireDate = calendar.getTime();

		// Creamos el token
		String jwtToken = Jwts.builder().setSubject(login).setIssuer("https://educovid.devve.space")
				.setIssuedAt(issueDate).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, RestSecurityFilter.KEY).compact();
		return jwtToken;
	}
}
