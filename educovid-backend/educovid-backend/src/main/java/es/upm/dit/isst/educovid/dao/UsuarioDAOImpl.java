package es.upm.dit.isst.educovid.dao;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.Profesor;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;
import es.upm.dit.isst.educovid.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO{
	private static UsuarioDAOImpl instance = null;
	private UsuarioDAOImpl() {
	}
	
	public static UsuarioDAOImpl getInstance() {
		if(null == instance)
			instance = new UsuarioDAOImpl();
		return instance;
	}
	
	@Override
	public Usuario createUsuario(Usuario usuario) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
           try {
             session.save(usuario);
           } catch(Exception e) {
        	   usuario = null;
           }
       session.getTransaction().commit();
       session.close();
       return usuario;
	}

	@Override
	public Usuario readUsuariobyId(String id) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Alumno alumno =  session.get(Alumno.class, id);
        Profesor profesor =  session.get(Profesor.class, id);
        ResponsableCOVID responsable =  session.get(ResponsableCOVID.class, Long.parseLong(id));
        if (alumno != null)
        	return (Usuario) alumno;
        if (profesor != null)
        	return (Usuario) profesor;
        if (responsable != null)
        	return (Usuario) responsable;
        session.getTransaction().commit();
        session.close();
        return null;
	}

	@Override
	public Usuario readUsuariobyName(String name) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Usuario usuario = session.get(Usuario.class, name); // TODO: MAL
        session.getTransaction().commit();
        session.close();
        return usuario;
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.saveOrUpdate(usuario);
        session.getTransaction().commit();
        session.close();
        return usuario;
	}

	@Override
	public Usuario updateUsuariobyId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario updateUsuariobyName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario deleteUsuario(Usuario usuario) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        session.delete(usuario);
        session.getTransaction().commit();
        session.close();
        return usuario;
	}

	
	

}