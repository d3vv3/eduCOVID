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

class ClaseDAOImplTest {

	static AlumnoDAO alumnodao = AlumnoDAOImpl.getInstance();
	static GrupoBurbujaDAO burbujadao = GrupoBurbujaDAOImpl.getInstance();
	static ClaseDAO clasedao = ClaseDAOImpl.getInstance();
	static CentroEducativoDAO centrodao = CentroEducativoDAOImpl.getInstance();
	
	@BeforeAll
	static void fillDataBase() {
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		List<Alumno> alumnos4 = new ArrayList<Alumno>();
		List<Alumno> alumnos5 = new ArrayList<Alumno>();
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 2", "no confinado", 2, alumnos2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 3", "no confinado", 3, alumnos3);
		GrupoBurbuja burbuja4 = new GrupoBurbuja("Grupo 4", "no confinado", 1, alumnos4);
		GrupoBurbuja burbuja5 = new GrupoBurbuja("Grupo 5", "no confinado", 2, alumnos5);
		
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja1), "GrupoBurbuja 1 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja2), "GrupoBurbuja 2 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja3), "GrupoBurbuja 3 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja4), "GrupoBurbuja 4 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja5), "GrupoBurbuja 5 no creado.");
		
		List <GrupoBurbuja> grupos = new ArrayList<>();
		List <GrupoBurbuja> grupos2 = new ArrayList<>();
	
		
		grupos.add(burbuja1);
		grupos.add(burbuja2);
		grupos.add(burbuja3);
		grupos2.add(burbuja4);
		grupos2.add(burbuja5);
		
		Clase clase = new Clase("1ºA", grupos.get(0), null, null, null, grupos);
		Clase clase2 = new Clase("2ºA", grupos2.get(0), null, null, null, grupos2);
		
		assertNotNull(clasedao.createClase(clase), "Clase 1 no creado.");
		assertNotNull(clasedao.createClase(clase2), "Clase 2 no creado.");
	}
	
	@Test
	void testClaseDAO() {
		List<Clase> clases = clasedao.readAllClases();
		Clase clase1 = null;
		Clase clase2 = null;
		for (Clase c: clases) {
			if (c.getNombre() == "1ºA") {
				clase1 = c;
			} else if (c.getNombre() == "2ºA") {
				clase2 = c;
			} else {
				fail("Problema en las clases.");
			}
		}
		
		List<GrupoBurbuja> gruposABorrar = clase2.getGruposBurbuja();
		GrupoBurbuja grupoABorrar1 = gruposABorrar.get(0);
		GrupoBurbuja grupoABorrar2 = gruposABorrar.get(1);
		
		GrupoBurbuja presencial1 = clase1.getBurbujaPresencial();
		GrupoBurbuja presencial2 = null;
		GrupoBurbuja presencial3 = null;

		for (GrupoBurbuja g : clase1.getGruposBurbuja()) {
			switch (g.getPrioridad().intValue()) {
			case 1:
				break;
			case 2:
				presencial2 = g;
				break;
			case 3:
				presencial3 = g;
				break;
			default:
				break;
			}
		}

		clasedao.updatePresencialGroup(clase1);
		clase1 = clasedao.readClasebyId(clase1.getId().toString());
		assertEquals(presencial2.getId(), clase1.getBurbujaPresencial().getId());
		
		clasedao.updatePresencialGroup(clase1);
		clase1 = clasedao.readClasebyId(clase1.getId().toString());
		assertEquals(presencial3.getId(), clase1.getBurbujaPresencial().getId());
		
		clasedao.updatePresencialGroup(clase1);
		clase1 = clasedao.readClasebyId(clase1.getId().toString());
		assertEquals(presencial1.getId(), clase1.getBurbujaPresencial().getId());
		
		clasedao.deleteClase(clase2);
		clases = clasedao.readAllClases();
		for (Clase c: clases)
			if (c.getId() == clase2.getId())
				fail("La clase no se borró.");
		
		List<GrupoBurbuja> grupos = burbujadao.readAllGruposBurbuja();
		for (GrupoBurbuja g: grupos)
			if (g.getId() == grupoABorrar1.getId() || g.getId() == grupoABorrar2.getId())
				fail("El grupo " + g.getNombre() + " no se borró.");
	}

}
