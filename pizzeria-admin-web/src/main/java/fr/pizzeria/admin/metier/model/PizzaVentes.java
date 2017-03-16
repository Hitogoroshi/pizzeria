package fr.pizzeria.admin.metier.model;

import fr.pizzeria.model.Pizza;

public class PizzaVentes {
	
	private Pizza pizza;
	private long qteVentes;
	
	public PizzaVentes(Pizza pizza, long qteVentes) {
		super();
		this.pizza = pizza;
		this.qteVentes = qteVentes;
	}

	public PizzaVentes() {
		super();
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public long getQteVentes() {
		return qteVentes;
	}

	public void setQteVentes(long qteVentes) {
		this.qteVentes = qteVentes;
	}

}
