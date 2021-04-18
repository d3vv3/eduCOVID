package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/manage")
public class ConfineResource {
	
	@POST
	@Path("/confinestudents")
	public Response confineStudents(List<Alumno> alumnos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Alumno a: alumnos) {
			a.setEstadoSanitario("Confinado");
		}
		return Response.status(Response.Status.OK).entity(alumnos).build();
	}
	
	
	@POST
	@Path("/confinegroups")
	public Response confineGroups(List<GrupoBurbuja> grupos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (GrupoBurbuja gb: grupos) {
			gb.setEstadoSanitario("Confinado");
		}
		return Response.status(Response.Status.OK).entity(grupos).build();
	}
	
	@POST
	@Path("/confineteachers")
	public Response confineTeachers(List<Profesor> profesores) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Profesor p: profesores) {
			p.setEstadoSanitario("Confinado");
		}
		return Response.status(Response.Status.OK).entity(profesores).build();
	}
	
	@POST
	@Path("/unconfinestudents")
	public Response unconfineStudents(List<Alumno> alumnos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Alumno a: alumnos) {
			a.setEstadoSanitario("No Confinado");
		}
		return Response.status(Response.Status.OK).entity(alumnos).build();
	}
	
	@POST
	@Path("/unconfinegroups")
	public Response unconfineGroups(List<GrupoBurbuja> grupos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (GrupoBurbuja gb: grupos) {
			gb.setEstadoSanitario("No Confinado");
		}
		return Response.status(Response.Status.OK).entity(grupos).build();
	}
	
	@POST
	@Path("/unconfineteachers")
	public Response unconfineTeachers(List<Profesor> profesores) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Profesor p: profesores) {
			p.setEstadoSanitario("No Confinado");
		}
		return Response.status(Response.Status.OK).entity(profesores).build();
	}

	@GET
	@Path("/students")
	public Response students() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<Alumno> alumnos = AlumnoDAOImpl.getInstance().readAllAlumnos();
		System.out.print(alumnos);
		return Response.ok(alumnos, MediaType.APPLICATION_JSON).build();
		
	}
	@GET
	@Path("/teachers")
	public Response teachers() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<Profesor> profesores = ProfesorDAOImpl.getInstance().readAllProfesores();
		return Response.status(Response.Status.OK).entity(profesores).build();
		
	}
	@GET
	@Path("/bubblegroups")
	public Response bubblegroups() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<GrupoBurbuja> gruposBurbuja = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbuja();
		return Response.ok(gruposBurbuja, MediaType.APPLICATION_JSON).build();
		
	}
}
