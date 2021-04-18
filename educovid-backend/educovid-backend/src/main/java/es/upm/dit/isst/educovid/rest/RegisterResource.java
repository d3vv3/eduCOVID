
package es.upm.dit.isst.educovid.rest;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import es.upm.dit.isst.educovid.aux.Security;
import es.upm.dit.isst.educovid.dao.AlumnoDAOImpl;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.dao.ProfesorDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

@Path("/register")
public class RegisterResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/responsible")
	public Response registerResponsible(String JSONBodyString) throws URISyntaxException {

		System.out.println(JSONBodyString);
		JSONObject body = new JSONObject(JSONBodyString);
		String name = body.getString("name");
		String centerName = body.getString("center");
		String nifNie = body.getString("nifNie");
		String password = body.getString("password");
		Boolean terms = body.getBoolean("terms");

		// 3. Create responsible (class) (needs list of centers)
		List<Clase> classes = new ArrayList<Clase>();
		CentroEducativo newCenter = new CentroEducativo(centerName, classes);
		CentroEducativoDAOImpl.getInstance().createCentroEducativo(newCenter);

		List<CentroEducativo> centros = new ArrayList<CentroEducativo>();
		centros.add(newCenter);

		String salt = Security.getSalt();
		String hash = Security.getHash(password, salt);
		ResponsableCOVID newResponsible = new ResponsableCOVID(name, hash, salt, nifNie, terms, centros);
		// TODO: save terms acceptance
		ResponsableDAOImpl.getInstance().createResponsable(newResponsible);

		// Return a 201 (created)
		return Response.status(Response.Status.CREATED).build();
	}

	@POST
	@Consumes("text/csv")
	@Path("/students")
	public Response registerStudents(String CSVString) throws URISyntaxException, IOException, CsvException {
		
		Map<String, Set<Alumno>> configuration = new HashMap<String, Set<Alumno>>();

		System.out.println(CSVString);
		try (CSVReader reader = new CSVReader(new StringReader(CSVString))) {
			List<String[]> r = reader.readAll();
			r = r.subList(1, r.size());
		    r.forEach(student -> {
		    	System.out.println(configuration.keySet());
		    	System.out.println(Arrays.toString(student));
		    	String salt = Security.getSalt();
		    	String hash = Security.getHash(student[2], salt);
		    	Alumno newStudent = new Alumno(student[1], hash, salt, student[2], "no confinado", null);
		    	AlumnoDAOImpl.getInstance().createAlumno(newStudent);
		    	
			    	try {
			    		Set<Alumno> currentStudents = configuration.get(student[0]);
			    		currentStudents.add(newStudent);
			    		configuration.put(student[0], currentStudents);
			    	} catch(Exception e) {
			    		Set<Alumno> alumnos = new HashSet<Alumno>();
			    		alumnos.add(newStudent);
			    		configuration.put(student[0], alumnos);
			    	};
		    	// TODO: register each row somehow. For example: x = [11,  Jaime Conde Segovia,  mtAAAAA]
		    	// [Clase, Nombre, Numero de matricula]
		    });
		    // Save classes with one BubbleGroup
		    configuration.keySet().forEach(key -> {
		    	// String name = NameGenerator.getName();
		    	String name = RandomWordGenerator.getRandomWord();
		    	System.out.println(name);
		    	System.out.println(configuration.get(key));
			    GrupoBurbuja newBubbleGroup = new GrupoBurbuja(name, "no confinado", "presencial", null, null, null, configuration.get(key));
			    GrupoBurbujaDAOImpl.getInstance().createGrupoBurbuja(newBubbleGroup);
			    List <GrupoBurbuja> bubbleGroups = new ArrayList<>();
			    bubbleGroups.add(newBubbleGroup);
			    Clase newClass = new Clase(key, null, null, bubbleGroups);
			    ClaseDAOImpl.getInstance().createClase(newClass);
			    
		    });
		    
		} catch(Exception e) {
			System.out.println(e);
		};

		return Response.status(Response.Status.CREATED).build();
	}

	@POST
	@Consumes("text/csv")
	@Path("/professors")
	public Response registerProfessors(String CSVString) throws URISyntaxException {

		Map<String, Set<Profesor>> configuration = new HashMap<String, Set<Profesor>>();
		
		System.out.println(CSVString);
		try (CSVReader reader = new CSVReader(new StringReader(CSVString))) {
			List<String[]> r = reader.readAll();
			r = r.subList(1, r.size());
		    r.forEach(professor -> {
		    	System.out.println(Arrays.toString(professor));
		    	String salt = Security.getSalt();
		    	String hash = Security.getHash(professor[2], salt);
		    	Profesor newProfessor = new Profesor(professor[1], hash, salt, professor[2], "no confinado", null, null);
		    	ProfesorDAOImpl.getInstance().createProfesor(newProfessor);
		    	// TODO: register each row somehow. For example: x = [11, Yod Samuel Martín García, 00000000A]
		    	// [Clase, Nombre, NIF/NIE]
		    	
		    	try {
		    		Set<Profesor> currentProfessors = configuration.get(professor[0]);
		    		currentProfessors.add(newProfessor);
		    		configuration.put(professor[0], currentProfessors);
		    	} catch(Exception e) {
		    		Set<Profesor> profesores= new HashSet<>();
		    		profesores.add(newProfessor);
		    		configuration.put(professor[0], profesores);
		    	};
		    });
		    
		 // Update classes with its teachers
		    for (String key : configuration.keySet()) {
		    	Clase oldClase = ClaseDAOImpl.getInstance().readClasebyName(key);
		    	oldClase.setProfesores(configuration.get(key));
		    	ClaseDAOImpl.getInstance().updateClase(oldClase);
			    
		    }
		} catch(Exception e) {
			System.out.println(e);
		};


		// Return a 201 (created)
		return Response.status(Response.Status.CREATED).build();
	}
}
