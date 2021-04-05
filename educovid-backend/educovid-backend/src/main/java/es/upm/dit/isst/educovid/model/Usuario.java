package es.upm.dit.isst.educovid.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Usuario implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	private String hash;
	private String salt;
	private static final long serialVersionUID = 1L;
	
	public Usuario() {
		super();
	}
	
	public Usuario(Integer id, String nombre, String hash, String salt) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.hash = hash;
		this.salt = salt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}