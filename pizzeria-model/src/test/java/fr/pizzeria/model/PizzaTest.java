package fr.pizzeria.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PizzaTest {

	@Test
	public void creerPizza() {
		Pizza p = new Pizza("TES", "test", BigDecimal.valueOf(10), CategoriePizza.POISSON);
		assertEquals("test", p.getNom());
	}

	@Test
	public void copyPizza() {
		List<Ingredient> listeIngredients = new ArrayList<>();
		listeIngredients.add(new Ingredient("test", "nameTest"));
		listeIngredients.add(new Ingredient("test1", "nameTest1"));
		listeIngredients.add(new Ingredient("test2", "nameTest2"));
		listeIngredients.add(new Ingredient("test3", "nameTest3"));
		Pizza p = new Pizza("TES", "test", BigDecimal.valueOf(10), CategoriePizza.POISSON);
		Pizza p1 = p.copy();
		assertEquals(p, p1);
		assertEquals(p != p1, true);
	}
}
