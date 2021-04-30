package es.upm.dit.isst.educovid.dao;

import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public class ResponsableDAOImpl implements ResponsableDAO{
	private static ResponsableDAOImpl instance = null;
	private ResponsableDAOImpl() {
	}
	
	public static ResponsableDAOImpl getInstance() {
		if(null == instance)
			instance = new ResponsableDAOImpl();
		return instance;
	}
	
	@Override
	public ResponsableCOVID createResponsable(ResponsableCOVID responsableCOVID) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(responsableCOVID);
           } catch(Exception e) {
        	   responsableCOVID = null;
           }
           session.getTransaction().commit();
           session.close();
           return responsableCOVID;
	}

	@Override
	public ResponsableCOVID readResponsablebyNIFNIE(String nifNie) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        ResponsableCOVID responsable = null;
        Object object = session.createQuery("from ResponsableCOVID r where r.nifNie='" + nifNie + "'").uniqueResult();
        if (object != null)
        	responsable = (ResponsableCOVID) object;
        session.getTransaction().commit();
        session.close();
        return responsable;
	}

	@Override
	public ResponsableCOVID readResponsablebyCentro(CentroEducativo centro) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        ResponsableCOVID responsableCOVID = session.get(ResponsableCOVID.class, centro);
        session.getTransaction().commit();
        session.close();
        return responsableCOVID;
	}

	@Override
	public ResponsableCOVID updateResponsable(ResponsableCOVID responsableCOVID) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(responsableCOVID);
        session.getTransaction().commit();
        session.close();
        return responsableCOVID;
	}

	@Override
	public ResponsableCOVID updateResponsablebyNIFNIE(String nifNie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsableCOVID updateResponsablebyCentro(CentroEducativo Centro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsableCOVID updateResponsablebyCentros(List<CentroEducativo> Centro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsableCOVID deleteResponsable(ResponsableCOVID responsableCOVID) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(responsableCOVID);
        session.getTransaction().commit();
        session.close();
        return responsableCOVID;
	}

	@Override
	public ResponsableCOVID readResponsablebyId(String id) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		ResponsableCOVID responsable = session.get(ResponsableCOVID.class, Integer.parseInt(id));
		session.getTransaction().commit();
		session.close();
		return responsable;
	}

}