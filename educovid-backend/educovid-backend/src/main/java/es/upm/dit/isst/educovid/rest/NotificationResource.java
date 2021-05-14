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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.upm.dit.isst.educovid.aux.CryptoService;
import es.upm.dit.isst.educovid.aux.ECDSAKeys;
import es.upm.dit.isst.educovid.aux.PushMessage;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/notification")
public class NotificationResource {

	@GET
	@Path("/publicSigningKey")
	@Produces("application/octet-stream")
	public byte[] publicSigningKey() {
		return ECDSAKeys.getInstance().getPublicKeyUncompressed();
	}

	@POST
	@Path("/subscription/alumno/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSubscriptionAlumno(@PathParam("userId") String userId, String JSONBodyString) {

		System.out.println(JSONBodyString);
		JSONObject body = new JSONObject(JSONBodyString);
		String subscriptionEndpoint = body.getString("subscriptionEndpoint");
		String auth = body.getString("auth");
		String p256dh = body.getString("p256dh");
		Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(userId);
		System.out.println("POST Subscription Endpoint: " + subscriptionEndpoint);
		System.out.println("POST Auth: " + auth);
		System.out.println("POST p256dh: " + p256dh);
		alumno.setSubscriptionEndpoint(subscriptionEndpoint);
		alumno.setAuth(auth);
		alumno.setP256dh(p256dh);
		AlumnoDAOImpl.getInstance().updateAlumno(alumno);
		System.out.println("Subscription endpoint: " + subscriptionEndpoint);
		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Path("/subscription/students/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response testreadSubscriptionAlumno(@PathParam("userId") String userId, String JSONBodyString) {
		try {
			JSONObject jsonBody = new JSONObject(JSONBodyString);
			String confineMessage = jsonBody.getString("confineMessage");
			String unconfineMessage = jsonBody.getString("unconfineMessage");
			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(userId);
			System.out.println("Usuario obtenido: " + alumno.getNombre());
			String subscriptionEndpoint = alumno.getSubscriptionEndpoint();
			String auth = alumno.getAuth();
			String p256dh = alumno.getP256dh();
			String origin = null;
			System.out.println("Subscription Endpoint: " + subscriptionEndpoint);
			System.out.println("Auth: " + auth);
			System.out.println("p256dh: " + p256dh);

			URI endpointURI = URI.create(subscriptionEndpoint);
			URL url = new URL(subscriptionEndpoint);
			origin = url.getProtocol() + "://" + url.getHost();
			System.out.println("Origin: " + origin);

			Algorithm jwtAlgorithm = Algorithm.ECDSA256(ECDSAKeys.getInstance().getPublicKey(),
					ECDSAKeys.getInstance().getPrivateKey());
			Date today = new Date();
			Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);

			String token = JWT.create().withAudience(origin).withExpiresAt(expires)
					.withSubject("mailto:example@example.com").sign(jwtAlgorithm);
			System.out.println("JWT: " + token);

			Builder httpRequestBuilder = HttpRequest.newBuilder();
			CryptoService cryptoService = new CryptoService();
			ObjectMapper objectMapper = new ObjectMapper();
			String msg = "";
			if (alumno.getEstadoSanitario().equals("confinado")) {
				msg = confineMessage;
			} else {
				msg = unconfineMessage;
			}
			byte[] body = cryptoService.encrypt(objectMapper.writeValueAsString(new PushMessage("eduCOVID", msg)),
					p256dh, auth, 0);
			httpRequestBuilder.POST(BodyPublishers.ofByteArray(body)).header("Content-Type", "application/octet-stream")
					.header("Content-Encoding", "aes128gcm");
			HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "2419200")
					.header("Authorization", "vapid t=" + token + ", k=" + ECDSAKeys.getInstance().getPublicKeyBase64())
					.build();
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpResponse<Void> response = httpClient.send(request, BodyHandlers.discarding());
			System.out.println("Response status code from push API: " + response.statusCode());

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
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
//	@GET
//	@Path("/subscription/students/{userId}")
//	public Response readSubscriptionAlumno(@PathParam("userId") String userId) {
//		try {
//
//			Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(userId);
//			System.out.println("Usuario obtenido: " + alumno.getNombre());
//			String subscriptionEndpoint = alumno.getSubscriptionEndpoint();
//			String auth = alumno.getAuth();
//			String p256dh = alumno.getP256dh();
//			String origin = null;
//			System.out.println("Subscription Endpoint: " + subscriptionEndpoint);
//			System.out.println("Auth: " + auth);
//			System.out.println("p256dh: " + p256dh);
//
//			URI endpointURI = URI.create(subscriptionEndpoint);
//			URL url = new URL(subscriptionEndpoint);
//			origin = url.getProtocol() + "://" + url.getHost();
//			System.out.println("Origin: " + origin);
//
//			Algorithm jwtAlgorithm = Algorithm.ECDSA256(ECDSAKeys.getInstance().getPublicKey(),
//					ECDSAKeys.getInstance().getPrivateKey());
//			Date today = new Date();
//			Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);
//
//			String token = JWT.create().withAudience(origin).withExpiresAt(expires)
//					.withSubject("mailto:example@example.com").sign(jwtAlgorithm);
//			System.out.println("JWT: " + token);
//
//			Builder httpRequestBuilder = HttpRequest.newBuilder();
//			CryptoService cryptoService = new CryptoService();
//			ObjectMapper objectMapper = new ObjectMapper();
//			String msg = "";
//			if (alumno.getEstadoSanitario().equals("confinado")) {
//				msg = "Has sido confinado";
//			} else {
//				msg = "Has sido desconfinado";
//			}
//			byte[] body = cryptoService.encrypt(objectMapper.writeValueAsString(new PushMessage("eduCOVID", msg)),
//					p256dh, auth, 0);
//			httpRequestBuilder.POST(BodyPublishers.ofByteArray(body)).header("Content-Type", "application/octet-stream")
//					.header("Content-Encoding", "aes128gcm");
//			HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "2419200")
//					.header("Authorization", "vapid t=" + token + ", k=" + ECDSAKeys.getInstance().getPublicKeyBase64())
//					.build();
//			HttpClient httpClient = HttpClient.newHttpClient();
//			HttpResponse<Void> response = httpClient.send(request, BodyHandlers.discarding());
//			System.out.println("Response status code from push API: " + response.statusCode());
//
//			switch (response.statusCode()) {
//			case 201:
//				System.out.println("Push message successfully sent");
//				break;
//			case 404:
//			case 410:
//				System.out.println("Subscription not found or gone");
//				break;
//			case 429:
//				System.out.println("Too many requests");
//				break;
//			case 400:
//				System.out.println("Invalid request");
//				break;
//			case 413:
//				System.out.println("Payload size too large");
//				break;
//			default:
//				System.out.println("Unhandled status code");
//			}
//
//			return Response.status(Response.Status.OK).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@POST
	@Path("/subscription/profesor/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSubscriptionProfesor(@PathParam("userId") String userId, String JSONBodyString) {

		System.out.println(JSONBodyString);
		JSONObject body = new JSONObject(JSONBodyString);
		String subscriptionEndpoint = body.getString("subscriptionEndpoint");
		String auth = body.getString("auth");
		String p256dh = body.getString("p256dh");
		Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyId(userId);
		profesor.setSubscriptionEndpoint(subscriptionEndpoint);
		profesor.setAuth(auth);
		profesor.setP256dh(p256dh);
		ProfesorDAOImpl.getInstance().updateProfesor(profesor);
		System.out.println("Subscription endpoint: " + subscriptionEndpoint);
		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("/subscription/professors/{userId}")
	public Response readSubscriptionProfesor(@PathParam("userId") String userId) {
		try {

			Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyId(userId);
			System.out.println("Usuario obtenido: " + profesor);
			String subscriptionEndpoint = profesor.getSubscriptionEndpoint();
			String auth = profesor.getAuth();
			String p256dh = profesor.getP256dh();
			String origin = null;
			System.out.println("Subscription Endpoint: " + subscriptionEndpoint);
			System.out.println("Auth: " + auth);
			System.out.println("p256dh: " + p256dh);

			URI endpointURI = URI.create(subscriptionEndpoint);
			URL url = new URL(subscriptionEndpoint);
			origin = url.getProtocol() + "://" + url.getHost();
			System.out.println("Origin: " + origin);

			Algorithm jwtAlgorithm = Algorithm.ECDSA256(ECDSAKeys.getInstance().getPublicKey(),
					ECDSAKeys.getInstance().getPrivateKey());
			Date today = new Date();
			Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);

			String token = JWT.create().withAudience(origin).withExpiresAt(expires)
					.withSubject("mailto:example@example.com").sign(jwtAlgorithm);
			System.out.println("JWT: " + token);

			Builder httpRequestBuilder = HttpRequest.newBuilder();
			CryptoService cryptoService = new CryptoService();
			ObjectMapper objectMapper = new ObjectMapper();
			String msg = "";
			if (profesor.getEstadoSanitario().equals("confinado")) {
				msg = "Has sido confinado";
			} else {
				msg = "Has sido desconfinado";
			}
			byte[] body = cryptoService.encrypt(objectMapper.writeValueAsString(new PushMessage("eduCOVID", msg)),
					p256dh, auth, 0);
			httpRequestBuilder.POST(BodyPublishers.ofByteArray(body)).header("Content-Type", "application/octet-stream")
					.header("Content-Encoding", "aes128gcm");
			HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "180")
					.header("Authorization", "vapid t=" + token + ", k=" + ECDSAKeys.getInstance().getPublicKeyBase64())
					.build();
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpResponse<Void> response = httpClient.send(request, BodyHandlers.discarding());
			System.out.println("Response status code from push API: " + response.statusCode());

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
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/subscription/bubbleGroups/{groupId}")
	public Response readSubscriptionGrupo(@PathParam("groupId") String groupId) {
		try {
			GrupoBurbuja grupo = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(groupId);
			for (Alumno alumno : grupo.getAlumnos()) {
				try {
					if (alumno.getAuth() == null) continue;
					System.out.println("Usuario obtenido: " + alumno);
					String subscriptionEndpoint = alumno.getSubscriptionEndpoint();
					String auth = alumno.getAuth();
					String p256dh = alumno.getP256dh();
					String origin = null;
					System.out.println("Subscription Endpoint: " + subscriptionEndpoint);
					System.out.println("Auth: " + auth);
					System.out.println("p256dh: " + p256dh);

					URI endpointURI = URI.create(subscriptionEndpoint);
					URL url = new URL(subscriptionEndpoint);
					origin = url.getProtocol() + "://" + url.getHost();
					System.out.println("Origin: " + origin);

					Algorithm jwtAlgorithm = Algorithm.ECDSA256(ECDSAKeys.getInstance().getPublicKey(),
							ECDSAKeys.getInstance().getPrivateKey());
					Date today = new Date();
					Date expires = new Date(today.getTime() + 12 * 60 * 60 * 1000);

					String token = JWT.create().withAudience(origin).withExpiresAt(expires)
							.withSubject("mailto:example@example.com").sign(jwtAlgorithm);
					System.out.println("JWT: " + token);

					Builder httpRequestBuilder = HttpRequest.newBuilder();
					CryptoService cryptoService = new CryptoService();
					ObjectMapper objectMapper = new ObjectMapper();
					String msg = "";
					if (alumno.getEstadoSanitario().equals("confinado")) {
						msg = "Has sido confinado";
					} else {
						msg = "Has sido desconfinado";
					}
					byte[] body = cryptoService.encrypt(
							objectMapper.writeValueAsString(new PushMessage("eduCOVID", msg)), p256dh, auth, 0);
					httpRequestBuilder.POST(BodyPublishers.ofByteArray(body))
							.header("Content-Type", "application/octet-stream").header("Content-Encoding", "aes128gcm");
					HttpRequest request = httpRequestBuilder.uri(endpointURI).header("TTL", "180")
							.header("Authorization",
									"vapid t=" + token + ", k=" + ECDSAKeys.getInstance().getPublicKeyBase64())
							.build();
					HttpClient httpClient = HttpClient.newHttpClient();
					HttpResponse<Void> response = httpClient.send(request, BodyHandlers.discarding());
					System.out.println("Response status code from push API: " + response.statusCode());

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
					e.printStackTrace();
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}
			}
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/subscription/exists/alumno/{userId}")
	public Response readAlreadySubscribed(@PathParam("userId") String userId) {
		if (Integer.parseInt(userId) != -1) {
			try {
				Alumno alumno = AlumnoDAOImpl.getInstance().readAlumnobyId(userId);
				if (alumno.getAuth() != null)
					return Response.status(Response.Status.OK).build();
				else
					return Response.status(Response.Status.NOT_FOUND).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
