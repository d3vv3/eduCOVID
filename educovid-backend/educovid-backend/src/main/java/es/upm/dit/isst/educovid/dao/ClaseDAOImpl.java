package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Clase;

public class ClaseDAOImpl implements ClaseDAO{
	private static ClaseDAOImpl instance = null;
	private ClaseDAOImpl() {
	}
	
	public static ClaseDAOImpl getInstance() {
		if(null == instance)
			instance = new ClaseDAOImpl();
		return instance;
	}
	
	@Override
	public Clase createClase(Clase clase) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(clase);
           } catch(Exception e) {
        	   clase = null;
           }
           session.getTransaction().commit();
           session.close();
           return clase;
	}

	@Override
	public Clase readClasebyId(String id) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Clase clase = session.get(Clase.class, id);
        session.getTransaction().commit();
        session.close();
        return clase;
	}
	
	@Override
	public Clase readClasebyName(String name) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Clase clase = null;
        Object object = session.createQuery("from Clase c where c.nombre='" + name + "'").uniqueResult();
        if (object != null)
        	clase = (Clase) object;
        session.getTransaction().commit();
        session.close();
        return clase;
	}

	@Override
	public Clase updateClase(Clase clase) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(clase);
        session.getTransaction().commit();
        session.close();
        return clase;
	}

	@Override
	public Clase deleteClase(Clase clase) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(clase);
        session.getTransaction().commit();
        session.close();
        return clase;
	}

	@Override
	public List<Clase> readAllClases() {
		List<Clase> clases = new ArrayList<Clase> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        clases.addAll(session.createQuery("from Clase").list());
        session.getTransaction().commit();
        session.close();
        return clases;
	}



}
