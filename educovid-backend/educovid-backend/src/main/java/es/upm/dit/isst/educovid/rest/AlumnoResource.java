package es.upm.dit.isst.educovid.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.anotation.Secured;
import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

@Path("/alumno")
public class AlumnoResource {

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAlumno(Alumno alumnoNuevo) throws URISyntaxException {
		String salt = Security.getSalt();
		String hash = Security.getHash(alumnoNuevo.getNumeroMatricula(), salt);
		alumnoNuevo.setSalt(salt);
		alumnoNuevo.setHash(hash);
		Alumno a = AlumnoDAOImpl.getInstance().createAlumno(alumnoNuevo);
	    if (a != null) {
	            URI uri = new URI("/educovid-backend/rest/alumno/" + a.getId());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.CONFLICT).build();
	}
	
//	@GET
//	@Path("mat/{numeroMatricula}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response readAlumnobyMatNum(@PathParam("numeroMatricula") String numeroMatricula) {
//		Alumno a = AlumnoDAOImpl.getInstance().readAlumnobyMatNum(numeroMatricula);
//		if (a == null)
//			return Response.status(Response.Status.NOT_FOUND).build();
//		return Response.ok(a, MediaType.APPLICATION_JSON).build();
//	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readAlumnobyId(@PathParam("id") String id) {
		Alumno a = AlumnoDAOImpl.getInstance().readAlumnobyId(id);
		if (a == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(a, MediaType.APPLICATION_JSON).build();
	}
	
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateAlumno(@PathParam("id") String id, Alumno alumno) {
		//System.out.println("Update request for " + id + " " + alumno.toString());
		Alumno antiguo = AlumnoDAOImpl.getInstance().readAlumnobyId(id);
	    if ((antiguo == null) || (!antiguo.getId().equals(alumno.getId()))) 
	    	return Response.notModified().build();
	    AlumnoDAOImpl.getInstance().updateAlumno(alumno);
	    return Response.ok(alumno, MediaType.APPLICATION_JSON).build();
	}
	
	@DELETE
	// @Secured
	@Path("/{id}")
	public Response deleteAlumno(@PathParam("id") String id) {
		Alumno a = AlumnoDAOImpl.getInstance().readAlumnobyId(id);
		if (a == null)
			return Response.notModified().build();
		AlumnoDAOImpl.getInstance().deleteAlumno(a);
		return Response.ok(a, MediaType.APPLICATION_JSON).build();
	}
	
//	@GET
//	@Secured
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Alumno> readAllAlumnos() {
//		return AlumnoDAOImpl.getInstance().readAllAlumnos();
//	}
	
	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/grupo/{grupoBurbujaId}")
	public Response readAllAlumnosbyGroup(@PathParam("grupoBurbujaId") String grupoBurbujaId) {
		GrupoBurbuja grupo = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(grupoBurbujaId);
		return Response.status(Response.Status.OK).entity(grupo.getAlumnos()).build();
	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("estadoSanitario/{estadoSanitario}")
//	public List<Alumno> readAllAlumnosbyEstadoSanitario(@PathParam("estadoSanitario") String estadoSanitario) {
//		return AlumnoDAOImpl.getInstance().readAllAlumnosbyEstadoSanitario(estadoSanitario);
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("fechaConfinamiento/{fechaConfinamiento}")
//	public List<Alumno> readAllAlumnosbyFechaConfinamiento(@PathParam("fechaConfinamiento") Date fechaConfinamiento) {
//		return AlumnoDAOImpl.getInstance().readAllAlumnosbyFechaConfinamiento(fechaConfinamiento);
//	}
}
