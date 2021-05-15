package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public class MockDataFiller {

	public static void main(String[] args) {
		String salt = Security.getSalt();
		Profesor profesor = new Profesor("Xavier", Security.getHash("00000000A", salt), salt, "00000000A", "no confinado", null);
		Profesor profesor2 = new Profesor("Logan", Security.getHash("00000001B", salt), salt, "00000001B", "no confinado", null);
		Profesor profesor3 = new Profesor("Mr. Potato", Security.getHash("00000002D", salt), salt, "00000002D", "confinado", null);
		ProfesorDAOImpl.getInstance().createProfesor(profesor);
		ProfesorDAOImpl.getInstance().createProfesor(profesor2);
		ProfesorDAOImpl.getInstance().createProfesor(profesor3);
		Set<Profesor> profesores1= new HashSet<>();
		Set<Profesor> profesores2 = new HashSet<>();
		profesores1.add(profesor);
		profesores2.add(profesor2);
		profesores1.add(profesor3);

		Alumno alumno1 = new Alumno("Pepe", Security.getHash("S01546", salt), salt, "S01546", "no confinado", null);
		Alumno alumno2 = new Alumno("Bob Esponja", Security.getHash("S01547", salt), salt, "S01547", "confinado", null);
		Alumno alumno3 = new Alumno("Urdangarín", Security.getHash("S01548", salt), salt, "S01548", "no confinado", null);
		Alumno alumno4 = new Alumno("Bill Gates", Security.getHash("S01549", salt), salt, "S01549", "no confinado", null);
		Alumno alumno5 = new Alumno("Patricio Estrella", Security.getHash("S01550", salt), salt, "S01550", "confinado", null);
		Alumno alumno6 = new Alumno("Doraemon", Security.getHash("S01551", salt), salt, "S01551", "no confinado", null);
		Alumno alumno7 = new Alumno("Nobita", Security.getHash("S01552", salt), salt, "S01552", "no confinado", null);
		Alumno alumno8 = new Alumno("Suneo", Security.getHash("S01553", salt), salt, "S01553", "no confinado", null);
		Alumno alumno9 = new Alumno("Bart Simpson", Security.getHash("S01554", salt), salt, "S01554", "confinado", null);
		AlumnoDAOImpl.getInstance().createAlumno(alumno1);
		AlumnoDAOImpl.getInstance().createAlumno(alumno2);
		AlumnoDAOImpl.getInstance().createAlumno(alumno3);
		AlumnoDAOImpl.getInstance().createAlumno(alumno4);
		AlumnoDAOImpl.getInstance().createAlumno(alumno5);
		AlumnoDAOImpl.getInstance().createAlumno(alumno6);
		AlumnoDAOImpl.getInstance().createAlumno(alumno7);
		AlumnoDAOImpl.getInstance().createAlumno(alumno8);
		AlumnoDAOImpl.getInstance().createAlumno(alumno9);
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		alumnos1.add(alumno1);
		alumnos2.add(alumno2);
		alumnos3.add(alumno3);
		alumnos1.add(alumno4);
		alumnos2.add(alumno5);
		alumnos3.add(alumno6);
		alumnos1.add(alumno7);
		alumnos2.add(alumno8);
		alumnos3.add(alumno9);

		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", null, 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 2", "no confinado", null, 2, alumnos2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 3", "no confinado", null, 3, alumnos3);
		GrupoBurbuja burbuja4 = new GrupoBurbuja("Grupo 1", "confinado", null, null, alumnos3);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja1);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja2);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja3);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja4);
		List <GrupoBurbuja> grupos1 = new ArrayList<>();
		List <GrupoBurbuja> grupos2 = new ArrayList<>();
		grupos1.add(burbuja1);
		grupos1.add(burbuja2);
		grupos1.add(burbuja3);
		grupos2.add(burbuja4);
		
		String fecha = "2021-05-02";
		
		java.sql.Date fechaInicio = java.sql.Date.valueOf(fecha);
		
		Clase clase1 = new Clase("1ºD", grupos1.get(0), fechaInicio, 7, profesores1, grupos1);
		Clase clase2 = new Clase("6ºA", grupos2.get(0), null, null, profesores2, grupos2);
		ClaseDAOImpl.getInstance().createClase(clase1);
		ClaseDAOImpl.getInstance().createClase(clase2);
		List<Clase> clases = new ArrayList<>();
		clases.add(clase1);
		clases.add(clase2);

		CentroEducativo centro = new CentroEducativo("Xavier's School for Gifted Youngsters", clases);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(centro);
		List<CentroEducativo> centros = new ArrayList<>();
		centros.add(centro);

		ResponsableCOVID responsable = new ResponsableCOVID("Magneto", Security.getHash("00000002C", salt), salt, "00000002C", false, centros);
		ResponsableDAOImpl.getInstance().createResponsable(responsable);

	}
}
