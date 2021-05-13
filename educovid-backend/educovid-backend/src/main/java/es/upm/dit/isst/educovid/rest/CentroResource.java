package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
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
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAO;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

@Path("/centro")
public class CentroResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> readAllCentroEducativo() {
		List<CentroEducativo> centros = CentroEducativoDAOImpl.getInstance().readAllCentroEducativo();
		List<String> nombresCentros = new ArrayList<>();
		for (CentroEducativo centro : centros) {
			nombresCentros.add(centro.getNombre());
		}
		return nombresCentros;
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/alumno/{nombreCentro}/{nombreClase}/{nombreGrupo}")
	public Response insertAlumnoEnCentro(Alumno alumnoNuevo, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("nombreClase") String nombreClase, @PathParam("nombreGrupo") String nombreGrupo) {
		String salt = Security.getSalt();
		String hash = Security.getHash(alumnoNuevo.getNumeroMatricula(), salt);
		alumnoNuevo.setSalt(salt);
		alumnoNuevo.setHash(hash);
		Alumno a = AlumnoDAOImpl.getInstance().createAlumno(alumnoNuevo);
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		GrupoBurbuja grupoAlumno = null;
		for (Clase c : centro.getClases()) {
			if (c.getNombre().equals(nombreClase)) {
				for (GrupoBurbuja g : c.getGruposBurbuja()) {
					if (g.getNombre().equals(nombreGrupo)) {
						grupoAlumno = g;
					}
				}
			}
		}
		if (grupoAlumno == null || a == null)
			return Response.status(Response.Status.CONFLICT).build();
		List<Alumno> alumnos = grupoAlumno.getAlumnos();
		alumnos.add(a);
		grupoAlumno.setAlumnos(alumnos);
		try {
			GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(grupoAlumno);
		} catch (Exception e) {
			AlumnoDAOImpl.getInstance().deleteAlumno(alumnoNuevo);
			return Response.status(Response.Status.CONFLICT).build();
		}
		return Response.ok().build();
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/professor/{nombreCentro}/{nombreClase}")
	public Response insertProfesorEnCentro(Profesor profesorNuevo, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("nombreClase") String nombreClase) {
		String salt = Security.getSalt();
		String hash = Security.getHash(profesorNuevo.getNifNie(), salt);
		profesorNuevo.setSalt(salt);
		profesorNuevo.setHash(hash);
//		Profesor p = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(profesorNuevo.getNifNie());
//		System.out.println(p);
		Profesor p;
		try {
			p = ProfesorDAOImpl.getInstance().createProfesor(profesorNuevo);
		} catch (Exception e) {
			System.out.println("Professor exists");
			p = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(profesorNuevo.getNifNie());
		}
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		for (Clase clase : centro.getClases()) {
			if (clase.getNombre().equals(nombreClase)) {
				Set<Profesor> profesoresClase = clase.getProfesores();
				profesoresClase.add(p);
				clase.setProfesores(profesoresClase);
				try {
					ClaseDAOImpl.getInstance().updateClase(clase);
				} catch (Exception e) {
					ProfesorDAOImpl.getInstance().deleteProfesor(profesorNuevo);
					return Response.status(Response.Status.CONFLICT).build();
				}
			}
		}
		return Response.ok().build();
	}
	
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/class/{nombreCentro}")
	public Response insertClaseEnCentro(Clase newClass, @PathParam("nombreCentro") String nombreCentro) {
		// Coger profesor y añadirlo
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		if (centro == null) return Response.status(Response.Status.CONFLICT).build();
		System.out.println("Centro: " + centro.getNombre());
		System.out.println("Clase: " + newClass.getNombre());
		try {
			if (newClass == null)
				return Response.status(Response.Status.CONFLICT).build();
			// Añadir grupo burbuja por defecto
			String[] names = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
					"S", "T", "U", "V", "W", "X", "Y", "Z" };
			String name = " GRUPO " + names[(int) Math.floor(Math.random() * names.length)]
					+ names[(int) Math.floor(Math.random() * names.length)];
			GrupoBurbuja newBubbleGroup = new GrupoBurbuja(name, "no confinado", null, null, null,
					new ArrayList<Alumno>());
			GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(newBubbleGroup);
			List<GrupoBurbuja> bubbleGroups = new ArrayList<>();
			bubbleGroups.add(newBubbleGroup);
			newClass.setGruposBurbuja(bubbleGroups);
			newClass.setBurbujaPresencial(newBubbleGroup);
			Clase c = ClaseDAOImpl.getInstance().createClase(newClass);
			// Añadir clase al centro
			centro.getClases().add(c);
			CentroEducativoDAOImpl.getInstance().updateCentroEducativo(centro);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error updating centro, creating class or creating grupo burbuja");
		}
		return Response.ok().build();
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/class/{nombreCentro}/{nifProfesor}")
	public Response insertProfEnClase(Clase newClass, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("nifProfesor") String nifProfesor) {
		// Coger profesor y añadirlo
		Profesor prof = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(nifProfesor);
		if (prof == null) return Response.status(Response.Status.CONFLICT).build();
		System.out.println("Clase: " + newClass.getNombre());
		try {
			Clase c = ClaseDAOImpl.getInstance().readClasebyName(newClass.getNombre(), nombreCentro);
			if (c == null)
				return Response.status(Response.Status.CONFLICT).build();
			Set<Profesor> profesoresClase;
			if (c.getProfesores() == null) profesoresClase = c.getProfesores();
			else profesoresClase = new HashSet<Profesor>();
			profesoresClase.add(prof);
			c.setProfesores(profesoresClase);
			ClaseDAOImpl.getInstance().updateClase(c);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error updating clase or reading clase");
		}
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/students")
	public Response students(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		List<Alumno> alumnos = AlumnoDAOImpl.getInstance().readAllAlumnos(nombreCentro);
		return Response.ok(alumnos, MediaType.APPLICATION_JSON).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/professors")
	public Response teachers(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		List<Profesor> profesores = ProfesorDAOImpl.getInstance().readAllProfesores(nombreCentro);
		return Response.status(Response.Status.OK).entity(profesores).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/classes")
	public Response classes(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		try {
			List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases(nombreCentro);
			if (clases.isEmpty()) return Response.status(Response.Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(clases).build();
		} catch(Exception e) {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/bubblegroups")
	public Response bubblegroups(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		List<GrupoBurbuja> gruposBurbuja = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbujabyCentro(nombreCentro);
		return Response.ok(gruposBurbuja, MediaType.APPLICATION_JSON).build();
	}
}