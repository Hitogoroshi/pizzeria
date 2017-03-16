package fr.pizzeria.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String expediteur;
	private String destinataire;
	private Date date;
	private String objet;
	private String pizza;
	private String message;
	
	public Email(String expediteur, String destinataire, Date date, String objet, String pizza, String message) {
		super();
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.date = date;
		this.objet = objet;
		this.pizza = pizza;
		this.message = message;
	}
	public Email(String expediteur, String destinataire, Date date, String objet,  String message) {
		super();
		this.expediteur = expediteur;
		this.destinataire = destinataire;
		this.date = date;
		this.objet = objet;
		this.message = message;
	}

	public Email() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(String destinataire) {
		this.destinataire = destinataire;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getPizza() {
		return pizza;
	}

	public void setPizza(String pizza) {
		this.pizza = pizza;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
