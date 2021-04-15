package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "alumnos")
public class Alumno extends Usuario implements Serializable {
	@Column(nullable = false)
	private String numeroMatricula;
	@Column(nullable = false)
	private String estadoSanitario;
	private Date fechaConfinamiento;
	private static final long serialVersionUID = 1L;
	
	public Alumno() {
		super();
	}
	
	public Alumno(String nombre, String hash, String salt, String numeroMatricula, String estadoSanitario, Date fechaConfinamiento) {
		super(nombre, hash, salt);
		this.numeroMatricula = numeroMatricula;
		this.estadoSanitario = estadoSanitario;
		this.fechaConfinamiento = fechaConfinamiento;
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
	public Date getFechaConfinamiento() {
		return fechaConfinamiento;
	}
	public void setFechaConfinamiento(Date fechaConfinamiento) {
		this.fechaConfinamiento = fechaConfinamiento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
