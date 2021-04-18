package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupos_burbuja")
public class GrupoBurbuja implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String estadoSanitario;
	@Column(nullable = false)
	private String estadoDocencia;
	private Date fechaConfinamiento;
	private Integer prioridad;
	private Date fechaUltimaConmutacion;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_grupoburbuja")
	private List<Alumno> alumnos;
	private static final long serialVersionUID = 1L;

	public GrupoBurbuja() {
		super();
	}

	public GrupoBurbuja(String nombre, String estadoSanitario, String estadoDocencia, Date fechaConfinamiento, Integer prioridad,
			Date fechaUltimaConmutacion, List<Alumno> alumnos) {
		super();
		this.nombre = nombre;
		this.estadoSanitario = estadoSanitario;
		this.estadoDocencia = estadoDocencia;
		this.fechaConfinamiento = fechaConfinamiento;
		this.prioridad = prioridad;
		this.fechaUltimaConmutacion = fechaUltimaConmutacion;
		this.alumnos = alumnos;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEstadoSanitario() {
		return estadoSanitario;
	}

	public void setEstadoSanitario(String estadoSanitario) {
		this.estadoSanitario = estadoSanitario;
	}

	public String getEstadoDocencia() {
		return estadoDocencia;
	}

	public void setEstadoDocencia(String estadoDocencia) {
		this.estadoDocencia = estadoDocencia;
	}

	public Date getFechaConfinamiento() {
		return fechaConfinamiento;
	}

	public void setFechaConfinamiento(Date fechaConfinamiento) {
		this.fechaConfinamiento = fechaConfinamiento;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Date getFechaUltimaConmutacion() {
		return fechaUltimaConmutacion;
	}

	public void setFechaUltimaConmutacion(Date fechaUltimaConmutacion) {
		this.fechaUltimaConmutacion = fechaUltimaConmutacion;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
