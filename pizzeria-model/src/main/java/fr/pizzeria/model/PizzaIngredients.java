package fr.pizzeria.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pizza_ingredient")
public class PizzaIngredients {
	@EmbeddedId
	@JsonIgnore
	private PizzaIngredientId id;

	private Double quantiteRequise;

	public PizzaIngredients() {
		super();
	}

	public PizzaIngredients(PizzaIngredientId id, Double quantiteRequise) {
		this.id = id;
		this.quantiteRequise = quantiteRequise;
	}

	public PizzaIngredientId getId() {
		return id;
	}

	public void setId(PizzaIngredientId id) {
		this.id = id;
	}

	public Double getQuantiteRequise() {
		return quantiteRequise;
	}

	public void setQuantiteRequise(Double quantiteRequise) {
		this.quantiteRequise = quantiteRequise;
	}
	
	public Ingredient getIngredient(){
		return id.getIngredient();
	}



	
	
		
	
	

	
	
	
	

}
