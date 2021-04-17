package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.upm.dit.isst.educovid.aux.CryptoService;
import es.upm.dit.isst.educovid.aux.ECDSAKeys;
import es.upm.dit.isst.educovid.aux.PushMessage;
import es.upm.dit.isst.educovid.dao.UsuarioDAOImpl;
import es.upm.dit.isst.educovid.model.Usuario;

@Path("/notification")
public class NotificationResource {

	@GET
	@Path("/publicSigningKey")
	@Produces("application/octet-stream")
	public byte[] publicSigningKey() {
		return ECDSAKeys.getInstance().getPublicKeyUncompressed();
	}

	@POST
	@Path("/subscription/{userId}")
	public Response createSubscription(@PathParam("userId") String userId,
			@FormParam("subscriptionEndpoint") String subscriptionEndpoint, @FormParam("p256dh") String p256dh,
			@FormParam("auth") String auth) {
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
			String auth = usuario.getAuth();
			String p256dh = usuario.getP256dh();
			String origin = null;

			URI endpointURI = URI.create(subscriptionEndpoint);
			URL url = new URL(subscriptionEndpoint);
			origin = url.getProtocol() + "://" + url.getHost();

			Algorithm jwtAlgorithm = Algorithm.ECDSA256(ECDSAKeys.getInstance().getPublicKey(),
					ECDSAKeys.getInstance().getPrivateKey());
			Date today = new Date();
			Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);

			String token = JWT.create().withAudience(origin).withExpiresAt(expires)
					.withSubject("mailto:example@example.com").sign(jwtAlgorithm);

			Builder httpRequestBuilder = HttpRequest.newBuilder();
			CryptoService cryptoService = new CryptoService();
			ObjectMapper objectMapper = new ObjectMapper();
			byte[] body = cryptoService.encrypt(
					objectMapper.writeValueAsString(new PushMessage("Prueba", "Probando notificacion")), p256dh, auth,
					0);
			httpRequestBuilder.POST(BodyPublishers.ofByteArray(body)).header("Content-Type", "application/octet-stream")
					.header("Content-Encoding", "aes128gcm");
			HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "180")
					.header("Authorization", "vapid t=" + token + ", k=" + ECDSAKeys.getInstance().getPublicKeyBase64())
					.build();
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpResponse<Void> response = httpClient.send(request, BodyHandlers.discarding());

			switch (response.statusCode()) {
			case 201:
				System.out.println("Push message successfully sent");
				break;
			case 404:
			case 410:
				System.out.println("Subscription not found or gone");
				break;
			case 429:
				System.out.println("Too many requests");
				break;
			case 400:
				System.out.println("Invalid request");
				break;
			case 413:
				System.out.println("Payload size too large");
				break;
			default:
				System.out.println("Unhandled status code");
			}

			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
