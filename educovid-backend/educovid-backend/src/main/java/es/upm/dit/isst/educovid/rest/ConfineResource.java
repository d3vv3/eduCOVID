package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/manage")
public class ConfineResource {
	
	@POST
	@Path("/confine")
	public Response confine(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
			return Response.status(Response.Status.OK).build();
	}
	
	@POST
	@Path("/unconfine")
	public Response unconfine(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
			return Response.status(Response.Status.OK).build();
		
	}
	@GET
	@Path("/students")
	public Response students(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
			return Response.status(Response.Status.OK).build();
		
	}
	@GET
	@Path("/teachers")
	public Response teachers(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
			return Response.status(Response.Status.OK).build();
		
	}
	@GET
	@Path("/bubblegoups")
	public Response bubblegroups(@FormParam("username") String username, @FormParam("password") String password) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
			return Response.status(Response.Status.OK).build();
		
	}
}
