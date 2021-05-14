package es.upm.dit.isst.educovid.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String hash;
	@Column(nullable = false)
	private String salt;
	private String subscriptionEndpoint;
	private String p256dh;
	private String auth;
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String hash, String salt) {
		super();
		this.nombre = nombre;
		this.hash = hash;
		this.salt = salt;
		//System.out.println("User name: '" + this.nombre + "'");
		//System.out.println("User salt: '" + this.salt.toString() + "'");
		//System.out.println("User hash: '" + this.hash.toString() + "'");
	}
	
//	public Usuario(String nombre, String password) {
//		super();
//		this.nombre = nombre;
//		this.setPassword(password);
//	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
//		System.out.println("Setting id: '" + id + "'");
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
//		System.out.println("Setting nombre: '" + nombre + "'");
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
	
	public String getSubscriptionEndpoint() {
		return subscriptionEndpoint;
	}

	public void setSubscriptionEndpoint(String subscriptionEndpoint) {
		this.subscriptionEndpoint = subscriptionEndpoint;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getP256dh() {
		return p256dh;
	}

	public void setP256dh(String p256dh) {
		this.p256dh = p256dh;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	
}
