package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
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

	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/alumno/{nombreCentro}/{nombreClase}/{nombreGrupo}")
	public Response updateAlumnoEnCentro(Alumno alumno, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("nombreClase") String nombreClase, @PathParam("nombreGrupo") String nombreGrupo) {
		String salt = Security.getSalt();
		String hash = Security.getHash(alumno.getNumeroMatricula(), salt);
		alumno.setSalt(salt);
		alumno.setHash(hash);
		Alumno a = AlumnoDAOImpl.getInstance().updateAlumno(alumno);
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		GrupoBurbuja grupoAlumno = null;
		// Delete from previous group
		GrupoBurbuja oldGrupo = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyAlumnoId(a.getId());
		List<Alumno> updatedAlumnos = new ArrayList<>();
		for (Alumno s : oldGrupo.getAlumnos()) {
			if (!s.getId().equals(a.getId())) {
				updatedAlumnos.add(s);
			}
		}
		oldGrupo.setAlumnos(updatedAlumnos);
		GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(oldGrupo);
		// Add to selected group
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
			AlumnoDAOImpl.getInstance().deleteAlumno(alumno);
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

	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/professor/{nombreCentro}/{nifNie}")
	public Response updateProfesorEnCentro(@PathParam("nifNie") String nifNie,
			@PathParam("nombreCentro") String nombreCentro, List<String> clasesProfesor) {
		// Create classes list from list of classes names
		List<Clase> clases = new ArrayList<>();
		for (String nombreClase : clasesProfesor) {
			Clase clase = ClaseDAOImpl.getInstance().readClasebyName(nombreClase, nombreCentro);
			clases.add(clase);
		}
		Profesor profesor = ProfesorDAOImpl.getInstance().readProfesorbyNIFNIE(nifNie);
		if (profesor == null)
			return Response.status(Response.Status.CONFLICT).build();
		// Remove the professor from all classes
		List<Clase> allClasses = ClaseDAOImpl.getInstance().readAllClases(nombreCentro);
		for (Clase c : allClasses) {
			Set<Profesor> oldProfesores = c.getProfesores();
			Set<Profesor> newProfesores = new HashSet<>();
			oldProfesores.forEach((old) -> {
				if (!old.getNifNie().equals(nifNie)) {
					newProfesores.add(old);
				}
			});
			c.setProfesores(newProfesores);
			ClaseDAOImpl.getInstance().updateClase(c);
		}
		// Add professor to the classes it should be in
		for (Clase c : clases) {
			Set<Profesor> newProfesores = c.getProfesores();
			newProfesores.add(profesor);
			c.setProfesores(newProfesores);
			ClaseDAOImpl.getInstance().updateClase(c);
		}
		return Response.ok().build();
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/class/{nombreCentro}")
	public Response insertClaseEnCentro(Clase newClass, @PathParam("nombreCentro") String nombreCentro) {
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		if (centro == null)
			return Response.status(Response.Status.CONFLICT).build();
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
			GrupoBurbuja newBubbleGroup = new GrupoBurbuja(name, "no confinado", 1, new ArrayList<Alumno>());
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

	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update/class/{nombreCentro}")
	public Response updateClaseEnCentro(Clase updatedClass, @PathParam("nombreCentro") String nombreCentro) {
		System.out.println("FechaInicioConmutacion " + updatedClass.getFechaInicioConmutacion());
		System.out.println("TiempoConmutacion " + updatedClass.getTiempoConmutacion());
		Set<Profesor> profesores = new HashSet<Profesor>();
		for (Profesor p : updatedClass.getProfesores()) {
			Profesor profe = ProfesorDAOImpl.getInstance().readProfesorbyId(p.getId().toString());
			profesores.add(profe);
		}
		Clase clase = ClaseDAOImpl.getInstance().readClasebyId(updatedClass.getId().toString());
		clase.setProfesores(profesores);
		clase.setNombre(updatedClass.getNombre());
		clase.setTiempoConmutacion(updatedClass.getTiempoConmutacion());
		clase.setFechaInicioConmutacion(updatedClass.getFechaInicioConmutacion());
		ClaseDAOImpl.getInstance().updateClase(clase);
		return Response.ok().build();
	}

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/class/{nombreCentro}/{profesorid}")
	public Response insertProfEnClase(Clase newClass, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("profesorid") String profesorid) {
		// Coger profesor y añadirlo
		Profesor prof = ProfesorDAOImpl.getInstance().readProfesorbyId(profesorid);
		if (prof == null)
			return Response.status(Response.Status.CONFLICT).build();
		System.out.println("Clase: " + newClass.getNombre() + ", Profesor: " + prof.getNombre());
		try {
			Clase c = ClaseDAOImpl.getInstance().readClasebyName(newClass.getNombre(), nombreCentro);
			if (c == null)
				return Response.status(Response.Status.CONFLICT).build();
			Set<Profesor> profesoresClase;
			if (c.getProfesores() != null)
				profesoresClase = c.getProfesores();
			else
				profesoresClase = new HashSet<Profesor>();
			profesoresClase.add(prof);
			c.setProfesores(profesoresClase);
			ClaseDAOImpl.getInstance().updateClase(c);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error updating clase or reading clase");
			return Response.status(Response.Status.CONFLICT).build();
		}
		return Response.ok().build();
	}
	
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insert/group/{nombreCentro}/{classId}")
	public Response insertGrupoEnClase(GrupoBurbuja newGrupo, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("classId") String classId) {
		System.out.println("Recibido grupo: " + newGrupo.getNombre());
		List<Alumno> newStudents = new ArrayList<>();
		for (Alumno student : newGrupo.getAlumnos()) {
			newStudents.add(AlumnoDAOImpl.getInstance().readAlumnobyId(student.getId().toString()));
		}
		newGrupo.setAlumnos(newStudents);
		Clase clase = ClaseDAOImpl.getInstance().readClasebyId(classId);
		List<GrupoBurbuja> gruposClase = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbujabyClase(clase);
		Integer lastMaximumPriority = gruposClase.get(gruposClase.size() - 1).getPrioridad();
		System.out.println("Last maximum priority: " + newGrupo.getNombre());
		newGrupo.setPrioridad(lastMaximumPriority + 1);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(newGrupo);
		gruposClase.add(newGrupo);
		clase.setGruposBurbuja(gruposClase);
		ClaseDAOImpl.getInstance().updateClase(clase);
		return Response.ok().build();
	}
	
	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update/group/{nombreCentro}/{groupId}")
	public Response updateGrupoEnClase(GrupoBurbuja newGrupo, @PathParam("nombreCentro") String nombreCentro,
			@PathParam("groupId") String groupId) {
		GrupoBurbuja oldGrupo = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyId(groupId);
		List<Alumno> newStudents = new ArrayList<>();
		for (Alumno student : newGrupo.getAlumnos()) {
			newStudents.add(AlumnoDAOImpl.getInstance().readAlumnobyId(student.getId().toString()));
		}
		oldGrupo.setAlumnos(newStudents);
		oldGrupo.setNombre(newGrupo.getNombre());
		GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(oldGrupo);
		return Response.ok().build();
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/students")
	public Response students(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		try {
			List<Alumno> alumnos = AlumnoDAOImpl.getInstance().readAllAlumnos(nombreCentro);
			if (alumnos.isEmpty())
				return Response.status(Response.Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(alumnos).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/professors")
	public Response teachers(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		try {
			List<Profesor> profesores = ProfesorDAOImpl.getInstance().readAllProfesores(nombreCentro);
			if (profesores.isEmpty())
				return Response.status(Response.Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(profesores).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/classes")
	public Response classes(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		try {
			List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases(nombreCentro);
			if (clases.isEmpty())
				return Response.status(Response.Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(clases).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nombreCentro}/bubblegroups")
	public Response bubblegroups(@PathParam("nombreCentro") String nombreCentro) throws URISyntaxException {
		try {
			List<GrupoBurbuja> gruposBurbuja = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbujabyCentro(nombreCentro);
			if (gruposBurbuja.isEmpty())
				return Response.status(Response.Status.NO_CONTENT).build();
			return Response.status(Response.Status.OK).entity(gruposBurbuja).build();
		} catch (Exception e) {
			return Response.status(Response.Status.CONFLICT).build();
		}
	}
}