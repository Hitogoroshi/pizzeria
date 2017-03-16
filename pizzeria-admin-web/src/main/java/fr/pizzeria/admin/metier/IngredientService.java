package fr.pizzeria.admin.metier;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import fr.pizzeria.model.Ingredient;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.PizzaIngredients;

@Stateless
public class IngredientService {

	@PersistenceContext
	protected EntityManager em;

	@Inject
	private PizzaService pizzaService;

	/**
	 * on récupère tout les ingredients qui sont actives (actif = true)
	 * 
	 * @return
	 */
	public List<Ingredient> findAll() {
		return em.createQuery("select i from Ingredient i", Ingredient.class).getResultList();
	}
	public List<Ingredient> findAllByQuantity() {
		return em.createQuery("select i from Ingredient i order by quantite", Ingredient.class).getResultList();
	}

	public Ingredient findOneIngredient(String code) {
		return em.createQuery("select i from Ingredient i where i.code=:code", Ingredient.class).setParameter("code", code).getSingleResult();
	}

	public void updateIngredient(Ingredient ingredientAvecCode) {
		Ingredient ing = findOneIngredient(ingredientAvecCode.getCode()); // vérifie qu'une pizza est présente
		ing.setNom(ingredientAvecCode.getNom());
		ing.setActif(ingredientAvecCode.isActif());
		//Ajout Pour ISSUE USA008
		ing.setQuantite(ingredientAvecCode.getQuantite());
		ing.setSeuil(ingredientAvecCode.getSeuil());
		if (!ing.isActif()) {
			disablePizza(ing);	//on désactive les pizzas qui contiennent cet ingrédient s'il est désactivé
		}
		em.merge(ing);
	}

	public boolean saveIngredient(Ingredient ingredientSansId) {
		try {
			findOneIngredient(ingredientSansId.getCode());
			return false;
		} catch (NoResultException | EJBException e) {
			em.persist(ingredientSansId);
			return true;
		}
	}

	public void deleteIngredient(String code) {
		Ingredient ing = findOneIngredient(code);
		removeFromPizza(ing);
		em.remove(ing);
	}
	
	//ISSUE USA008
	public void saveQteIngredient(PizzaIngredients pI){
		em.merge(pI);
	}

	private void disablePizza(Ingredient ing) {
		List<Pizza> listPizzas = pizzaService.findAll();
		for (Pizza pizza : listPizzas) {
			List<PizzaIngredients> listeIngredientsPizza = pizza.getIngredients();
			for(PizzaIngredients ingPizza : listeIngredientsPizza){
				if(ing.getId() != ingPizza.getId().getIngredient().getId()){
					continue;
				}
				pizza.setActif(false);
			}
			/*
			if (!listeIngredientsPizza.contains(ing)) {
				continue;
			}
			pizza.setActif(false);
			*/
			pizzaService.updatePizza(pizza);
		}
	}

	private void removeFromPizza(Ingredient ing) {
		List<Pizza> listPizzas = pizzaService.findAll();
		for (Pizza pizza : listPizzas) {
			List<PizzaIngredients> listeIngredientsPizza = pizza.getIngredients();
			if (!listeIngredientsPizza.contains(ing)) {
				continue;
			}
			listeIngredientsPizza.remove(ing);
			pizzaService.updatePizza(pizza);
		}

	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void setPizzaService(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}
	
	public void updateStock(Ingredient ing){
		em.merge(ing);
	}
}