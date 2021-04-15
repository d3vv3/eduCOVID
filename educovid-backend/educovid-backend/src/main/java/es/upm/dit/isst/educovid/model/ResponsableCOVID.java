package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "responsables_COVID")
public class ResponsableCOVID extends Usuario implements Serializable {
	
	@Column(unique = true, nullable = false)
	private String nifNie;
	@Column(nullable = false)
	private Boolean privacidadAceptada;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_id_responsable")
	private List<CentroEducativo> centros;
	private static final long serialVersionUID = 1L;

	public ResponsableCOVID() {
		super();
	}
	
	public ResponsableCOVID(String nombre, String hash, String salt, String nifNie, Boolean privacidadAceptada, List<CentroEducativo> centros) {
		super(nombre, hash, salt);
		this.nifNie = nifNie;
		this.privacidadAceptada = privacidadAceptada;
		this.centros = centros;
	}

	public Boolean getPrivacidadAceptada() {
		return privacidadAceptada;
	}

	public void setPrivacidadAceptada(Boolean privacidadAceptada) {
		this.privacidadAceptada = privacidadAceptada;
	}


	public String getNifNie() {
		return nifNie;
	}

	public void setNifNie(String nifNie) {
		this.nifNie = nifNie;
	}

	public List<CentroEducativo> getCentros() {
		return centros;
	}

	public void setCentros(List<CentroEducativo> centros) {
		this.centros = centros;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
