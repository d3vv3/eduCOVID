package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "alumnos", uniqueConstraints = @UniqueConstraint(columnNames = { "fk_id_grupo", "numeroMatricula" }))
public class Alumno extends Usuario implements Serializable {
	@Column(nullable = false)
	private String numeroMatricula;
	@Column(nullable = false)
	private String estadoSanitario;
	private static final long serialVersionUID = 1L;
	
	public Alumno() {
		super();
	}
	
	public Alumno(String nombre, String hash, String salt, String numeroMatricula, String estadoSanitario) {
		super(nombre, hash, salt);
		this.numeroMatricula = numeroMatricula;
		this.estadoSanitario = estadoSanitario;
	}
	
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	public String getEstadoSanitario() {
		return estadoSanitario;
	}
	public void setEstadoSanitario(String estadoSanitario) {
		this.estadoSanitario = estadoSanitario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
