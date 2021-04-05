package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clases")
public class Clase implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	private Integer tiempoConmutacion; // In school days
	@ManyToOne()
	@JoinColumn(name = "fk_id_centro")
	private CentroEducativo centro;
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
	@JoinTable(
			name = "clases_profesores",
			joinColumns = {@JoinColumn(name = "fk_id_clase")},
			inverseJoinColumns = {@JoinColumn(name = "fk_id_profesor")}
	)
	private Set<Profesor> profesores; // Set because List uses too many queries: 
									// https://www.adictosaltrabajo.com/2020/04/02/hibernate-onetoone-onetomany-manytoone-y-manytomany/
	@OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GrupoBurbuja> gruposBurbuja;
	private static final long serialVersionUID = 1L;
	
	public Clase() {
		super();
	}
	
	public Clase(Integer id, String nombre, Integer tiempoConmutacion, CentroEducativo centro, Set<Profesor> profesores,
			List<GrupoBurbuja> gruposBurbuja) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tiempoConmutacion = tiempoConmutacion;
		this.centro = centro;
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
	public CentroEducativo getCentro() {
		return centro;
	}
	public void setCentro(CentroEducativo centro) {
		this.centro = centro;
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
}