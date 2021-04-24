package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections4.map.HashedMap;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import es.upm.dit.isst.educovid.rest.RestSecurityFilter.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/login")
public class LoginResource {

	@GET
	@Secured
	@Path("/session/center")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginRetrieveSessionCenter(@Context SecurityContext securityContext) {
		try {
			SecurityUser user = (SecurityUser) securityContext.getUserPrincipal();
			return Response.status(Response.Status.OK).entity(user.getCenter()).build();
		} catch(Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Secured
	@Path("/session")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginRetrieveSession(@Context SecurityContext securityContext) {
		try {
			SecurityUser user = (SecurityUser) securityContext.getUserPrincipal();
			if (securityContext.isUserInRole("alumno")) {
				Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(user.getId().toString());
				if (alumno == null)
					return Response.status(Response.Status.UNAUTHORIZED).build();
				String token = this.issueToken(alumno.getId().toString(), user.getCenter());
				alumno.setHash(token);
				alumno.setSalt("alumno");
				return Response.status(Response.Status.OK).entity(alumno).build();
			} else if (securityContext.isUserInRole("profesor")) {
				Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyId(user.getId().toString());
				if (profesor == null)
					return Response.status(Response.Status.UNAUTHORIZED).build();
				String token = this.issueToken(profesor.getId().toString(), user.getCenter());
				profesor.setHash(token);
				profesor.setSalt("profesor");
				return Response.status(Response.Status.OK).entity(profesor).build();
			} else if (securityContext.isUserInRole("responsable")) {
				ResponsableCOVID responsable = ResponsableDAOImpl.getInstance()
						.readResponsablebyId(user.getId().toString());
				if (responsable == null)
					return Response.status(Response.Status.UNAUTHORIZED).build();
				String token = this.issueToken(responsable.getId().toString(), user.getCenter());
				responsable.setHash(token);
				responsable.setSalt("responsable");
				return Response.status(Response.Status.OK).entity(responsable).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Path("/profesor")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginProfesor(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("center") String center) throws URISyntaxException {
		if (authenticate(username, password, center, "profesor")) {
			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(username);
			String token = this.issueToken(profesor.getId().toString(), center);
			// System.out.print(token);
			profesor.setHash(token);
			profesor.setSalt("");
			return Response.status(Response.Status.OK).entity(profesor).build();
		}
		System.out.print("Not valid profesor");
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/alumno")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginAlumno(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("center") String center) throws URISyntaxException {
		if (authenticate(username, password, center, "alumno")) {
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyMatNumCenter(username, center);
			String token = this.issueToken(alumno.getId().toString(), center);
			// System.out.print(token);
			alumno.setHash(token);
			alumno.setSalt("");
			return Response.status(Response.Status.OK).entity(alumno).build();
		}
		System.out.print("Not valid alumno");
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("/responsable")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginResponsable(@FormParam("username") String username, @FormParam("password") String password,
			@FormParam("center") String center) throws URISyntaxException {
		if (authenticate(username, password, center, "responsable")) {
			ResponsableCOVID responsable = ResponsableDAOImpl.getInstance().readResponsablebyNIFNIE(username);
			String token = this.issueToken(responsable.getId().toString(), center);
			responsable.setHash(token);
			responsable.setSalt("");
			return Response.status(Response.Status.OK).entity(responsable).build();
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	private Boolean authenticate(String username, String password, String center, String role) {
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(center);
		// System.out.println("Nombre del centro recuperado: " + centro.getNombre());
		if (centro == null)
			return false;
		switch (role) {
		case "profesor":
			// Get profesor by username
			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(username);
			if (!validCentroProfesor(profesor, center))
				return false;
			if (profesor == null)
				return false;
			if (!Security.checkPassword(password, profesor.getHash(), profesor.getSalt()))
				return false;
			return true;
		case "alumno":
			// Get alumno by username
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyMatNumCenter(username, center);
			if (alumno == null)
				return false;
			if (!Security.checkPassword(password, alumno.getHash(), alumno.getSalt()))
				return false;
			return true;
		case "responsable":
			// Get responsable by username
			ResponsableCOVID responsable = ResponsableDAOImpl.getInstance().readResponsablebyNIFNIE(username);
			if (!validCentroResponsable(responsable, centro))
				return false;
			if (responsable == null)
				return false;
			if (!Security.checkPassword(password, responsable.getHash(), responsable.getSalt()))
				return false;
			return true;
		default:
			return false;
		}
	}

	private String issueToken(String id, String center) {
		// Calculamos la fecha de expiración del token
		Date issueDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(issueDate);
		calendar.add(Calendar.MINUTE, 60);
		Date expireDate = calendar.getTime();

		// Creamos el token
		Map<String, Object> tokenData = new HashedMap<>();
		tokenData.put("center", center);
		tokenData.put("sub", id);
		String jwtToken = Jwts.builder().setSubject(id).setIssuer("https://educovid.devve.space").setClaims(tokenData)
				.setIssuedAt(issueDate).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, RestSecurityFilter.KEY).compact();
		return jwtToken;
	}

	private Boolean validCentroProfesor(Profesor profesor, String nombreCentro) {
		for (CentroEducativo centro : CentroEducativoDAOImpl.getInstance().readAllCentroEducativo()) {
			for (Clase clase : centro.getClases()) {
//				System.out.println("Contains profesor: " + clase.getProfesores().contains(profesor));
//				System.out.println("Same centro: "
//						+ (centro.getNombre().trim().toLowerCase().equals(nombreCentro.trim().toLowerCase())));
//				System.out.println("Profesores de clase: " + clase.getProfesores());
//				System.out.println("Profesor: " + profesor);
				if (centro.getNombre().trim().toLowerCase().equals(nombreCentro.trim().toLowerCase())) {
					for (Profesor profesorAux : clase.getProfesores()) {
						if (profesorAux.getId().equals(profesor.getId())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private Boolean validCentroResponsable(ResponsableCOVID responsable, CentroEducativo centro) {
		for (CentroEducativo centroAux : responsable.getCentros()) {
			if (centroAux.getId().equals(centro.getId())) {
				return true;
			}
		}
		return false;
	}
}
