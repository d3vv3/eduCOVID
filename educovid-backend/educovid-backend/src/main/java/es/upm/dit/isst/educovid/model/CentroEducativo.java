package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
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
@Table(name = "centros_educativos")
public class CentroEducativo implements Serializable {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_id_centro")
	private List<Clase> clases;
	private static final long serialVersionUID = 1L;

	public CentroEducativo() {
		super();
	}

	public CentroEducativo(String nombre, List<Clase> clases) {
		super();
		this.nombre = nombre;
		this.clases = clases;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Clase> getClases() {
		return clases;
	}

	public void setClases(List<Clase> clases) {
		this.clases = clases;
	}


}
