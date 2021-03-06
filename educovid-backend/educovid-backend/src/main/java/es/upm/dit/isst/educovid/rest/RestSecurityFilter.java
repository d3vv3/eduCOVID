package es.upm.dit.isst.educovid.rest;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class RestSecurityFilter implements ContainerRequestFilter {

	public static final Key KEY = MacProvider.generateKey();

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Recupera la cabecera HTTP Authorization de la petición
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		try {
			// Extrae el token de la cabecera
			String token = authorizationHeader.substring("Bearer".length()).trim();

			// Valida el token utilizando la cadena secreta
			Jws<Claims> jwt = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
			// System.out.println(jwt);
			// System.out.println(jwt.getBody().getSubject());
			String userId = jwt.getBody().getSubject();
			String center = (String) jwt.getBody().get("center");

			// Get the role
			String role = "";
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(userId);
			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyId(userId);
			ResponsableCOVID responsable = ResponsableDAOImpl.getInstance().readResponsablebyId(userId);
			if (alumno != null) {
				role = "alumno";
			} else if (profesor != null) {
				role = "profesor";
			} else if (responsable != null) {
				role = "responsable";
			}

			SecurityContext originalContext = requestContext.getSecurityContext();
			Set<String> roles = new HashSet<>();
			roles.add(role);
			Authorizer authorizer = new Authorizer(roles, Long.parseLong(userId), "admin", center,
					originalContext.isSecure());
			requestContext.setSecurityContext(authorizer);

		} catch (Exception e) {
			//e.printStackTrace();
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	public static class Authorizer implements SecurityContext {

		Set<String> roles;
		Long id;
		String username;
		String center;
		boolean isSecure;

		public Authorizer(Set<String> roles, final Long id, final String username, final String center,
				boolean isSecure) {
			this.id = id;
			this.roles = roles;
			this.username = username;
			this.center = center;
			this.isSecure = isSecure;
		}

		@Override
		public Principal getUserPrincipal() {
			return new SecurityUser(id, username, center);
		}

		@Override
		public boolean isUserInRole(String role) {
			return roles.contains(role);
		}

		@Override
		public boolean isSecure() {
			return isSecure;
		}

		@Override
		public String getAuthenticationScheme() {
			return "Credentials";
		}
	}

	public static class SecurityUser implements Principal {
		Long id;
		String name;
		String center;

		public SecurityUser(Long id, String name, String center) {
			this.id = id;
			this.name = name;
			this.center = center;
		}

		public Long getId() {
			return id;
		}

		public String getCenter() {
			return center;
		}

		@Override
		public String getName() {
			return name;
		}
	}

}
