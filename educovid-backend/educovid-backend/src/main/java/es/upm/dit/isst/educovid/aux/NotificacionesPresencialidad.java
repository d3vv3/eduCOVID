package es.upm.dit.isst.educovid.aux;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

public class NotificacionesPresencialidad {
	
	public static void cambioPresencialidadGrupo(List<Alumno> alumnos, Set<Profesor> profesores, String grupo, String clase, boolean puestoPresencial) {
		System.out.println("PRUEBA 1" + grupo);
		System.out.println("PRUEBA 2" + clase);
		System.out.println("PRUEBA");
		cambioPresencialidadAlumnos(alumnos, puestoPresencial);
		cambioPresencialidadProfesores(profesores, grupo, clase, puestoPresencial);
	}

	public static void cambioPresencialidadAlumnos(List<Alumno> alumnos, boolean puestoPresencial) {
		try {
			for (Alumno alumno : alumnos) {
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
					if (puestoPresencial) {
						msg = "Tu grupo ahora tiene docencia presencial.";
					} else {
						msg = "Tu grupo ahora tiene docencia online.";
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cambioPresencialidadProfesores(Set<Profesor> profesores, String nGrupo, String nClase, boolean puestoPresencial) {
		try {
			for (Profesor profesor : profesores) {
				try {
					if (profesor.getAuth() == null) continue;
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
					if (puestoPresencial) {
						msg = "El " + nGrupo + " de la clase " + nClase + " ahora tiene docencia presencial.";
					} else {
						msg = "El " + nGrupo + " de la clase " + nClase + " ahora tiene docencia online.";
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
