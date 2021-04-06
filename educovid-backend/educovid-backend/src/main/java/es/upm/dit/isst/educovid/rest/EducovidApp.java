package es.upm.dit.isst.educovid.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class EducovidApp extends ResourceConfig {
	public EducovidApp() {
		packages("es.upm.dit.isst.educovid.rest");
	}
}
