package es.upm.dit.isst.educovid.dao;

import java.util.*;

import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public interface ResponsableDAO {
	
	public ResponsableCOVID createResponsable (ResponsableCOVID ResponsableCOVID);
	public ResponsableCOVID readResponsablebyNIFNIE (String nifNie);
	public ResponsableCOVID readResponsablebyId (String id);
	public ResponsableCOVID updateResponsable (ResponsableCOVID ResponsableCOVID);
	public ResponsableCOVID updateResponsablebyNIFNIE (String nifNie);
	public ResponsableCOVID updateResponsablebyCentro (CentroEducativo Centro);
	public ResponsableCOVID updateResponsablebyCentros (List<CentroEducativo> Centro);
	public ResponsableCOVID deleteResponsable (ResponsableCOVID ResponsableCOVID);
	ResponsableCOVID readResponsablebyCentro(CentroEducativo centro);
	
}