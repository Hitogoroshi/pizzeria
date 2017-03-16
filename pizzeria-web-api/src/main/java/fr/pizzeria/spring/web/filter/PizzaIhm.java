package fr.pizzeria.spring.web.filter;

import java.math.BigDecimal;
import java.util.List;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.PizzaIngredients;
/**
 * Pizza en rupture de stock ingrédient ou pas
 */
public class PizzaIhm extends Pizza {

	// champ calculé
	private boolean disponible;

	public PizzaIhm(Integer id, String code, String nom, BigDecimal prix, CategoriePizza categorie, String urlImage,List<PizzaIngredients> ingredients, Boolean actif, boolean disponible) {
		super(id, code, nom, prix, categorie, urlImage, ingredients, actif);
		this.disponible = disponible;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
}
