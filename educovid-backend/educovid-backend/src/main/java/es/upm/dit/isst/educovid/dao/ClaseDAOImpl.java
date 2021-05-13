package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.aux.NotificacionesPresencialidad;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

public class ClaseDAOImpl implements ClaseDAO {
	private static ClaseDAOImpl instance = null;

	private ClaseDAOImpl() {
	}

	public static ClaseDAOImpl getInstance() {
		if (null == instance)
			instance = new ClaseDAOImpl();
		return instance;
	}

	@Override
	public Clase createClase(Clase clase) {
		// Validate:
		// 1. Numero de matricula unique per alumno per center
		// 2. Alumno unique per Grupo Burbuja
		Set<String> nombresGrupoBurbuja = new HashSet<String>();
		List<GrupoBurbuja> grupos = clase.getGruposBurbuja();
		for (GrupoBurbuja grupo : clase.getGruposBurbuja()) {
			if (nombresGrupoBurbuja.contains(clase.getNombre())) {
				System.out.println("There cannot be two GrupoBurbuja with the same nombre in the same Clase");
				return null;
			} else {
				nombresGrupoBurbuja.add(clase.getNombre());
			}
		}
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(clase);
		} catch (Exception e) {
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
		Clase clase = session.get(Clase.class, Integer.parseInt(id));
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
		List<Clase> clases = new ArrayList<Clase>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		clases.addAll(session.createQuery("from Clase").list());
		session.getTransaction().commit();
		session.close();
		return clases;
	}

	public Clase updatePresencialGroup(Clase clase) {
		List<GrupoBurbuja> grupos = clase.getGruposBurbuja();
		GrupoBurbuja presencialActual = clase.getBurbujaPresencial();
		Integer prioridadSiguiente = (presencialActual.getPrioridad() % grupos.size()) + 1;
		GrupoBurbuja nuevoPresencial = null;
		for (GrupoBurbuja b : grupos) {
			if (b.getPrioridad() == prioridadSiguiente) {
				nuevoPresencial = b;
				break;
			}
		}
		clase.setBurbujaPresencial(nuevoPresencial);
		updateClase(clase);
		return clase;
	}
	
	
}
