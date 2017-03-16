package fr.pizzeria.model;

public enum StatutCommande {
	NON_TRAITE("Non traitée"), EXPEDIE("Expédiée"), LIVRE("Livrée");
	
	private String libelle;
	
	StatutCommande(String libelle) {
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}

	@Override
	public String toString() {
		return libelle;
	}
}
