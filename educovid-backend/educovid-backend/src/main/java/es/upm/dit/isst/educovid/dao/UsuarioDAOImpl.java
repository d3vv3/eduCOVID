package es.upm.dit.isst.educovid.dao;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.dao.UsuarioDAO;
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
	public Usuario readUsuariobyId(Integer id) {
		Session session = SessionFactoryService.get().openSession();
        session.beginTransaction();
        Usuario usuario = session.get(Usuario.class, id);
        session.getTransaction().commit();
        session.close();
        return usuario;
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