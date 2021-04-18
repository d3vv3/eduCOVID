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
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/grupo")
public class GrupoBurbujaResource {

	@GET
	@Path("/{profesorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGroupsByProfesor(@PathParam("profesorId") String profesorId) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyId(profesorId);
		if (p == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Clase> clasesProfesor = new ArrayList<Clase> ();
			for (Clase c: ClaseDAOImpl.getInstance().readAllClases()) {
				for (Profesor profesor: c.getProfesores()) {
					if(profesor.getNifNie().equals(p.getNifNie())) {
						clasesProfesor.add(c);
					}
				}
			}
			List<GrupoBurbuja> grupos = new ArrayList<GrupoBurbuja> ();
			List<GrupoBurbuja> gruposFront = new ArrayList<GrupoBurbuja> ();
			for (Clase c: clasesProfesor) {
				for (GrupoBurbuja grupo: c.getGruposBurbuja()) {
					grupos.add(grupo);
				}
			}
			for (GrupoBurbuja g: grupos) {
				for (Alumno a: g.getAlumnos()) {
					if (a.getEstadoSanitario().equals("confinado")) {
						gruposFront.add(new GrupoBurbuja(g.getNombre(), "alumnosconfinados", g.getEstadoDocencia(), null, null, null, null));
					} else {
						gruposFront.add(new GrupoBurbuja(g.getNombre(), "alumnosnoconfinados", g.getEstadoDocencia(), null, null, null, null));
					}
				}
			}
			return Response.ok(gruposFront, MediaType.APPLICATION_JSON).build();
		}
	}

	@GET
	@Path("alumno/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGrupoAlumnobyId(@PathParam("id") String id) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyAlumnoId(id);
		if (g == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		GrupoBurbuja gNew = new GrupoBurbuja(g.getNombre(), g.getEstadoSanitario(), g.getEstadoDocencia(), null, null, null, null);
		return Response.ok(gNew, MediaType.APPLICATION_JSON).build();
	}
}
