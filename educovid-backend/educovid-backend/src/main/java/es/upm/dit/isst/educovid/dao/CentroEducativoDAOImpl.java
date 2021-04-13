package es.upm.dit.isst.educovid.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.dao.CentroEducativoDAO;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public class CentroEducativoDAOImpl implements CentroEducativoDAO{
	private static CentroEducativoDAOImpl instance = null;
	private CentroEducativoDAOImpl() {
	}
	
	public static CentroEducativoDAOImpl getInstance() {
		if(null == instance)
			instance = new CentroEducativoDAOImpl();
		return instance;
	}
	
	@Override
	public CentroEducativo createCentroEducativo(CentroEducativo centroEducativo) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(centroEducativo);
           } catch(Exception e) {
        	   centroEducativo = null;
           }
           session.getTransaction().commit();
           session.close();
           return centroEducativo;
	}

	@Override
	public CentroEducativo readCentroEducativobyName(String nombre) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        CentroEducativo centroEducativo = session.get(CentroEducativo.class, nombre);
        session.getTransaction().commit();
        session.close();
        return centroEducativo;
	}

	@Override
	public CentroEducativo readCentroEducativobyId(String id) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        CentroEducativo centroEducativo = session.get(CentroEducativo.class, id);
        session.getTransaction().commit();
        session.close();
        return centroEducativo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CentroEducativo> readAllCentroEducativobyResponsable(ResponsableCOVID responsable) {
		List<CentroEducativo> centros = new ArrayList<CentroEducativo> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        centros.addAll(session.createQuery("from centros_educativos where centros_educativos.ResponsableCOVID="+ responsable).list());
        session.getTransaction().commit();
        session.close();
        return centros;
	}

	@Override
	public CentroEducativo updateCentroEducativo(CentroEducativo CentroEducativo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CentroEducativo updateCentroEducativobyResponsable(ResponsableCOVID responsable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CentroEducativo deleteCentroEducativo(CentroEducativo centroEducativo) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(centroEducativo);
        session.getTransaction().commit();
        session.close();
        return centroEducativo;
	}


}