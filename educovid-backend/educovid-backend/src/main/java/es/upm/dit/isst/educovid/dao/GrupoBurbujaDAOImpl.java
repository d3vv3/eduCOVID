package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.dao.GrupoBurbujaDAO;
import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

public class GrupoBurbujaDAOImpl implements GrupoBurbujaDAO{
	private static GrupoBurbujaDAOImpl instance = null;
	private GrupoBurbujaDAOImpl() {
	}
	
	public static GrupoBurbujaDAOImpl getInstance() {
		if(null == instance)
			instance = new GrupoBurbujaDAOImpl();
		return instance;
	}
	
	@Override
	public GrupoBurbuja createGrupoBurbuja(GrupoBurbuja grupoBurbuja) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(grupoBurbuja);
           } catch(Exception e) {
        	   grupoBurbuja = null;
           }
           session.getTransaction().commit();
           session.close();
           return grupoBurbuja;
	}

	@Override
	public GrupoBurbuja readGrupoBurbujabyId(String id) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        GrupoBurbuja grupoBurbuja = session.get(GrupoBurbuja.class, id);
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}
	
	@SuppressWarnings("unchecked")
	public GrupoBurbuja readGrupoBurbujabyAlumnoId(String id) {
		Integer idNew = Integer.parseInt(id);
		List<GrupoBurbuja> gruposBurbuja = new ArrayList<GrupoBurbuja> ();
		System.out.println("Alumno con id " + id);
		GrupoBurbuja grupoBurbuja = null;
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        gruposBurbuja.addAll(session.createQuery("from GrupoBurbuja").list());
        for (GrupoBurbuja g : gruposBurbuja) {
        	for (Alumno a : g.getAlumnos()) {
        		if (a.getId().equals(idNew)) {
        			grupoBurbuja = g;
            		session.getTransaction().commit();
                    session.close();
                    return grupoBurbuja;
        		}	
        	}
        }
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbuja(GrupoBurbuja grupoBurbuja) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(grupoBurbuja);
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyEstadoSanitario(String estadoSanitario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyEstadoDocencia(String estadoDocencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyClase(Clase clase) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyFechaConfinamiento(Date fechaConfinamiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja updateGrupoBurbujabyPrioridad(Integer prioridad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GrupoBurbuja deleteGrupoBurbuja(GrupoBurbuja grupoBurbuja) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(grupoBurbuja);
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbuja() {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja").list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyEstadoSanitario(String estadoSanitario) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.estadoSanitario=" + estadoSanitario).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyEstadoDocencia(String estadoDocencia) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.estadoDocencia=" + estadoDocencia).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyClase(Clase clase) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.clase=" + clase).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyFechaConfinamiento(Date fechaConfinamiento) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.fechaConfinamiento=" + fechaConfinamiento).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyFechaUltimaConmutacion(Date fechaUltConmutacion) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.fechaUltimaConmutacion=" + fechaUltConmutacion).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}

	@Override
	public List<GrupoBurbuja> readAllGruposBurbujabyPrioridad(Integer prioridad) {
		List<GrupoBurbuja> grupoBurbuja = new ArrayList<GrupoBurbuja> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        grupoBurbuja.addAll(session.createQuery("from grupos_burbuja a where grupos_burbuja.prioridad=" + prioridad).list());
        session.getTransaction().commit();
        session.close();
        return grupoBurbuja;
	}


}