package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
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
		Profesor profesor = new Profesor("Xavier", Security.getHash("00000000A", salt), salt, "00000000A", "no confinado", null, null);
		Profesor profesor2 = new Profesor("Logan", Security.getHash("00000001B", salt), salt, "00000001B", "no confinado", null, null);
		ProfesorDAOImpl.getInstance().createProfesor(profesor);
		ProfesorDAOImpl.getInstance().createProfesor(profesor2);
		Set<Profesor> profesores = new HashSet<>();
		profesores.add(profesor);
		profesores.add(profesor2);
		
		Alumno alumno1 = new Alumno("Pepe", Security.getHash("S01546", salt), salt, "S01546", "no confinado", null);
		AlumnoDAOImpl.getInstance().createAlumno(alumno1);
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		alumnos1.add(alumno1);
		Alumno alumno2 = new Alumno("Jose", Security.getHash("S01546", salt), salt, "S01546", "confinado", null);
		AlumnoDAOImpl.getInstance().createAlumno(alumno2);
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		alumnos2.add(alumno2);
		Alumno alumno3= new Alumno("Pepe", Security.getHash("S01546", salt), salt, "S01546", "no confinado", null);
		AlumnoDAOImpl.getInstance().createAlumno(alumno1);
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		alumnos3.add(alumno3);
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", "presencial", null, null, null, alumnos1);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 2", "confinado", "presencial", null, null, null, alumnos2);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 3", "confinado", "online", null, null, null, alumnos3);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja3);
		List <GrupoBurbuja> grupos = new ArrayList<>();
		grupos.add(burbuja1);
		grupos.add(burbuja2);
		grupos.add(burbuja3);

		
		Clase clase = new Clase("1ºD", null, profesores, grupos);
		ClaseDAOImpl.getInstance().createClase(clase);
		List<Clase> clases = new ArrayList<>();
		clases.add(clase);
		
		CentroEducativo centro = new CentroEducativo("Xavier's School for Gifted Youngsters", clases);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(centro);
		List<CentroEducativo> centros = new ArrayList<>();
		centros.add(centro);
		
		ResponsableCOVID responsable = new ResponsableCOVID("Magneto", Security.getHash("00000002C", salt), salt, "00000002C", false, centros);
		ResponsableDAOImpl.getInstance().createResponsable(responsable);
	}
}
