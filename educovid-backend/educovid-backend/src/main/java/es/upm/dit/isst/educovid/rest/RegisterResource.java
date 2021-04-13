
package es.upm.dit.isst.educovid.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.StringReader;

import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.dao.ResponsableDAOImpl;


@Path("/register")
public class RegisterResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/responsible")
	public Response registerResponsible(String JSONBodyString) throws URISyntaxException {

		System.out.println(JSONBodyString);
		JSONObject body = new JSONObject(JSONBodyString);
		String centerName = body.getString("center");
		String nifNie = body.getString("nifNie");
		String password = body.getString("nifNie");
		Boolean terms = body.getBoolean("terms");

		// 3. Create responsible (class) (needs list of centers)
		List<CentroEducativo> centros = new ArrayList<CentroEducativo>();
		ResponsableCOVID newResponsible = new ResponsableCOVID(nifNie, centros);
		// TODO: save terms acceptance
		newResponsible.setPassword(password);
		ResponsableCOVID responsible = ResponsableDAOImpl.getInstance().createResponsable(newResponsible);

		List<Clase> classes = new ArrayList<Clase>();
		CentroEducativo newCenter = new CentroEducativo(centerName, responsible, classes);
		CentroEducativo center = CentroEducativoDAOImpl.getInstance().createCentroEducativo(newCenter);

		// Return a 201 (created)
		return Response.status(Response.Status.CREATED).build();
	}

	@POST
	@Consumes("text/csv")
	@Path("/students")
	public Response registerStudents(String CSVString) throws URISyntaxException, IOException, CsvException {

		System.out.println(CSVString);
		try (CSVReader reader = new CSVReader(new StringReader(CSVString))) {
			List<String[]> r = reader.readAll();
			r = r.subList(1, r.size());
		    r.forEach(student -> {
		    	System.out.println(Arrays.toString(student));
		    	Alumno newStudent = new Alumno(nifNie, centros);
		    	// TODO: register each row somehow. For example: x = [11,  Jaime Conde Segovia,  mtAAAAA]
		    	// [Clase, Nombre, Numero de matricula]
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

		System.out.println(CSVString);
		try (CSVReader reader = new CSVReader(new StringReader(CSVString))) {
			List<String[]> r = reader.readAll();
			r = r.subList(1, r.size());
		    r.forEach(professor -> {
		    	System.out.println(Arrays.toString(professor));
		    	// TODO: register each row somehow. For example: x = [11,  Jaime,  Conde Segovia,  mtAAAAA]
		    	// [Clase, Nombre, Apellidos, NIF/NIE]
		    });
		} catch(Exception e) {
			System.out.println(e);
		};


		// Return a 201 (created)
		return Response.status(Response.Status.CREATED).build();
	}
}
