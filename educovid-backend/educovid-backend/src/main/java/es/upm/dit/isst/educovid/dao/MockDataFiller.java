package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public class MockDataFiller {

	public static void main(String[] args) {
		Profesor profesor = new Profesor("Xavier", "00000000A", "00000000A", "no confinado", null, null);
		Profesor profesor2 = new Profesor("Logan", "00000001B", "00000001B", "no confinado", null, null);
		ProfesorDAOImpl.getInstance().createProfesor(profesor);
		ProfesorDAOImpl.getInstance().createProfesor(profesor2);
		Set<Profesor> profesores = new HashSet<>();
		profesores.add(profesor);
		profesores.add(profesor2);
		
		Alumno alumno1 = new Alumno("Pepe", "password!", "S01546", "no confinado", null);
		AlumnoDAOImpl.getInstance().createAlumno(alumno1);
		List<Alumno> alumnos = new ArrayList<Alumno>();
		alumnos.add(alumno1);
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", "presencial", null, null, null, alumnos);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja1);
		List <GrupoBurbuja> grupos = new ArrayList<>();
		grupos.add(burbuja1);
		
		Clase clase = new Clase("1ºD", null, profesores, grupos);
		ClaseDAOImpl.getInstance().createClase(clase);
		List<Clase> clases = new ArrayList<>();
		clases.add(clase);
		
		CentroEducativo centro = new CentroEducativo("Xavier's School for Gifted Youngsters", clases);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(centro);
		List<CentroEducativo> centros = new ArrayList<>();
		centros.add(centro);
		
		ResponsableCOVID responsable = new ResponsableCOVID("Magneto", "00000002C", "00000002C", false, centros);
		ResponsableDAOImpl.getInstance().createResponsable(responsable);
	}
}
