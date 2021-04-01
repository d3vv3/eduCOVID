package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumnos")
public class Alumno extends Usuario implements Serializable {
	private String numeroMatricula;
	private String estadoSanitario;
	private Date fechaConfinamiento; 
	@ManyToOne()
	@JoinColumn(name = "fk_id_grupo_burbuja")
	private GrupoBurbuja grupoBurbuja;
	private static final long serialVersionUID = 1L;
	
	public Alumno() {
		super();
	}
	
	public Alumno(String numeroMatricula, String estadoSanitario, Date fechaConfinamiento, GrupoBurbuja grupoBurbuja) {
		super();
		this.numeroMatricula = numeroMatricula;
		this.estadoSanitario = estadoSanitario;
		this.fechaConfinamiento = fechaConfinamiento;
		this.grupoBurbuja = grupoBurbuja;
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
	public GrupoBurbuja getGrupoBurbuja() {
		return grupoBurbuja;
	}
	public void setGrupoBurbuja(GrupoBurbuja grupoBurbuja) {
		this.grupoBurbuja = grupoBurbuja;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
