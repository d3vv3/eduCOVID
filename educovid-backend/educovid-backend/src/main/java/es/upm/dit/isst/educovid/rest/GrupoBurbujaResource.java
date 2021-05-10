package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	@Path("/alumno/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGrupoAlumnobyId(@PathParam("id") String id) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyAlumnoId(id);
		if (g == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		GrupoBurbuja gNew = new GrupoBurbuja(g.getNombre(), g.getEstadoSanitario(), null, null, null, null);
		return Response.ok(gNew, MediaType.APPLICATION_JSON).build();
	}
	
	//Métodos rest para gestionar centro parte de grupos

	@POST
 	//@Secured
 	@Consumes(MediaType.APPLICATION_JSON)
 	@Path("/insert/grupoburbuja/{nombreCentro}/{nombreClase}")
 	public Response insertGrupoEnClase(GrupoBurbuja grupoNuevo, @PathParam("nombreCentro") String nombreCentro, @PathParam("nombreClase") String nombreClase) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(grupoNuevo);
 		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
 		Clase clase = null;
 		for (Clase c : centro.getClases()) {
 			if (c.getNombre().equals(nombreClase)) {
 				clase = c;
 			}
 		}
 		if (clase == null || g == null) return Response.status(Response.Status.CONFLICT).build();
 		List<GrupoBurbuja> grupos = clase.getGruposBurbuja();
 		grupos.add(g);
 		clase.setGruposBurbuja(grupos);
 		try {
 			ClaseDAOImpl.getInstance().updateClase(clase);
 		} catch(Exception e) {
 			GrupoBurbujaDAOImpl.getInstance().deleteGrupoBurbuja(grupoNuevo);
 			return Response.status(Response.Status.CONFLICT).build();
 		}
 		return Response.ok().build();
 	}
	
	
	@DELETE
	//@Secured
	@Path("/delete/{nombreClase}/{idGrupo}")
	public Response deleteGroup(@PathParam("nombreClase") String nombreClase, @PathParam("idGrupo") String idGrupo) {
		GrupoBurbuja grupo = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(idGrupo);
		Clase clase = ClaseDAOImpl.getInstance().readClasebyName(nombreClase);
		List<GrupoBurbuja> grupos = clase.getGruposBurbuja();
		for (GrupoBurbuja g : clase.getGruposBurbuja()) {
 			if (g.getId().equals(idGrupo)) {
 				grupos.remove(g);
 			}
 		}
		clase.setGruposBurbuja(grupos);
		if (grupo == null || idGrupo == null) return Response.status(Response.Status.CONFLICT).build();
		
		GrupoBurbujaDAOImpl.getInstance().deleteGrupoBurbuja(grupo);
		return Response.ok(MediaType.APPLICATION_JSON).build();
	}
	
	@PUT
	//@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateGroup(@PathParam("id") String id, GrupoBurbuja g) {
		GrupoBurbuja gOld = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(id);
	    if (gOld == null || (! gOld.getId().equals(g.getId()))) {
	    	return Response.notModified().build();
	    }
	    
	    GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(g);
	    return Response.ok(g, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	//@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGrpuoById(@PathParam("id") String id) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(id);
		if (g == null ) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(g, MediaType.APPLICATION_JSON).build();
	}
	
}
