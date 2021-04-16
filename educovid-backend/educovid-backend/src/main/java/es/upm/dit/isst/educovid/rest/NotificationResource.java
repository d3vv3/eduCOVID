package es.upm.dit.isst.educovid.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.UsuarioDAOImpl;
import es.upm.dit.isst.educovid.model.Usuario;

@Path("/notification")
public class NotificationResource {

	@POST
	@Path("/subscription/{userId}")
	public Response createSubscription(@PathParam("userId") String userId, @FormParam("subscriptionEndpoint") String subscriptionEndpoint) {
		Usuario usuario = UsuarioDAOImpl.getInstance().readUsuariobyId(Integer.parseInt(userId));
		usuario.setSubscriptionEndpoint(subscriptionEndpoint);
		UsuarioDAOImpl.getInstance().updateUsuario(usuario);
		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("/subscription/{userId}")
	public Response readSubscription(@PathParam("userId") String userId) {
		Usuario usuario = UsuarioDAOImpl.getInstance().readUsuariobyId(Integer.parseInt(userId));
		String subscriptionEndpoint = usuario.getSubscriptionEndpoint();
		// TODO: Make POST to that subscription endpoint
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build(); // TODO: Change when finished
	}
	
}
