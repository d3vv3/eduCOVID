package es.upm.dit.isst.educovid.dao;

import es.upm.dit.isst.educovid.model.Usuario;

public interface UsuarioDAO {

	public Usuario createUsuario (Usuario Usuario);
	public Usuario readUsuariobyId(Integer id);
	public Usuario readUsuariobyName(String name);
	public Usuario updateUsuario (Usuario Usuario);
	public Usuario updateUsuariobyId (String id);
	public Usuario updateUsuariobyName (String name);
	public Usuario deleteUsuario (Usuario Usuario);
	
}