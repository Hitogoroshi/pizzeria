package fr.pizzeria.admin.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pizzeria.model.Ingredient;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceTest {

	private static final Logger LOG = Logger.getLogger(IngredientServiceTest.class.getName());

	@Mock
	private EntityManager em;

	@Mock
	private PizzaService pizzaService;

	@Mock
	private TypedQuery<Ingredient> query;

	private IngredientService service;

	@Before
	public void setUp() {
		service = new IngredientService();

		service.setPizzaService(pizzaService);
		service.setEm(em);
	}

	@Test
	public void findOneIngredient() {
		LOG.info("Etant donne un objet ingredient");

		Ingredient ingredient = new Ingredient("CHA", "champignon");
		when(em.createQuery("select i from Ingredient i where i.code=:code", Ingredient.class)).thenReturn(query);
		when(query.setParameter("code", "CHA")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(ingredient);

		Ingredient newIngredient = service.findOneIngredient("CHA");

		LOG.info("Alors 'ingredient' a ete modifie");
		assertEquals(ingredient, newIngredient);

		LOG.info("FIN");
	}

	@Test
	public void creerIngredient() {
		LOG.info("Etant donne un objet ingredient");
		Ingredient ingredient = new Ingredient("CHA", "champignon");

		when(em.createQuery("select i from Ingredient i where i.code=:code", Ingredient.class)).thenReturn(query);
		when(query.setParameter("code", "CHA")).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);

		service.saveIngredient(ingredient);

		LOG.info("Alors 'ingredient' a ete persiste");
		verify(em).persist(ingredient);
		LOG.info("FIN");
	}

	@Test
	public void updateIngredient() {
		LOG.info("Etant donne un objet ingredient");

		Ingredient ingredient = new Ingredient("CHA", "champignon");
		when(em.createQuery("select i from Ingredient i where i.code=:code", Ingredient.class)).thenReturn(query);
		when(query.setParameter("code", "CHA")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(ingredient);

		// Vérification des donnée de base
		LOG.info("Insertion de l'objet");
		service.saveIngredient(ingredient);
		assertTrue(ingredient.isActif());
		assertEquals(ingredient.getNom(), "champignon");

		Ingredient ingredientAvecCode = new Ingredient("CHA", "des champignon");
		service.updateIngredient(ingredientAvecCode);

		// Vérification des nouvelle données
		LOG.info("Alors 'ingredient' a ete modifie");
		assertEquals(ingredient.getNom(), "des champignon");
		verify(em).merge(ingredient);

		LOG.info("FIN");
	}

	@Test
	public void supprimerIngredientVerifModifIsActive() {
		LOG.info("Etant donne un objet ingredient");
		Ingredient ingredient = new Ingredient("CHA", "champignon");
		when(em.createQuery("select i from Ingredient i where i.code=:code", Ingredient.class)).thenReturn(query);
		when(query.setParameter("code", "CHA")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(ingredient);

		LOG.info("Insertion de l'objet");
		service.saveIngredient(ingredient);
		assertTrue(ingredient.isActif());

		service.deleteIngredient("CHA");

		LOG.info("FIN");
	}
}