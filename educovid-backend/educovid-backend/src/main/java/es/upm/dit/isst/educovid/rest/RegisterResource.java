
package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;


@Path("/register")
public class RegisterResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/responsable")
	public Response registerResponsableCOVID(String JSONBodyString) throws URISyntaxException {
		
		System.out.println(JSONBodyString);
		JSONObject body = new JSONObject(JSONBodyString);
		String center = body.getString("center");
		String nifNie = body.getString("nifNie");
		String password = body.getString("nifNie");
		Boolean terms = body.getBoolean("terms");
		
		// 1. Parse somehow classes from CSV
		
		// 2. Create center (class) (needs responsible, classes and groups) (check it doesn't already exists and return 409 if so)
		
	
		// 3. Create responsible (class) (needs list of centers)
		// ResponsableCOVID responsible = new ResponsableCOVID(nifNie, center, nifNie, password, terms);
		// responsible.setPassword(password);
		
		// Return a 201 (created)
		return Response.status(Response.Status.CREATED).build();
	}
}