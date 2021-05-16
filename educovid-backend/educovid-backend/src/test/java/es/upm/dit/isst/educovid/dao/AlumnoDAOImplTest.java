package es.upm.dit.isst.educovid.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

class AlumnoDAOImplTest {

	@Test
	void testGetNombreCentro() {
		AlumnoDAO alumnodao = AlumnoDAOImpl.getInstance();
		
		String salt1 = Security.getSalt();
		String salt2 = Security.getSalt();
		Alumno alumno1 = new Alumno("Pepe", Security.getHash("S01546", salt1), salt1, "S01546", "no confinado", null);
		Alumno alumno2 = new Alumno("Mario", Security.getHash("S01547", salt2), salt2, "S01547", "confinado", null);
		
		assertNotNull("Alumno 1 no creado.", alumnodao.createAlumno(alumno1));
		assertNotNull("Alumno 2 no creado.", alumnodao.createAlumno(alumno2));
		
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		alumnos1.add(alumno1);
		alumnos2.add(alumno2);
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", null, 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 2", "no confinado", null, 2, alumnos2);
		//TODO
	}

	@Test
	void testReadAlumnobyMatNumCenter() {
		//TODO
	}

	@Test
	void testReadAllAlumnosCentro() {
		//TODO
	}

}
