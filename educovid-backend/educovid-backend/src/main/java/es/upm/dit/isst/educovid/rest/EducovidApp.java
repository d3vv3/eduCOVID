package es.upm.dit.isst.educovid.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import es.upm.dit.isst.educovid.aux.TimerPresencialidad;

@ApplicationPath("rest")
public class EducovidApp extends ResourceConfig {
	public EducovidApp() {
		packages("es.upm.dit.isst.educovid.rest");
		TimerPresencialidad.getInstance().iniciarTemporizador();
		register(new CorsFilter());
	}
}
