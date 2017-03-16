package fr.pizzeria.model;

import java.util.List;

public class ClientCommande {
	
	private Integer idClient;
	private List<PizzaCommande> pizzascom;
	
	public Integer getIdClient() {
		return idClient;
	}
	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}
	public List<PizzaCommande> getPizzascom() {
		return pizzascom;
	}
	public void setPizzascom(List<PizzaCommande> pizzascom) {
		this.pizzascom = pizzascom;
	}
	
	
}
