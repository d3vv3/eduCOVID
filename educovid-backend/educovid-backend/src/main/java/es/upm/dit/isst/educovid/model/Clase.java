package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "clases", uniqueConstraints = @UniqueConstraint(columnNames = { "fk_id_centro", "nombre" }))
public class Clase implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "grupopresencial_clase", joinColumns = {
			@JoinColumn(name = "clase_id", referencedColumnName = "id", unique = true) }, inverseJoinColumns = {
					@JoinColumn(name = "grupo_id", referencedColumnName = "id", unique = true) })
	private GrupoBurbuja burbujaPresencial;
	private Date fechaInicioConmutacion;
	private Integer tiempoConmutacion; // In school days
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, orphanRemoval = true })
	@JoinTable(name = "clases_profesores", joinColumns = { @JoinColumn(name = "fk_id_clase") }, inverseJoinColumns = {
			@JoinColumn(name = "fk_id_profesor") })
	private Set<Profesor> profesores; // Set because List uses too many queries:
										// https://www.adictosaltrabajo.com/2020/04/02/hibernate-onetoone-onetomany-manytoone-y-manytomany/
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_clase")
	private List<GrupoBurbuja> gruposBurbuja;
	private static final long serialVersionUID = 1L;

	public Clase() {
		super();
	}

	public Clase(String nombre, GrupoBurbuja burbujaPresencial, Date fechaInicioConmutacion, Integer tiempoConmutacion,
			Set<Profesor> profesores, List<GrupoBurbuja> gruposBurbuja) {
		super();
		this.nombre = nombre;
		this.burbujaPresencial = burbujaPresencial;
		this.fechaInicioConmutacion = fechaInicioConmutacion;
		this.tiempoConmutacion = tiempoConmutacion;
		this.profesores = profesores;
		this.gruposBurbuja = gruposBurbuja;
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

	public Integer getTiempoConmutacion() {
		return tiempoConmutacion;
	}

	public void setTiempoConmutacion(Integer tiempoConmutacion) {
		this.tiempoConmutacion = tiempoConmutacion;
	}

	public Set<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(Set<Profesor> profesores) {
		this.profesores = profesores;
	}

	public List<GrupoBurbuja> getGruposBurbuja() {
		return gruposBurbuja;
	}

	public void setGruposBurbuja(List<GrupoBurbuja> gruposBurbuja) {
		this.gruposBurbuja = gruposBurbuja;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public GrupoBurbuja getBurbujaPresencial() {
		return burbujaPresencial;
	}

	public void setBurbujaPresencial(GrupoBurbuja burbujaPresencial) {
		this.burbujaPresencial = burbujaPresencial;
	}

	public Date getFechaInicioConmutacion() {
		return fechaInicioConmutacion;
	}

	public void setFechaInicioConmutacion(Date fechaInicioConmutacion) {
		this.fechaInicioConmutacion = fechaInicioConmutacion;
	}
}
