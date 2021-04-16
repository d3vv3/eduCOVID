package es.upm.dit.isst.educovid.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

@Path("/clase")
public class ClaseResource {

	@GET
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
	@Path("/alumno/{alumnoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readClaseByAlumno(@PathParam("alumnoId") String alumnoId) {
		Alumno a = AlumnoDAOImpl.getInstance().readAlumnobyId(alumnoId);
		System.out.println(a);
		if (a == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases();
			if (clases == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			Clase claseAlumno = null;
			for (Clase c: clases) {
				for (GrupoBurbuja gc : c.getGruposBurbuja()) {
					for (Alumno al : gc.getAlumnos()) {
						if (al.getId().equals(Integer.parseInt(alumnoId))) {
							claseAlumno = new Clase(c.getNombre(), null, null, null);
							return Response.ok(claseAlumno, MediaType.APPLICATION_JSON).build();
						}
					}
				}
			}
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
