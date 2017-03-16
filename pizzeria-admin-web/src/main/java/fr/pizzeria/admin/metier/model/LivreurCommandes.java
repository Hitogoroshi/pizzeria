package fr.pizzeria.admin.metier.model;

import fr.pizzeria.model.Livreur;

public class LivreurCommandes {
	
	private Livreur livreur;
	private long nbCommandes;
	
	public LivreurCommandes(Livreur livreur, long nbCommandes) {
		super();
		this.livreur = livreur;
		this.nbCommandes = nbCommandes;
	}

	public LivreurCommandes() {
		super();
	}

	public Livreur getLivreur() {
		return livreur;
	}

	public void setLivreur(Livreur livreur) {
		this.livreur = livreur;
	}

	public long getNbCommandes() {
		return nbCommandes;
	}

	public void setNbCommandes(long nbCommandes) {
		this.nbCommandes = nbCommandes;
	}

}
