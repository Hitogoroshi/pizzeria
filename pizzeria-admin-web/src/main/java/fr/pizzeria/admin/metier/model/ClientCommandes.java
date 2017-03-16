package fr.pizzeria.admin.metier.model;

import java.math.BigDecimal;

import fr.pizzeria.model.Client;

public class ClientCommandes implements Comparable<ClientCommandes> {
	
	private Client client;
	private BigDecimal valMoyenne;
	
	public ClientCommandes(Client client, BigDecimal valMoyenne) {
		super();
		this.client = client;
		this.valMoyenne = valMoyenne;
	}

	public ClientCommandes() {
		super();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BigDecimal getValMoyenne() {
		return valMoyenne;
	}

	public void setValMoyenne(BigDecimal valMoyenne) {
		this.valMoyenne = valMoyenne;
	}

	@Override
	public int compareTo(ClientCommandes o) {
		return this.valMoyenne.compareTo(o.valMoyenne);
	}

}
