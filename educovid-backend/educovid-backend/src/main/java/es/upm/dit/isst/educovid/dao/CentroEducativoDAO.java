
package es.upm.dit.isst.educovid.dao;

import java.util.List;

import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public interface CentroEducativoDAO {

	public CentroEducativo createCentroEducativo (CentroEducativo centroEducativo);
	public CentroEducativo readCentroEducativobyName (String nombre);
	public CentroEducativo readCentroEducativobyId (String id);
	public List<CentroEducativo> readAllCentroEducativobyResponsable (ResponsableCOVID responsable);
	public CentroEducativo updateCentroEducativo (CentroEducativo CentroEducativo);
	public CentroEducativo updateCentroEducativobyResponsable (ResponsableCOVID responsable);
	public CentroEducativo deleteCentroEducativo (CentroEducativo CentroEducativo);
	
}