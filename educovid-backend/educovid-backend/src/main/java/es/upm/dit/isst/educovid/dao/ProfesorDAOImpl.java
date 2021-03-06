package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.Profesor;

public class ProfesorDAOImpl implements ProfesorDAO {
	private static ProfesorDAOImpl instance = null;

	private ProfesorDAOImpl() {
	}

	public static ProfesorDAOImpl getInstance() {
		if (null == instance)
			instance = new ProfesorDAOImpl();
		return instance;
	}

	@Override
	public Profesor createProfesor(Profesor profesor) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(profesor);
		} catch (Exception e) {
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
		Query q = session.createQuery("select p from Profesor p where p.nifNie = :nifNie");
		q.setParameter("nifNie", nifNie);
		List<Profesor> ps = q.getResultList();
		if (ps.size() > 0) profesor = ps.get(0);
		session.getTransaction().commit();
		session.close();
		return profesor;
	}

	@Override
	public Profesor readProfesorbyId(String id) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Profesor profesor = session.get(Profesor.class, Long.parseLong(id));
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
		List<Clase> allClasses = ClaseDAOImpl.getInstance().readAllClases();
		for (Clase c : allClasses) {
			Set<Profesor> oldProfesores = c.getProfesores();
			Set<Profesor> newProfesores = new HashSet<>();
			oldProfesores.forEach((e) -> {
				if (!e.getId().equals(profesor.getId())) {
					// System.out.print("Profesor " + e.getNombre() + " anadido a " + c.getNombre() + " \n");
					newProfesores.add(e);
				}
			});
			c.setProfesores(newProfesores);
			ClaseDAOImpl.getInstance().updateClase(c);
		}
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
	public List<Profesor> readAllProfesorbyClase(Clase clase) {
		List<Profesor> profesor = new ArrayList<Profesor>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		profesor.addAll(session.createQuery("from Profesor").list());
		session.getTransaction().commit();
		session.close();
		return profesor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Profesor> readAllProfesores() {
		List<Profesor> profesor = new ArrayList<Profesor>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		profesor.addAll(session.createQuery("from Profesor").list());
		session.getTransaction().commit();
		session.close();
		return profesor;
	}

	public List<Profesor> readAllProfesores(String nombreCentro) {
		List<Profesor> profesoresCentro = new ArrayList<Profesor>();
		List<Long> idProfesoresCentro = new ArrayList<Long>();
		List<Clase> clasesCentro = ClaseDAOImpl.getInstance().readAllClases(nombreCentro);
		for (Clase c : clasesCentro) {
			for (Profesor p : c.getProfesores()) {
				if (!idProfesoresCentro.contains(p.getId())) {
					profesoresCentro.add(p);
					idProfesoresCentro.add(p.getId());
				}
			}
		}
		return profesoresCentro;
	}
}