package fr.pizzeria.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Livreur {

	public static final String CODE_LIVREUR_PAR_DEFAUT = "DEFAUT";
	public static final int NB_CARACTERES_NOM_PRENOM_CODE = 3;
	public static final char CARACTERE_REMPLISSAGE_CODE = '-'; // caractère ajouté si un nom ou prénom est trop court

	public Livreur() {
		this.actif = true;
	}

	public Livreur(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.actif = true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String code;
	private String nom;
	private String prenom;
	private boolean actif = true;

	public String genererCodeBrut() {
		// supprimer les "accents" (en réalité, tous les diacritiques reconnus)
		String nomStr = StringUtils.stripAccents(nom);
		String prenomStr = StringUtils.stripAccents(prenom);

		// supprimer tout ce qui n'est pas une lettre
		nomStr = StringUtils.removePattern(nomStr, "[^A-Za-z]");
		prenomStr = StringUtils.removePattern(prenomStr, "[^A-Za-z]");

		// prendre les NB_CARACTERES_NOM_PRENOM_CODE premiers caractères, ou tous les caractères s'il y en a moins
		nomStr = nomStr.substring(0, (nomStr.length() > NB_CARACTERES_NOM_PRENOM_CODE - 1) ? NB_CARACTERES_NOM_PRENOM_CODE : nomStr.length());
		prenomStr = prenomStr.substring(0, (prenomStr.length() > NB_CARACTERES_NOM_PRENOM_CODE - 1) ? NB_CARACTERES_NOM_PRENOM_CODE : prenomStr.length());

		if ((nomStr + prenomStr).length() == 0) {
			// la chaîne en sortie serait vide / ne contiendrait que le caractère de remplissage : retourner le code par défaut
			return CODE_LIVREUR_PAR_DEFAUT;
		} else {

			// rajouter des caractères CARACTERE_REMPLISSAGE_CODE si la longueur est inférieure à NB_CARACTERES_NOM_PRENOM_CODE caractères
			for (int i = nomStr.length(); i < NB_CARACTERES_NOM_PRENOM_CODE; i++) {
				nomStr += CARACTERE_REMPLISSAGE_CODE;
			}
			for (int i = prenomStr.length(); i < NB_CARACTERES_NOM_PRENOM_CODE; i++) {
				prenomStr += CARACTERE_REMPLISSAGE_CODE;
			}

			// concaténer le nom et prénom, passer le tout en majuscules
			return (nomStr + prenomStr).toUpperCase();

		}

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public void toggleActif() {
		this.setActif(!this.actif);
	}

	@Override
	public String toString() {
		return prenom + " " + nom;
	}
}
