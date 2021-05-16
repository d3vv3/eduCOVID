package es.upm.dit.isst.educovid.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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

class GrupoBurbujaDAOImplTest {

	@Test
	void test() {
		GrupoBurbujaDAO burbujadao = GrupoBurbujaDAOImpl.getInstance();
		ClaseDAO clasedao = ClaseDAOImpl.getInstance();
		CentroEducativoDAO centrodao = CentroEducativoDAOImpl.getInstance();
		AlumnoDAO alumnodao = AlumnoDAOImpl.getInstance();
		
		String salt1 = Security.getSalt();
		Alumno alumno1 = new Alumno("Pepe", Security.getHash("S01546", salt1), salt1, "S01546", "no confinado");
		assertNotNull(alumnodao.createAlumno(alumno1), "Alumno 1 no creado.");
		
		List<Alumno> alumnos1 = new ArrayList<Alumno>();
		List<Alumno> alumnos2 = new ArrayList<Alumno>();
		List<Alumno> alumnos3 = new ArrayList<Alumno>();
		List<Alumno> alumnos4 = new ArrayList<Alumno>();
		
		alumnos1.add(alumno1);
		
		GrupoBurbuja burbuja1 = new GrupoBurbuja("Grupo 1", "no confinado", 1, alumnos1);
		GrupoBurbuja burbuja2 = new GrupoBurbuja("Grupo 2", "no confinado", 2, alumnos2);
		GrupoBurbuja burbuja3 = new GrupoBurbuja("Grupo 3", "no confinado", 1, alumnos3);
		GrupoBurbuja burbuja4 = new GrupoBurbuja("Grupo 3", "no confinado", 1, alumnos4);
		
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja1), "GrupoBurbuja 1 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja2), "GrupoBurbuja 2 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja3), "GrupoBurbuja 3 no creado.");
		assertNotNull(burbujadao.createGrupoBurbuja(burbuja4), "GrupoBurbuja 4 no creado.");
		
		List <GrupoBurbuja> grupos1 = new ArrayList<>();
		List <GrupoBurbuja> grupos2 = new ArrayList<>();
		List <GrupoBurbuja> grupos3 = new ArrayList<>();
		
		grupos1.add(burbuja1);
		grupos2.add(burbuja2);
		grupos2.add(burbuja3);
		grupos3.add(burbuja4);
		
		Clase clase1 = new Clase("Clase 1", grupos1.get(0), null, null, null, grupos1);
		Clase clase2 = new Clase("Clase 2", grupos2.get(0), null, null, null, grupos2);
		Clase clase3 = new Clase("Clase 3", grupos3.get(0), null, null, null, grupos3);
		
		assertNotNull(clasedao.createClase(clase1), "Clase 1 no creado.");
		assertNotNull(clasedao.createClase(clase2), "Clase 2 no creado.");
		assertNotNull(clasedao.createClase(clase3), "Clase 3 no creado.");
		
		List<Clase> clases = new ArrayList<>();
		List<Clase> clases2 = new ArrayList<>();
		
		clases.add(clase1);
		clases.add(clase2);
		clases2.add(clase3);
		
		CentroEducativo centro = new CentroEducativo("Colegio", clases);
		CentroEducativo centro2 = new CentroEducativo("Instituto", clases2);
		
		assertNotNull(centrodao.createCentroEducativo(centro), "Centro no creado.");
		assertNotNull(centrodao.createCentroEducativo(centro2), "Centro 2 no creado.");
		
		GrupoBurbuja grupoEnBase = burbujadao.readGrupoBurbujabyAlumnoId(alumno1.getId());
		assertEquals(burbuja1.getId(), grupoEnBase.getId());
		
		List<GrupoBurbuja> gruposColegio = burbujadao.readAllGruposBurbujabyCentro(centro.getNombre());
		for (GrupoBurbuja g: gruposColegio)
			System.out.println(g.getNombre());
		assertEquals(3, gruposColegio.size());
		
		for (GrupoBurbuja g: gruposColegio)
			if (g.getId() == burbuja4.getId())
				fail("Burbuja no pedida.");
	}

}
