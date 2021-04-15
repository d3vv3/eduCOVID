package es.upm.dit.isst.educovid.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.upm.dit.isst.educovid.dao.CentroEducativoDAOImpl;
import es.upm.dit.isst.educovid.model.CentroEducativo;

@Path("/centro")
public class CentroResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> readAllCentroEducativo() {
		List<CentroEducativo> centros = CentroEducativoDAOImpl.getInstance().readAllCentroEducativo();
		List<String> nombresCentros = new ArrayList<>();
		for (CentroEducativo centro : centros) {
			nombresCentros.add(centro.getNombre());
		}
		return nombresCentros;
	}
	
}
