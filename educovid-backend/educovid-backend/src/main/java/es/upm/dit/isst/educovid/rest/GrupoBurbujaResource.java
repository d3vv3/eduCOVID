package es.upm.dit.isst.educovid.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAOImpl;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

@Path("/grupo")
public class GrupoBurbujaResource {

	@GET
	@Path("alumno/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readGrupoAlumnobyId(@PathParam("id") String id) {
		GrupoBurbuja g = GrupoBurbujaDAOImpl.getInstance().readGrupoBurbujabyAlumnoId(id);
		if (g == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		GrupoBurbuja gNew = new GrupoBurbuja(g.getNombre(), g.getEstadoSanitario(), g.getEstadoDocencia(), null, null, null, null);
		return Response.ok(gNew, MediaType.APPLICATION_JSON).build();
	}
}
