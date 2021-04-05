package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profesores")
public class Profesor extends Usuario implements Serializable {

	private String nifNie;
	private String estadoSanitario;
	private Date fechaConfinamiento;
	@ManyToMany(mappedBy = "profesores")
	private Set<Clase> clases;
	private static final long serialVersionUID = 1L;
	
	public Profesor() {
		super();
	}
	
	public Profesor(String nifNie, String estadoSanitario, Date fechaConfinamiento, Set<Clase> clases) {
		super();
		this.nifNie = nifNie;
		this.estadoSanitario = estadoSanitario;
		this.fechaConfinamiento = fechaConfinamiento;
		this.clases = clases;
	}
	public String getNifNie() {
		return nifNie;
	}
	public void setNifNie(String nifNie) {
		this.nifNie = nifNie;
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
	public Set<Clase> getClases() {
		return clases;
	}
	public void setClases(Set<Clase> clases) {
		this.clases = clases;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}