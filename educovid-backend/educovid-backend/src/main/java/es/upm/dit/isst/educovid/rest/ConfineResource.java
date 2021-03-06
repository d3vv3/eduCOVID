package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/confine/students")
	public Response confineStudents(List<Alumno> alumnos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Alumno a: alumnos) {
			Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
			b.setEstadoSanitario("confinado");
			AlumnoDAOImpl.getInstance().updateAlumno(b);
		}
		return Response.status(Response.Status.OK).entity(alumnos).build();
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/confine/bubbleGroups")
	public Response confineGroups(List<GrupoBurbuja> grupos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (GrupoBurbuja gb: grupos) {
			gb.setEstadoSanitario("confinado");
			GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(gb);
			for (Alumno a: gb.getAlumnos()) {
				Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
				b.setEstadoSanitario("confinado");
				AlumnoDAOImpl.getInstance().updateAlumno(b);
			}
		}
		return Response.status(Response.Status.OK).entity(grupos).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/confine/professors")
	public Response confineTeachers(List<Profesor> profesores) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Profesor p: profesores) {
			Profesor p2 = ProfesorDAOImpl.getInstance().readProfesorbyId(p.getId().toString());
			p2.setEstadoSanitario("confinado");
			ProfesorDAOImpl.getInstance().updateProfesor(p2);
		}
		return Response.status(Response.Status.OK).entity(profesores).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/unconfine/students")
	public Response unconfineStudents(List<Alumno> alumnos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Alumno a: alumnos) {
			Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
			b.setEstadoSanitario("no confinado");
			AlumnoDAOImpl.getInstance().updateAlumno(b);
		}
		return Response.status(Response.Status.OK).entity(alumnos).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/unconfine/bubbleGroups")
	public Response unconfineGroups(List<GrupoBurbuja> grupos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (GrupoBurbuja gb: grupos) {
			gb.setEstadoSanitario("no confinado");
			GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(gb);
			for (Alumno a: gb.getAlumnos()) {
				Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
				b.setEstadoSanitario("no confinado");
				AlumnoDAOImpl.getInstance().updateAlumno(b);
			}
		}
		return Response.status(Response.Status.OK).entity(grupos).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/unconfine/professors")
	public Response unconfineTeachers(List<Profesor> profesores) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Profesor p: profesores) {
			Profesor p2 = ProfesorDAOImpl.getInstance().readProfesorbyId(p.getId().toString());
			p2.setEstadoSanitario("no confinado");
			ProfesorDAOImpl.getInstance().updateProfesor(p2);
		}
		return Response.status(Response.Status.OK).entity(profesores).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/switch/students")
	public Response switchStudents(List<Alumno> alumnos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Alumno a: alumnos) {
			Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
			if (b.getEstadoSanitario().equals("confinado")) b.setEstadoSanitario("no confinado");
			else b.setEstadoSanitario("confinado");
			AlumnoDAOImpl.getInstance().updateAlumno(b);
		}
		return Response.status(Response.Status.OK).entity(alumnos).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/switch/bubbleGroups")
	public Response switchGroups(List<GrupoBurbuja> grupos) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (GrupoBurbuja gb: grupos) {
			if (gb.getEstadoSanitario().equals("confinado")) gb.setEstadoSanitario("no confinado");
			else gb.setEstadoSanitario("confinado");
			GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(gb);
			for (Alumno a: gb.getAlumnos()) {
				Alumno b = AlumnoDAOImpl.getInstance().readAlumnobyId(a.getId().toString());
				if (b.getEstadoSanitario().equals("confinado")) b.setEstadoSanitario("no confinado");
				else b.setEstadoSanitario("confinado");
				AlumnoDAOImpl.getInstance().updateAlumno(b);
			}
		}
		return Response.status(Response.Status.OK).entity(grupos).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/switch/professors")
	public Response switchTeachers(List<Profesor> profesores) throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into confine
		for (Profesor p: profesores) {
			Profesor p2 = ProfesorDAOImpl.getInstance().readProfesorbyId(p.getId().toString());
			if (p2.getEstadoSanitario().equals("confinado")) p2.setEstadoSanitario("no confinado");
			else p2.setEstadoSanitario("confinado");
			ProfesorDAOImpl.getInstance().updateProfesor(p2);
		}
		return Response.status(Response.Status.OK).entity(profesores).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/students")
	public Response students() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<Alumno> alumnos = AlumnoDAOImpl.getInstance().readAllAlumnos();
//		System.out.print(alumnos);
		return Response.ok(alumnos, MediaType.APPLICATION_JSON).build();

	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/professors")
	public Response teachers() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<Profesor> profesores = ProfesorDAOImpl.getInstance().readAllProfesores();
		return Response.status(Response.Status.OK).entity(profesores).build();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/bubblegroups")
	public Response bubblegroups() throws URISyntaxException {
		//TODO design an endpoint that changes the health status of the people or groups selected into not confine
		List<GrupoBurbuja> gruposBurbuja = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbuja();
		return Response.ok(gruposBurbuja, MediaType.APPLICATION_JSON).build();
	}
}
