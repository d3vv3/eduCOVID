package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.sun.xml.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;

@MappedSuperclass
public class Usuario implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String hash;
	@Column(nullable = false)
	private String salt;
	private static final long serialVersionUID = 1L;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String hash, String salt) {
		super();
		this.nombre = nombre;
		this.hash = hash;
		this.salt = salt;
		System.out.println("User name: '" + this.nombre + "'");
		System.out.println("User salt: '" + this.salt.toString() + "'");
		System.out.println("User hash: '" + this.hash.toString() + "'");
	}
	
//	public Usuario(String nombre, String password) {
//		super();
//		this.nombre = nombre;
//		this.setPassword(password);
//	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
		System.out.println("Setting id: '" + id + "'");
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
		System.out.println("Setting nombre: '" + nombre + "'");
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
