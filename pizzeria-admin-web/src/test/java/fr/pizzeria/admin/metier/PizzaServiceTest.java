package fr.pizzeria.admin.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Pizza;

@RunWith(MockitoJUnitRunner.class)
public class PizzaServiceTest {
	private static final Logger LOG = Logger.getLogger(PizzaServiceTest.class.getName());

	@Mock
	private EntityManager em;

	@Mock
	private TypedQuery<Pizza> query;

	private PizzaService service;

	@Before
	public void setUp() {
		service = new PizzaService();
		service.setEm(em);
	}

	@Test
	public void testFindAll() {
		Pizza p1 = new Pizza("test", "p1", new BigDecimal(11), CategoriePizza.VIANDE);
		Pizza p2 = new Pizza("test2", "p2", new BigDecimal(12), CategoriePizza.VIANDE);
		service.savePizza(p1);
		service.savePizza(p2);
		// assertEquals(expected, actual);

	}

	@Test
	public void testFindOnePizza() {

		Pizza p = new Pizza("test", "p1", new BigDecimal(11), CategoriePizza.VIANDE);
		when(em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)).thenReturn(query);
		when(query.setParameter("code", "test")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(p);

		Pizza p2 = service.findOnePizza("test");

		assertEquals(p.getCode(), p2.getCode());
		assertEquals(p.getCategorie(), p2.getCategorie());
		assertEquals(p.getPrix(), p2.getPrix());
		assertEquals(p.getNom(), p.getNom());

	}

	@Test
	public void testUpdatePizza() {
		LOG.info("Etant donne un objet pizza");
		Pizza p = new Pizza("test", "p1", new BigDecimal(11), CategoriePizza.VIANDE);
		
		when(em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)).thenReturn(query);
		when(query.setParameter("code", "test")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(p);
		
		service.updatePizza(p);
		verify(em).merge(p);
		
		LOG.info("FIN");
	}

	@Test
	public void testSavePizza() {
		LOG.info("Etant donne un objet ingredient");
		Pizza p = new Pizza("test", "p1", new BigDecimal(11), CategoriePizza.VIANDE);

		service.savePizza(p);

		LOG.info("Alors 'pizza' a ete persiste");
		verify(em).persist(p);
		LOG.info("FIN");

	}

	@Test
	public void testDeletePizza() {
		LOG.info("Etant donne un objet pizza");
		Pizza pizza = new Pizza("test", "p1", new BigDecimal(11), CategoriePizza.VIANDE);
		when(em.createQuery("select p from Pizza p where p.code=:code", Pizza.class)).thenReturn(query);
		when(query.setParameter("code", "test")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(pizza);
		LOG.info("Insertion de l'objet");
		service.savePizza(pizza);
		assertTrue(pizza.isActif());

		service.deletePizza("test");

		LOG.info("FIN");
	}

}
