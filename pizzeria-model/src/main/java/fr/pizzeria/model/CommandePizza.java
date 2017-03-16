package fr.pizzeria.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "commande_pizza")
@IdClass(CommandePizzaId.class)
public class CommandePizza {
	@Id
	private Integer commandeId;
	@Id
	private Integer pizzaId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "commande_id", referencedColumnName = "id")
	@JsonIgnore
	private Commande commande;
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "pizza_id", referencedColumnName = "id")
	private Pizza pizza;

	private int quantite;

	public CommandePizza(Commande commande, Pizza pizza, int quantite) {
		this.commande = commande;
		this.pizza = pizza;
		this.quantite = quantite;
	}

	public CommandePizza() {
		// TODO Auto-generated constructor stub
	}

	public int getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(Integer commandeId) {
		this.commandeId = commandeId;
	}

	public int getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(Integer pizzaId) {
		this.pizzaId = pizzaId;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commande == null) ? 0 : commande.hashCode());
		result = prime * result + ((commandeId == null) ? 0 : commandeId.hashCode());
		result = prime * result + ((pizza == null) ? 0 : pizza.hashCode());
		result = prime * result + ((pizzaId == null) ? 0 : pizzaId.hashCode());
		result = prime * result + quantite;
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
		CommandePizza other = (CommandePizza) obj;
		if (quantite != other.quantite)
			return false;
		return true;
	}

}
