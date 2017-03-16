package fr.pizzeria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Issue USA008

/*		Modifications effectuées :
 * 
 *  - Ajout de l'attribut quantité( décimal car l'unité est le kilo )
 */
@Entity
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;
	private String nom;
	@Column(name="quantite", columnDefinition="Decimal(10,3) default '0.000'", nullable=false)
	private Double quantite = 0.000;
	private Double seuil = 10.00;
	private boolean actif = true;
	

	public Ingredient() {
		// default construct
	}
	
	public Ingredient(String code, String nom, Double quantite) {
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
	}
	
	
	
	public Ingredient(String code, String nom, Double quantite, Double seuil) {
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
		this.seuil = seuil;
	}
	
	
	public Ingredient(Integer id, String code, String nom, Double quantite, Double seuil) {
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
		this.seuil = seuil;
	}

	public Ingredient(Integer id, String code, String nom, Double quantite) {
		this.id = id;
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
	}

	public Ingredient(Integer id, String code, String nom) {
		this.id = id;
		this.code = code;
		this.nom = nom;
	}

	public Ingredient(String code, String nom) {
		this.code = code;
		this.nom = nom;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public void toggleActif() {
		this.setActif(!this.actif);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public  Double getQuantite() {
		return quantite;
	}

	public void setQuantite( Double quantite) {
		this.quantite = quantite;
	}

	public Double getSeuil() {
		return seuil;
	}

	public void setSeuil(Double seuil) {
		this.seuil = seuil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (actif ? 1231 : 1237);
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((quantite == null) ? 0 : quantite.hashCode());
		result = prime * result + ((seuil == null) ? 0 : seuil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (actif != other.actif)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (quantite == null) {
			if (other.quantite != null)
				return false;
		} else if (!quantite.equals(other.quantite))
			return false;
		if (seuil == null) {
			if (other.seuil != null)
				return false;
		} else if (!seuil.equals(other.seuil))
			return false;
		return true;
	}
	
	
}
