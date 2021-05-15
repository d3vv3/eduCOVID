package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Clase;

public interface ClaseDAO {

	public Clase createClase (Clase clase);
	public Clase readClasebyId (String id);
	public Clase readClasebyName (String name, String centro);
	public Clase updateClase (Clase clase);
	public Clase deleteClase (Clase clase);
	public List<Clase> readAllClases();
	public Clase updatePresencialGroup(Clase clase);
	public List<Clase> readAllClases(String nombreCentro);
}
