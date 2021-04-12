package es.upm.dit.isst.educovid.rest;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import es.upm.dit.isst.educovid.anotation.Secured;
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
			Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);

		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}
}
