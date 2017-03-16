
package fr.pizzeria.admin.metier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pizzeria.model.Livreur;

@RunWith(MockitoJUnitRunner.class)
public class LivreurServiceTest {

	private static final Logger LOG = Logger.getLogger(LivreurServiceTest.class.getName());

	@Mock
	private EntityManager em;

	@Mock
	private TypedQuery<Livreur> query;

	private LivreurService service;

	@Before
	public void setUp() {
		service = new LivreurService();
		service.setEm(em);
	}

	@Test
	public void findOneLivreur() {
		LOG.info("Etant donne un livreur");

		Livreur livreur = new Livreur("Bové", "José");
		String id = "1";
		when(em.createQuery("select p from Livreur p where p.id=:id", Livreur.class)).thenReturn(query);
		when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(livreur);

		Livreur livreur2 = service.findOneLivreur(id);

		assertEquals(livreur, livreur2);

		LOG.info("FIN");
	}

	@Test
	public void saveLivreurTest() {
		LOG.info("Etant donne un objet ingredient");
		Livreur livreur = new Livreur("José", "bové");

		service.saveLivreur(livreur);

		LOG.info("livreur a été persisté");
		verify(em).persist(livreur);
		LOG.info("FIN");
	}

	@Test
	public void updateLivreurTest() {
		LOG.info("Etant donne un livreur");

		Livreur livreur = new Livreur("Bové", "José");
		String id = "1";
		when(em.createQuery("select p from Livreur p where p.id=:id", Livreur.class)).thenReturn(query);
		when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(livreur);

		Livreur livreur2 = service.findOneLivreur(id);

		service.updateLivreur(id, "Bovin", "José", true);
		// Vérification des nouvelle données
		LOG.info("Alors 'livreur' a ete modifie");
		assertEquals(livreur2.getNom(), "Bovin");
		verify(em).merge(livreur2);
		LOG.info("FIN");
	}

	@Test
	public void deleteLivreurTest() {
		LOG.info("Etant donne un objet livreur");
		Livreur livreur = new Livreur("Bové", "José");
		String id = "1";
		when(em.createQuery("select p from Livreur p where p.id=:id", Livreur.class)).thenReturn(query);
		when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(livreur);

		LOG.info("Insertion de l'objet");
		service.saveLivreur(livreur);
		assertTrue(livreur.isActif());

		service.deleteLivreur(id);

		LOG.info("FIN");
	}

	@Test
	public void findLivreurTest() {
		LOG.info("Etant donne un livreur");

		String nom = "Bovin";
		String prenom = "José";
		List<Livreur> livreurs = new ArrayList<>();
		Livreur livreur1 = new Livreur(nom, prenom);
		Livreur livreur2 = new Livreur("toto", prenom);
		livreurs.add(livreur1);
		livreurs.add(livreur2);
		when(em.createQuery("select p from Livreur p where p.nom=:nom and p.prenom=:prenom", Livreur.class)).thenReturn(query);
		when(query.setParameter("nom", nom)).thenReturn(query);
		when(query.setParameter("prenom", prenom)).thenReturn(query);
		when(query.getResultList()).thenReturn(livreurs);

		int resultat = service.findLivreur(nom, prenom).size();
		assertEquals(2, resultat);

		verify(em).createQuery("select p from Livreur p where p.nom=:nom and p.prenom=:prenom", Livreur.class);
		verify(query).setParameter("nom", nom);
		verify(query).setParameter("prenom", prenom);
		LOG.info("FIN");
	}
}
