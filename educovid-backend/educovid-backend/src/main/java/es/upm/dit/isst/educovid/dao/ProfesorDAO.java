package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.Profesor;

public interface ProfesorDAO {

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
	
}