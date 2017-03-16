package fr.pizzeria.admin.metier;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Livreur;
import fr.pizzeria.model.StatutCommande;
import fr.pizzeria.model.StatutCommandePaiement;

@RunWith(MockitoJUnitRunner.class)
public class CommandeServiceTest {

	@Mock
	private EntityManager em;

	@Mock
	private TypedQuery<Commande> query;

	private CommandeService service;

	@Before
	public void setUp() {
		service = new CommandeService();
		service.setEm(em);
	}

	@Test
	public void testFindAll() throws GeneralSecurityException {
		Livreur livreur = new Livreur("Doe", "John");

		Client client = new Client("Travis", "Bob", "bt@gmail.com", "", "37 bd...", "0700000000");
	
		Commande commande1 = new Commande("001", StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreur, client);
		Commande commande2 = new Commande("002",StatutCommandePaiement.PAYE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreur, client);


		List<Commande> commandes = new ArrayList<>();
		commandes.add(commande1);
		commandes.add(commande2);

		when(em.createQuery("select c from Commande c where supprime = false", Commande.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(commandes);
		service.findAll();
	}

	@Test
	public void testFindOneCommande() throws GeneralSecurityException {
		Livreur livreur = new Livreur("Doe", "John");

		Client client = new Client("Travis", "Bob", "bt@gmail.com", "", "37 bd...", "0700000000");

		Commande commande = new Commande("001",StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreur, client);


		when(em.createQuery("select c from Commande c where c.numeroCommande = :numeroCommande and supprime = false",
				Commande.class)).thenReturn(query);
		when(query.setParameter("numeroCommande", "001")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(commande);

		service.findOneCommande("001");
	}

	@Test
	public void testUpdateCommande() throws GeneralSecurityException {
		Livreur livreur = new Livreur("Doe", "John");

		Client client = new Client("Travis", "Bob", "bt@gmail.com", "", "37 bd...", "0700000000");

		Commande commandeOld = new Commande("001", StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreur, client);
		Commande commandeNew = new Commande(1, "001", StatutCommandePaiement.PAYE, StatutCommande.EXPEDIE, Calendar.getInstance(), livreur, client);


		when(em.createQuery("select c from Commande c where c.numeroCommande = :numeroCommande and supprime = false",
				Commande.class)).thenReturn(query);
		when(query.setParameter("numeroCommande", "001")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(commandeOld);

		service.updateCommande("001", commandeNew);
		verify(em).merge(commandeNew);
	}

	@Test
	public void testSaveCommande() throws GeneralSecurityException {
		Livreur livreur = new Livreur("Doe", "John");

		Client client = new Client("Travis", "Bob", "bt@gmail.com", "", "37 bd...", "0700000000");

		Commande commande = new Commande("001",StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreur, client);

		service.saveCommande(commande);

		verify(em).persist(commande);
	}

	@Test
	public void testDeleteCommande() throws GeneralSecurityException {
		Livreur livreur = new Livreur("Doe", "John");

		Client client = new Client("Travis", "Bob", "bt@gmail.com", "", "37 bd...", "0700000000");

		Commande commande = new Commande(1, "001",StatutCommandePaiement.NON_PAYEE, StatutCommande.EXPEDIE, Calendar.getInstance(), livreur, client);


		when(em.createQuery("select c from Commande c where c.numeroCommande = :numeroCommande and supprime = false",
				Commande.class)).thenReturn(query);
		when(query.setParameter("numeroCommande", "001")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(commande);

		service.deleteCommande("001");
		assertTrue(commande.isSupprime());
		verify(em).merge(commande);
	}
}
