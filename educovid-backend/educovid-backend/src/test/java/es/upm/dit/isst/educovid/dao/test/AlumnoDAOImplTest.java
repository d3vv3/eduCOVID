package es.upm.dit.isst.educovid.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.AlumnoDAO;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAO;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAO;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAO;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

class AlumnoDAOImplTest {

	static AlumnoDAO alumnodao = AlumnoDAOImpl.getInstance();
	static GrupoBurbujaDAO burbujadao = GrupoBurbujaDAOImpl.getInstance();
	static ClaseDAO clasedao = ClaseDAOImpl.getInstance();
	static CentroEducativoDAO centrodao = CentroEducativoDAOImpl.getInstance();
	
	@BeforeAll
	static void createObjects() {
		String salt1 = Security.getSalt();
		String salt2 = Security.getSalt();
		String salt3 = Security.getSalt();
		String salt4 = Security.getSalt();
		Alumno alumno1 = new Alumno("Pepe", Security.getHash("S01546", salt1), salt1, "S01546", "no confinado");
		Alumno alumno2 = new Alumno("Mario", Security.getHash("S01547", salt2), salt2, "S01547", "confinado");
		Alumno alumno3 = new Alumno("Lorena", Security.getHash("S01548", salt3), salt3, "S01548", "no confinado");
		Alumno alumno4 = new Alumno("Alberto", Security.getHash("S01549", salt4), salt4, "S01549", "no confinado");
		
		assertNotNull(alumnodao.createAlumno(alumno1), "Alumno 1 no creado.");
		assertNotNull(alumnodao.createAlumno(alumno2), "Alumno 2 no creado.");
		assertNotNull(alumnodao.createAlumno(alumno3), "Alumno 3 no creado.");
		assertNotNull(alumnodao.createAlumno(alumno4), "Alumno 4 no creado.");
		
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		
		alumnos1.add(alumno1);
		alumnos2.add(alumno2);
		alumnos2.add(alumno3);
		alumnos3.add(alumno4);
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos3);
		
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja1), "GrupoBurbuja 1 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja2), "GrupoBurbuja 2 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja3), "GrupoBurbuja 3 no creado.");
		
		List <GrupoBurbuja> grupos1 = new ArrayList<>();
		List <GrupoBurbuja> grupos2 = new ArrayList<>();
		List <GrupoBurbuja> grupos3 = new ArrayList<>();
		
		grupos1.add(burbuja1);
		grupos2.add(burbuja2);
		grupos3.add(burbuja3);
		
		Clase clase1 = new Clase("1ºA", grupos1.get(0), null, null, null, grupos1);
		Clase clase2 = new Clase("2ºA", grupos2.get(0), null, null, null, grupos2);
		Clase clase3 = new Clase("1ºA", grupos3.get(0), null, null, null, grupos3);
		
		assertNotNull(clasedao.createClase(clase1), "Clase 1 no creado.");
		assertNotNull(clasedao.createClase(clase2), "Clase 2 no creado.");
		assertNotNull(clasedao.createClase(clase3), "Clase 3 no creado.");
		
		List<Clase> clases1 = new ArrayList<>();
		List<Clase> clases2 = new ArrayList<>();
		
		clases1.add(clase1);
		clases1.add(clase2);
		clases2.add(clase3);
		
		CentroEducativo centro1 = new CentroEducativo("Colegio", clases1);
		CentroEducativo centro2 = new CentroEducativo("Instituto", clases2);
	
		assertNotNull(centrodao.createCentroEducativo(centro1), "Centro 1 no creado.");
		assertNotNull(centrodao.createCentroEducativo(centro2), "Centro 2 no creado.");
	}
	
	@Test
	void testGetNombreCentro() {
		Alumno a1 = alumnodao.readAlumnobyMatNumCenter("S01546", "Colegio");
		Alumno a2 = alumnodao.readAlumnobyMatNumCenter("S01547", "Colegio");
		Alumno a3 = alumnodao.readAlumnobyMatNumCenter("S01548", "Colegio");
		Alumno a4 = alumnodao.readAlumnobyMatNumCenter("S01549", "Instituto");
		
		assertEquals("Pepe", a1.getNombre());
		assertEquals("Mario", a2.getNombre());
		assertEquals("Lorena", a3.getNombre());
		assertEquals("Alberto", a4.getNombre());
		
		String nombreCentroPepe = alumnodao.getNombreCentro(a1);
		String nombreCentroMario = alumnodao.getNombreCentro(a2);
		String nombreCentroLorena = alumnodao.getNombreCentro(a3);
		String nombreCentroAlberto = alumnodao.getNombreCentro(a4);
		
		assertEquals("Colegio", nombreCentroPepe);
		assertEquals("Colegio", nombreCentroMario);
		assertEquals("Colegio", nombreCentroLorena);
		assertEquals("Instituto", nombreCentroAlberto);
		
		List<Alumno> alumnos1 = alumnodao.readAllAlumnos("Colegio");
		List<Alumno> alumnos2 = alumnodao.readAllAlumnos("Instituto");
		
		System.out.println(alumnos1);
		System.out.println(alumnos2);
		
		assertTrue(alumnos1.get(0).getId() == a1.getId());
		assertTrue(alumnos1.get(1).getId() == a2.getId());
		assertTrue(alumnos1.get(2).getId() == a3.getId());
		assertTrue(alumnos2.get(0).getId() == a4.getId());
	} 

}
