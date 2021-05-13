package es.upm.dit.isst.educovid.rest;

import java.util.ArrayList;
import java.util.List;

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
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

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
	public Response insertAlumnoEnCentro(Alumno alumnoNuevo, @PathParam("nombreCentro") String nombreCentro, @PathParam("nombreClase") String nombreClase, @PathParam("nombreGrupo") String nombreGrupo) {
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
		if (grupoAlumno == null || a == null) return Response.status(Response.Status.CONFLICT).build();
		List<Alumno> alumnos = grupoAlumno.getAlumnos();
		alumnos.add(a);
		grupoAlumno.setAlumnos(alumnos);
		try {
			GrupoBurbujaDAOImpl.getInstance().updateGrupoBurbuja(grupoAlumno);
		} catch(Exception e) {
			AlumnoDAOImpl.getInstance().deleteAlumno(alumnoNuevo);
			return Response.status(Response.Status.CONFLICT).build();
		}
		return Response.ok().build();
	}
	
}
