package es.upm.dit.isst.educovid.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/grupo")
public class GrupoBurbujaResource {
	
	@GET
	@Secured
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAllClases() {
		List<GrupoBurbuja> grupos = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbuja();
		return Response.ok(grupos, MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Secured
	@Path("/{profesorId}/{nombreCentro}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGroupsByProfesorAndCenter(@PathParam("profesorId") String profesorId, @PathParam("nombreCentro") String nombreCentro) {
		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyId(profesorId);
		CentroEducativo c = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		if (p == null || c == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			List<Clase> clasesProfesor = new ArrayList<Clase> ();
			for (Clase clase: c.getClases()) {
				for (Profesor profesor: clase.getProfesores()) {
					if(profesor.getNifNie().equals(p.getNifNie())) {
						clasesProfesor.add(clase);
					}
				}
			}
			//List<GrupoBurbuja> gruposFront = new ArrayList<GrupoBurbuja> ();
			List<Map<String,String>> gruposFront = new ArrayList<Map<String,String>> ();
			for (Clase clase: clasesProfesor) {
				for (GrupoBurbuja grupo: clase.getGruposBurbuja()) {
					boolean confinados = false;
					boolean grupoPresencial = grupo.getNombre().equals(clase.getBurbujaPresencial().getNombre());
					for (Alumno a: grupo.getAlumnos()) {
						if (a.getEstadoSanitario().equals("confinado")) {
							confinados = true;
						}
					}
					
					if(confinados) {
						//gruposFront.add(new GrupoBurbuja(clase.getNombre() + " - " + grupo.getNombre(), "alumnosconfinados", null, null, null, null));
						Map<String,String> g = new HashMap<String,String>();
						g.put("nombre", clase.getNombre() + " - " + grupo.getNombre());
						g.put("grupoPresencial", String.valueOf(grupoPresencial));
						g.put("alumnosConfinados", String.valueOf(true));
						gruposFront.add(g);
					} else {
						//gruposFront.add(new GrupoBurbuja(clase.getNombre() + " - " + grupo.getNombre(), "alumnosnoconfinados", null, null, null, null));
						Map<String,String> g = new HashMap<String,String>();
						g.put("nombre", clase.getNombre() + " - " + grupo.getNombre());
						g.put("grupoPresencial", String.valueOf(grupoPresencial));
						g.put("alumnosConfinados", String.valueOf(false));
						gruposFront.add(g);
					}
				}
			}
			return Response.ok(gruposFront, MediaType.APPLICATION_JSON).build();
		}
	}
	
	@GET
	@Secured
	@Path("/alumno/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGrupoAlumnobyId(@PathParam("id") String id) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyAlumnoId(Long.parseLong(id));
		if (g == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		GrupoBurbuja gNew = new GrupoBurbuja(g.getNombre(), g.getEstadoSanitario(), null, null, null);
		return Response.ok(gNew, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Secured
	@Path("/clase/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response readGroupsByClaseId(@PathParam("id") String id) {
		Clase c = ClaseDAOImpl.getInstance().readClasebyId(id);
		if (c == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		List<GrupoBurbuja> gruposClase = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbujabyClase(c);
		return Response.ok(gruposClase, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	@Secured
	@Path("/delete/{groupId}/{classId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGrupoById(@PathParam("groupId") String groupId, @PathParam("classId") String classId) {
		Clase c = ClaseDAOImpl.getInstance().readClasebyId(classId);
		// If there is only one group, it cant be deleted
		if (c.getGruposBurbuja().size() == 1) return Response.status(Response.Status.EXPECTATION_FAILED).build();
		// Remove group from class groups
		List<GrupoBurbuja> newGroups = new ArrayList<>();
		for (GrupoBurbuja g : c.getGruposBurbuja()) {
			if (!g.getId().toString().equals(groupId)) {
				newGroups.add(g);
			}
		}
		c.setGruposBurbuja(newGroups);
		// Read the group and proceed if it exists
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(groupId);
		if (g == null ) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		// If the group is set to be the presential group
		if (c.getBurbujaPresencial().getId().equals(g.getId())) {
			// Get next priority
			Integer prioridadSiguiente = (c.getBurbujaPresencial().getPrioridad() % (c.getGruposBurbuja().size() + 1)) + 1;
			System.out.println("Siguiente prioridad: " + prioridadSiguiente);
			for (GrupoBurbuja b : c.getGruposBurbuja()) {
				if (b.getPrioridad() == prioridadSiguiente) {
					System.out.println("Estableciendo grupo presencial " + b.getNombre() + " con " + prioridadSiguiente);
					c.setBurbujaPresencial(b);
					GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(b);
				}
			}
		}
		ClaseDAOImpl.getInstance().updateClase(c);
		GrupoBurbujaDAOImpl.getInstance().deleteGrupoBurbuja(g);
		for (GrupoBurbuja b : c.getGruposBurbuja()) {
			if (b.getPrioridad() > g.getPrioridad()) {
				System.out.println("Cambiando prioridad de " + b.getNombre() + " de " + b.getPrioridad() + " a " + (b.getPrioridad() - 1));
				b.setPrioridad(b.getPrioridad() - 1);
				GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(b);
			}
		}
		ClaseDAOImpl.getInstance().updateClase(c);
		return Response.ok(g, MediaType.APPLICATION_JSON).build();
	}
}
