package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

public interface GrupoBurbujaDAO {

	public GrupoBurbuja createGrupoBurbuja (GrupoBurbuja grupoBurbuja);
	public GrupoBurbuja readGrupoBurbujabyId (String id);
	public GrupoBurbuja updateGrupoBurbuja (GrupoBurbuja GrupoBurbuja);
	public GrupoBurbuja updateGrupoBurbujabyEstadoSanitario (String estadoSanitario);
	//public GrupoBurbuja updateGrupoBurbujabyEstadoDocencia (String estadoDocencia);
	public GrupoBurbuja updateGrupoBurbujabyClase(Clase clase);
	public GrupoBurbuja updateGrupoBurbujabyFechaConfinamiento(Date fechaConfinamiento);
	//public GrupoBurbuja updateGrupoBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion);
	public GrupoBurbuja updateGrupoBurbujabyPrioridad(Integer prioridad);
	public GrupoBurbuja deleteGrupoBurbuja (GrupoBurbuja GrupoBurbuja);
	public List<GrupoBurbuja> readAllGruposBurbuja();
	public GrupoBurbuja readGrupoBurbujabyAlumnoId(Long id);
	//public List<GrupoBurbuja> readAllGruposBurbujabyEstadoSanitario(String estadoSanitario);
	//public List<GrupoBurbuja> readAllGruposBurbujabyEstadoDocencia(String estadoDocencia);
	public List<GrupoBurbuja> readAllGruposBurbujabyClase(Clase clase);
	//public List<GrupoBurbuja> readAllGruposBurbujabyFechaConfinamiento(Date fechaConfinamiento);
	// public List<GrupoBurbuja> readAllGruposBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion);
	//public List<GrupoBurbuja> readAllGruposBurbujabyPrioridad(Integer prioridad);
	public List<GrupoBurbuja> readAllGruposBurbujabyCentro(String nombreCentro);
		
}