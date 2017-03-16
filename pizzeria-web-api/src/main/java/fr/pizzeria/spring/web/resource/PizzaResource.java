package fr.pizzeria.spring.web.resource;

import fr.pizzeria.model.Ingredient;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.PizzaIngredients;
import fr.pizzeria.spring.web.filter.PizzaIhm;
import fr.pizzeria.spring.web.repository.IPizzaRepository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ressource Pizza.
 */
@RestController
@RequestMapping("/pizzas")
public class PizzaResource {

  @Autowired private IPizzaRepository pizzaDao;

  @RequestMapping(method = RequestMethod.GET)
  public List<PizzaIhm> listAllPizzas() {
	  List<PizzaIhm> pizzas = new ArrayList<PizzaIhm>();
	  List<Pizza> pizzActif = pizzaDao.findByActif(true);
	  boolean suffisant = true;
	  
	  for(Pizza pizza : pizzActif){
		  for(PizzaIngredients item : pizza.getIngredients()){
			  Ingredient ingr = item.getIngredient();
			  if(ingr.getQuantite() < item.getQuantiteRequise()){
				  suffisant = false;
			  }
		  }
		  pizzas.add(new PizzaIhm(pizza.getId(), pizza.getCode(), pizza.getNom(), pizza.getPrix(), pizza.getCategorie(), pizza.getUrlImage(), pizza.getIngredients(), true, suffisant));
		  suffisant = true;
	  }
	  
      return pizzas;
  }

}
