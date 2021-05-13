package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Alumno;

public interface AlumnoDAO {

	public Alumno createAlumno (Alumno Alumno);
	public Alumno readAlumnobyMatNumCenter (String numeroMatricula, String centro);
	public Alumno readAlumnobyId(String id);
	public Alumno updateAlumno (Alumno Alumno);
	public Alumno updateAlumnobyEstadoSanitario (String estadoSanitario);
	public Alumno updateAlumnobyFechaConfinamiento(Date fechaConfinamiento);
	public Alumno updateAlumnobyGrupo(String grupoBurbuja);
	public Alumno deleteAlumno (Alumno Alumno);
	public List<Alumno> readAllAlumnos();
	public List<Alumno> readAllAlumnos(String centro);
//	public List<Alumno> readAllAlumnosbyGroup(String grupoBurbuja);
//	public List<Alumno> readAllAlumnosbyEstadoSanitario(String estadoSanitario);
//	public List<Alumno> readAllAlumnosbyFechaConfinamiento(Date fechaConfinamiento);

}
