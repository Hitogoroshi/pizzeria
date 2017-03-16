package fr.pizzeria.admin.metier;

import fr.pizzeria.model.Pizza;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PizzaService {

	@PersistenceContext
	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * on recupere toutes les pizzas actives (actif = true)
	 * 
	 * @return
	 */
	public List<Pizza> findAll() {
		return em.createQuery("select p from Pizza p", Pizza.class).getResultList();
	}

	public List<Pizza> findAllWithIngredient() {
		List<Pizza> pizzas = em.createQuery("select p from Pizza p", Pizza.class).getResultList();
		for (Pizza pizza : pizzas) {
			if (pizza.getIngredients().iterator().hasNext()) {
				pizza.getIngredients().iterator().next();
			}
		}
		return pizzas;
	}

	public Pizza findOnePizza(String code) {
		return em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)
				.setParameter("code", code).getSingleResult();
	}

	public Pizza findOnePizzaWithIngredients(String code) {
		Pizza pizza = em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)
				.setParameter("code", code).getSingleResult();
		// simulation de recupération des ingrédients (requetes)
		if (pizza.getIngredients().iterator().hasNext()) {
			pizza.getIngredients().iterator().next();
		}
		return pizza;
	}

	public List<Pizza> isCodeTaken(String code) {
		return em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)
				.setParameter("code", code).getResultList();
	}

	public void updatePizza(Pizza pizza) {
		Pizza p = findOnePizza(pizza.getCode()); // vérifie qu'une pizza est présente
		p = pizza;
		em.merge(p);
	}

	public void savePizza(Pizza pizza) {
		em.persist(pizza);
	}

	public void deletePizza(String code) {
		Pizza p = findOnePizza(code); // vérifie qu'une pizza est présente
		em.remove(p);
	}
}
