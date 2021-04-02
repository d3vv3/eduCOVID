package es.upm.dit.isst.educovid.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "responsables_COVID")
public class ResponsableCOVID extends Usuario implements Serializable {
	
	private String nifNie;
	@OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CentroEducativo> centros;
	private static final long serialVersionUID = 1L;

	public ResponsableCOVID() {
		super();
	}
	
	public ResponsableCOVID(String nifNie, List<CentroEducativo> centros) {
		super();
		this.nifNie = nifNie;
		this.centros = centros;
	}

	public String getNif_nie() {
		return nifNie;
	}

	public void setNif_nie(String nif_nie) {
		this.nifNie = nif_nie;
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
