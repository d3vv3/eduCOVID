package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "centros_educativos")
public class CentroEducativo implements Serializable {
	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	@ManyToOne()
	@JoinColumn(name = "fk_id_responsable_covid")
	private ResponsableCOVID responsable;
	@OneToMany(mappedBy = "centro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Clase> clases;
	private static final long serialVersionUID = 1L;

	public CentroEducativo() {
		super();
	}
	
	public CentroEducativo(Integer id, String nombre, ResponsableCOVID responsable, List<Clase> clases) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.responsable = responsable;
		this.clases = clases;
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

	public ResponsableCOVID getResponsable() {
		return responsable;
	}

	public void setResponsable(ResponsableCOVID responsable) {
		this.responsable = responsable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
