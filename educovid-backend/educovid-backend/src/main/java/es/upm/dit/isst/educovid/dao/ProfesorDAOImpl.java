package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.dao.ProfesorDAO;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.Profesor;

public class ProfesorDAOImpl implements ProfesorDAO{
	private static ProfesorDAOImpl instance = null;
	private ProfesorDAOImpl() {
	}
	
	public static ProfesorDAOImpl getInstance() {
		if(null == instance)
			instance = new ProfesorDAOImpl();
		return instance;
	}

	@Override
	public Profesor createProfesor(Profesor profesor) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(profesor);
           } catch(Exception e) {
        	   profesor = null;
           }
           session.getTransaction().commit();
           session.close();
           return profesor;
	}

	@Override
	public Profesor readProfesorbyNIFNIE(String nifNie) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Profesor profesor = null;
        Object object = session.createQuery("from Profesor p where p.nifNie='" + nifNie + "'").uniqueResult();
        if (object != null)
        	profesor = (Profesor) object;
        session.getTransaction().commit();
        session.close();
        return profesor;
	}

	@Override
	public Profesor updateProfesor(Profesor profesor) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(profesor);
        session.getTransaction().commit();
        session.close();
        return profesor;
	}

	@Override
	public Profesor updateProfesorbyEstadoSanitario(String estadoSanitario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor updateProfesorbyFechaConfinamiento(Date fechaConfinamiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor updateProfesorbyClases(Set<Clase> clases) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor updateProfesorbyClase(Clase clase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profesor deleteProfesor(Profesor profesor) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(profesor);
        session.getTransaction().commit();
        session.close();
        return profesor;
	}

	@Override
	public List<Profesor> readAllProfesorbyEstadoSanitario(String estadoSanitario) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Profesor> readAllProfesorbyFechaConfinamiento(Date fechaConfinamiento) {
		List<Profesor> profesor = new ArrayList<Profesor> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        profesor.addAll(session.createQuery("from profesores p where profesores.fechaConfinamiento=" +fechaConfinamiento).list());
        session.getTransaction().commit();
        session.close();
        return profesor;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Profesor> readAllProfesorbyClase(Clase clase) {
		List<Profesor> profesor = new ArrayList<Profesor> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        profesor.addAll(session.createQuery("from profesores").list());
        session.getTransaction().commit();
        session.close();
        return profesor;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Profesor> readAllProfesores() {
		List<Profesor> profesor = new ArrayList<Profesor> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        profesor.addAll(session.createQuery("from Profesor").list());
        session.getTransaction().commit();
        session.close();
        return profesor;
	}

}