package es.upm.dit.isst.educovid.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;

class ProfesorDAOImplTest {

	@Test
	void testProfesorDAO() {
		String salt1 = Security.getSalt();
		String salt2 = Security.getSalt();
		String salt3 = Security.getSalt();
		String salt4 = Security.getSalt();
		Profesor profesor1 = new Profesor("Pepe", Security.getHash("00000000A", salt1), salt1, "00000001A", "no confinado");
		Profesor profesor2 = new Profesor("Gloria", Security.getHash("00000001B", salt2), salt2, "00000002B", "no confinado");
		Profesor profesor3 = new Profesor("Alberto", Security.getHash("00000002D", salt3), salt3, "00000003C", "confinado");
		Profesor profesor4 = new Profesor("Nuria", Security.getHash("00000002D", salt4), salt4, "00000004D", "confinado");
		assertNotNull(ProfesorDAOImpl.getInstance().createProfesor(profesor1));
		assertNotNull(ProfesorDAOImpl.getInstance().createProfesor(profesor2));
		assertNotNull(ProfesorDAOImpl.getInstance().createProfesor(profesor3));
		assertNotNull(ProfesorDAOImpl.getInstance().createProfesor(profesor4));
		Set<Profesor> profesores1= new HashSet<>();
		Set<Profesor> profesores2 = new HashSet<>();
		Set<Profesor> profesores3 = new HashSet<>();
		profesores1.add(profesor1);
		profesores2.add(profesor2);
		profesores2.add(profesor3);
		profesores3.add(profesor4);
		
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 1", "no confinado", 2, alumnos2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 1", "no confinado", 3, alumnos3);
		
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja1);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja2);
		GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(burbuja3);
		
		List <GrupoBurbuja> grupos1 = new ArrayList<>();
		List <GrupoBurbuja> grupos2 = new ArrayList<>();
		List <GrupoBurbuja> grupos3 = new ArrayList<>();
		
		grupos1.add(burbuja1);
		grupos2.add(burbuja2);
		grupos3.add(burbuja3);
		
		Clase clase1 = new Clase("Clase 1", grupos1.get(0), null, null, profesores1, grupos1);
		Clase clase2 = new Clase("Clase 2", grupos2.get(0), null, null, profesores2, grupos2);
		Clase clase3 = new Clase("Clase 3", grupos3.get(0), null, null, profesores3, grupos3);
		
		ClaseDAOImpl.getInstance().createClase(clase1);
		ClaseDAOImpl.getInstance().createClase(clase2);
		ClaseDAOImpl.getInstance().createClase(clase3);
		List<Clase> clases1 = new ArrayList<>();
		List<Clase> clases2 = new ArrayList<>();
		clases1.add(clase1);
		clases1.add(clase2);
		clases2.add(clase3);
		
		CentroEducativo centro1 = new CentroEducativo("Colegio", clases1);
		CentroEducativo centro2 = new CentroEducativo("Instituto", clases2);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(centro1);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(centro2);
		
		List<Profesor> profesCole = ProfesorDAOImpl.getInstance().readAllProfesores("Colegio");
		assertEquals(3, profesCole.size());
		for (Profesor p: profesCole)
			if (p.getId() == profesor4.getId())
				fail("Este profe no es de colegio");
		
		ProfesorDAOImpl.getInstance().deleteProfesor(profesor2);
		List<Profesor> todosProfes = ProfesorDAOImpl.getInstance().readAllProfesores();
		for (Profesor p: todosProfes)
			if (p.getId() == profesor2.getId())
				fail("No se ha borrado a Gloria");
	}

}
