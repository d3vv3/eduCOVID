package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.UsuarioDAOImpl;
import es.upm.dit.isst.educovid.model.Usuario;

@Path("/notification")
public class NotificationResource {

	@POST
	@Path("/subscription/{userId}")
	public Response createSubscription(@PathParam("userId") String userId,
			@FormParam("subscriptionEndpoint") String subscriptionEndpoint) {
		Usuario usuario = UsuarioDAOImpl.getInstance().readUsuariobyId(Integer.parseInt(userId));
		usuario.setSubscriptionEndpoint(subscriptionEndpoint);
		UsuarioDAOImpl.getInstance().updateUsuario(usuario);
		System.out.println("Subscription endpoint: " + subscriptionEndpoint);
		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/subscription/{userId}")
	public Response readSubscription(@PathParam("userId") String userId) {
		try {
			Usuario usuario = UsuarioDAOImpl.getInstance().readUsuariobyId(Integer.parseInt(userId));
			String subscriptionEndpoint = usuario.getSubscriptionEndpoint();
			String inputJson = "{ \"name\":\"tammy133\", \"salary\":\"5000\", \"age\":\"20\" }";

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(subscriptionEndpoint))
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(inputJson))
					.build();

			HttpClient client = HttpClient.newHttpClient();

			HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
