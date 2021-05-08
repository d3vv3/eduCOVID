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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "gruposburbuja", uniqueConstraints = @UniqueConstraint(columnNames = { "fk_id_clase", "nombre" }))
public class GrupoBurbuja implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String estadoSanitario;
	private Date fechaConfinamiento;
	private Integer prioridad;
	private Date fechaUltimaConmutacion;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_grupo")
	private List<Alumno> alumnos; // Set because List uses too many queries:
								  // https://www.adictosaltrabajo.com/2020/04/02/hibernate-onetoone-onetomany-manytoone-y-manytomany/
	private static final long serialVersionUID = 1L;

	public GrupoBurbuja() {
		super();
	}

	public GrupoBurbuja(String nombre, String estadoSanitario, Date fechaConfinamiento, Integer prioridad,
			Date fechaUltimaConmutacion, List<Alumno> alumnos) {
		super();
		this.nombre = nombre;
		this.estadoSanitario = estadoSanitario;
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
