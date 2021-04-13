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

@MappedSuperclass
public class Usuario implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private byte[] hash;
	@Column(nullable = false)
	private byte[] salt;
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}

	public Usuario(String nombre, String password) {
		super();
		this.nombre = nombre;
		this.setPassword(password);
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
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	public void generateAndSaveSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		this.setSalt(salt);
	}
	public void setPassword(String password) {
		this.generateAndSaveSalt();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(this.salt);
			byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
			this.hash = hashedPassword;
	    }
	    catch (NoSuchAlgorithmException e) {
	        System.err.println("SHA-512 is not a valid message digest algorithm");
	        System.err.println(e);
	    }
	}
	public Boolean checkPassword(String passwordToCheck) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(this.salt);
			byte[] hashedPasswordToCheck = md.digest(passwordToCheck.getBytes(StandardCharsets.UTF_8));
			return hashedPasswordToCheck.equals(this.hash);
	    }
	    catch (NoSuchAlgorithmException e) {
	    	System.err.println("SHA-512 is not a valid message digest algorithm");
	        System.err.println(e);
	        return false;
	    }
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
