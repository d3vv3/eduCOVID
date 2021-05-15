package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/clase")
public class ClaseResource {
	
	@GET
	@Secured
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAllClases() {
		List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases();
		return Response.ok(clases, MediaType.APPLICATION_JSON).build();
	}


	@GET
	@Secured
	@Path("/profesor/{profesorId}")
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
				for (Profesor profesor: c.getProfesores()) {
					if(profesor.getNifNie().equals(p.getNifNie())) {
						clasesProfesor.add(c);
					}
				}
			}
			return Response.ok(clasesProfesor, MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Secured
	@Path("/alumno/{alumnoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readClaseByAlumno(@PathParam("alumnoId") String alumnoId) {
		Map<String, String> datos = new HashMap<String, String>();
		Alumno a = AlumnoDAOImpl.getInstance().readAlumnobyId(alumnoId);
		System.out.println(a);
		if (a == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases();
			if (clases == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			for (Clase c: clases) {
				for (GrupoBurbuja gc : c.getGruposBurbuja()) {
					for (Alumno al : gc.getAlumnos()) {
						if (al.getId().equals(Long.parseLong(alumnoId))) {
							datos.put("nombreClase", c.getNombre());
							datos.put("grupoPresencial", String.valueOf(gc.getId().equals(c.getBurbujaPresencial().getId())));
							return Response.ok(datos, MediaType.APPLICATION_JSON).build();
						}
					}
				}
			}
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createClase(Clase claseNueva) throws URISyntaxException {
		Clase c = ClaseDAOImpl.getInstance().createClase(claseNueva);
	    if (c != null) {
	            URI uri = new URI("/educovid-backend/rest/clase/" + c.getId());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readClasebyId(@PathParam("id") String id) {
		Clase c = ClaseDAOImpl.getInstance().readClasebyId(id);
		if (c == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(c, MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateClase(@PathParam("id") String id, Clase clase) {
		//System.out.println("Update request for " + id + " " + alumno.toString());
		Clase antiguo = ClaseDAOImpl.getInstance().readClasebyId(id);
	    if ((antiguo == null) || (!antiguo.getId().equals(clase.getId()))) 
	    	return Response.notModified().build();
	    ClaseDAOImpl.getInstance().updateClase(clase);
	    return Response.ok(clase, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response deleteClase(@PathParam("id") String id) {
		Clase c = ClaseDAOImpl.getInstance().readClasebyId(id);
		if (c == null)
			return Response.notModified().build();
		ClaseDAOImpl.getInstance().deleteClase(c);
		return Response.status(Response.Status.OK).build();
	}
}
