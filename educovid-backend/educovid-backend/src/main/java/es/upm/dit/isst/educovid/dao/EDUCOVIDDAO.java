package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import es.upm.dit.isst.educovid.model.Usuario;

public interface EducovidDAO {

	public Alumno createAlumno (Alumno Alumno);
	public Alumno readAlumnobyMatNum (String numeroMatricula);
	public Alumno updateAlumno (Alumno Alumno);
	public Alumno updateAlumnobyEstadoSanitario (String estadoSanitario);
	public Alumno updateAlumnobyFechaConfinamiento(Date fechaConfinamiento);
	public Alumno updateAlumnobyGrupo(String grupoBurbuja);
	public Alumno deleteAlumno (Alumno Alumno);
	public List<Alumno> readAllAlumnos();
	public List<Alumno> readAllAlumnosbyGroup(String grupoBurbuja);
	public List<Alumno> readAllAlumnosbyEstadoSanitario(String estadoSanitario);
	public List<Alumno> readAllAlumnosbyFechaConfinamiento(Date fechaConfinamiento);
	
	public CentroEducativo createCentroEducativo (CentroEducativo centroEducativo);
	public CentroEducativo readCentroEducativobyName (String nombre);
	public CentroEducativo readCentroEducativobyId (String id);
	public CentroEducativo readCentroEducativobyResponsable (String responsable);
	public CentroEducativo updateCentroEducativo (CentroEducativo CentroEducativo);
	public CentroEducativo updateCentroEducativobyResponsable (ResponsableCOVID responsable);
	public CentroEducativo deleteCentroEducativo (CentroEducativo CentroEducativo);

	public GrupoBurbuja createGrupoBurbuja (GrupoBurbuja grupoBurbuja);
	public GrupoBurbuja readGrupoBurbujabyId (String id);
	public GrupoBurbuja updateGrupoBurbuja (GrupoBurbuja GrupoBurbuja);
	public GrupoBurbuja updateGrupoBurbujabyEstadoSanitario (String estadoSanitario);
	public GrupoBurbuja updateGrupoBurbujabyEstadoDocencia (String estadoDocencia);
	public GrupoBurbuja updateGrupoBurbujabyClase(Clase clase);
	public GrupoBurbuja updateGrupoBurbujabyFechaConfinaiento(Date fechaConfinamiento);
	public GrupoBurbuja updateGrupoBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion);
	public GrupoBurbuja updateGrupoBurbujabyPrioridad(Integer prioridad);
	public GrupoBurbuja deleteGrupoBurbuja (GrupoBurbuja GrupoBurbuja);
	public List<GrupoBurbuja> readAllGruposBurbuja();
	public List<GrupoBurbuja> readAllGruposBurbujabyEstadoSanitario(String estadoSanitario);
	public List<GrupoBurbuja> readAllGruposBurbujabyEstadoDocencia(String estadoDocencia);
	public List<GrupoBurbuja> readAllGruposBurbujabyClase(Clase clase);
	public List<GrupoBurbuja> readAllGruposBurbujabyFechaConfinaiento(Date fechaConfinamiento);
	public List<GrupoBurbuja> readAllGruposBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion);
	public List<GrupoBurbuja> readAllGruposBurbujabyPrioridad(Integer prioridad);
	
	public Profesor createProfesor (Profesor Profesor);
	public Profesor readProfesorbyNIFNIE (String nifNie);
	public Profesor updateProfesor (Profesor Profesor);
	public Profesor updateProfesorbyEstadoSanitario (String estadoSanitario);
	public Profesor updateProfesorbyFechaConfinamiento (Date fechaConfinamiento);
	public Profesor updateProfesorbyClases (Set<Clase> clases);
	public Profesor updateProfesorbyClase (Clase clase);
	public Profesor deleteProfesor (Profesor Profesor);
	public List<Profesor> readAllProfesorbyEstadoSanitario(String estadoSanitario);
	public List<Profesor> readAllProfesorbyFechaConfinamiento(Date fechaConfinamiento);
	public List<Profesor> readAllProfesorbyClase(Clase clase);
	
	public ResponsableCOVID createResponsable (ResponsableCOVID ResponsableCOVID);
	public ResponsableCOVID readResponsablebyNIFNIE (String nifNie);
	public ResponsableCOVID updateResponsable (ResponsableCOVID ResponsableCOVID);
	public ResponsableCOVID updateResponsablebyNIFNIE (String nifNie);
	public ResponsableCOVID updateResponsablebyCentro (CentroEducativo Centro);
	public ResponsableCOVID updateResponsablebyCentros (List<CentroEducativo> Centro);
	public ResponsableCOVID deleteResponsable (ResponsableCOVID ResponsableCOVID);

	public Usuario createUsuario (Usuario Usuario);
	public Usuario readUsuariobyId(String id);
	public Usuario readUsuariobyName(String name);
	public Usuario updateUsuario (Usuario Usuario);
	public Usuario updateUsuariobyId (String id);
	public Usuario updateUsuariobyName (String name);
	public ResponsableCOVID deleteUsuario (Usuario Usuario);
	
}
