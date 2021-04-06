package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

import es.upm.dit.isst.educovid.dao.AlumnoDAO;
import es.upm.dit.isst.educovid.model.Alumno;

<<<<<<< HEAD
//@ApplicationPath("/api")
public class EducovidDAOImpl implements EducovidDAO{
=======
public class AlumnoDAOImpl implements AlumnoDAO{
>>>>>>> minor changes to fix errors

	@Override
	public Alumno createAlumno(Alumno alumno) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(alumno);
           } catch(Exception e) {
        	   alumno = null;
           }
           session.getTransaction().commit();
           session.close();
           return alumno;
	}

	@Override
	public Alumno readAlumnobyMatNum(String numeroMatricula) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Alumno alumno = session.get(Alumno.class, numeroMatricula);
        session.getTransaction().commit();
        session.close();
        return alumno;
	}

	@Override
	public Alumno updateAlumno(Alumno Alumno) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(Alumno);
        session.getTransaction().commit();
        session.close();
        return Alumno;
	}

	@Override
	public Alumno updateAlumnobyEstadoSanitario(String estadoSanitario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno updateAlumnobyFechaConfinamiento(Date fechaConfinamiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno updateAlumnobyGrupo(String grupoBurbuja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno deleteAlumno(Alumno alumno) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(alumno);
        session.getTransaction().commit();
        session.close();
        return alumno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Alumno> readAllAlumnos() {
		List<Alumno> alumnos = new ArrayList<Alumno> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        alumnos.addAll(session.createQuery("from alumnos").list());
        session.getTransaction().commit();
        session.close();
        return alumnos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Alumno> readAllAlumnosbyGroup(String grupoBurbuja) {
		List<Alumno> alumnos = new ArrayList<Alumno> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        alumnos.addAll(session.createQuery("from alumnos a where alumnos.grupoBurbuja=" + grupoBurbuja).list());
        session.getTransaction().commit();
        session.close();
        return alumnos;
	}

	@Override
	public List<Alumno> readAllAlumnosbyEstadoSanitario(String estadoSanitario) {
		List<Alumno> alumnos = new ArrayList<Alumno> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        alumnos.addAll(session.createQuery("from alumnos a where alumnos.estadoSanitario=" + estadoSanitario).list());
        session.getTransaction().commit();
        session.close();
        return alumnos;
	}

	@Override
	public List<Alumno> readAllAlumnosbyFechaConfinamiento(Date fechaConfinamiento) {
		List<Alumno> alumnos = new ArrayList<Alumno> ();
        Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        alumnos.addAll(session.createQuery("from alumnos a where alumnos.fechaConfinamiento=" + fechaConfinamiento).list());
        session.getTransaction().commit();
        session.close();
        return alumnos;
	}

	

}
