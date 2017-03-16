package fr.pizzeria.model;

public enum StatutCommandePaiement {
	NON_PAYEE("non payée"), PAYE("payée");
	
	private String libelle;
	
	StatutCommandePaiement(String libelle){
		this.libelle= libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	@Override
	public String toString() {
		return libelle;
	}
	

}
