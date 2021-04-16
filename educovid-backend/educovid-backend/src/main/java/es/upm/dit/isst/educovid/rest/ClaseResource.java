package es.upm.dit.isst.educovid.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.Clase;

@Path("/clase")
public class ClaseResource {

	@GET
	@Path("/{profesorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readClaseByProfesor(@PathParam("profesorId") String profesorId) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyId(profesorId);
		if (p == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases();
			if (clases == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			List<Clase> clasesProfesor = new ArrayList<Clase> ();
			for (Clase c: clases) {
				if(c.getProfesores().contains(p)) {
					clasesProfesor.add(c);
				}
			}
			return Response.ok(clasesProfesor, MediaType.APPLICATION_JSON).build();
		}
	}
}
