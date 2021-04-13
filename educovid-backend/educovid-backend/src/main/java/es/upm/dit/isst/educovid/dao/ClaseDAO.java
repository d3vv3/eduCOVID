package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

public interface ClaseDAO {

	public Clase createClase (Clase clase);
	public Clase readClasebyId (String id);
	public Clase updateClase (Clase clase);
	public Clase deleteClase (Clase clase);
	public List<Clase> readAllClases();
		
}
