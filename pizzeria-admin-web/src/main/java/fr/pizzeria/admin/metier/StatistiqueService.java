package fr.pizzeria.admin.metier;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.pizzeria.admin.metier.model.ClientCommandes;
import fr.pizzeria.admin.metier.model.LivreurCommandes;
import fr.pizzeria.admin.metier.model.PizzaVentes;
import fr.pizzeria.model.Client;

@Stateless
public class StatistiqueService {

	@PersistenceContext
	protected EntityManager em;

	public void setEm(EntityManager em2) {
		this.em = em2;
	}

	// pizzas triées par nombre de ventes
	public List<PizzaVentes> findPizzasParVentes() {
		return em.createQuery("SELECT new fr.pizzeria.admin.metier.model.PizzaVentes(p, SUM(c.quantite)) from Pizza p, CommandePizza c, Commande cd where c.pizzaId = p.id AND c.commandeId = cd.id AND cd.supprime = false GROUP BY c.pizzaId ORDER BY SUM(c.quantite) DESC", fr.pizzeria.admin.metier.model.PizzaVentes.class).getResultList();
	}
	
	// clients triés par valeur moyenne de commande
	public List<ClientCommandes> findClientsParCommandes() {
		// on récupère chaque commande client avec son total
		List<ClientCommandes> listeCommandes =  em.createQuery("SELECT new fr.pizzeria.admin.metier.model.ClientCommandes(c, SUM(cp.quantite * p.prix)) FROM Client c, Commande cd, CommandePizza cp, Pizza p WHERE c.id = cd.client.id AND cd.id = cp.commandeId AND cp.pizza.id = p.id AND cd.supprime = false GROUP BY c.id, cd.id ORDER BY c.id", fr.pizzeria.admin.metier.model.ClientCommandes.class).getResultList();
		List<ClientCommandes> valeursMoyennes = new ArrayList<>();
		Client client = null;
		BigDecimal moy = BigDecimal.ZERO;
		int compteur = 0;
		// calcul de la valeur moyenne par client
		for(int i=0; i<listeCommandes.size(); i++) {
			if(i!=0) {
				// client suivant
				if(listeCommandes.get(i).getClient().getId() != listeCommandes.get(i-1).getClient().getId()) {
					moy = moy.divide(new BigDecimal(compteur),2, java.math.RoundingMode.CEILING);
					valeursMoyennes.add(new ClientCommandes(client, moy));
					client = listeCommandes.get(i).getClient();
					moy = listeCommandes.get(i).getValMoyenne();
					compteur = 1;
				}
				// même client
				else {
					moy = moy.add(listeCommandes.get(i).getValMoyenne());
					compteur++;
				}
			}
			// index 0
			else {
				client = listeCommandes.get(i).getClient();
				moy = listeCommandes.get(i).getValMoyenne();
				compteur = 1;
			}
		}
		// on ajoute le dernier client
		moy = moy.divide(new BigDecimal(compteur));
		valeursMoyennes.add(new ClientCommandes(client, moy));
		Collections.sort(valeursMoyennes, Collections.reverseOrder());
		return valeursMoyennes;
	}
	
	// livreurs triés par nombre de livraison le dernier mois
	public List<LivreurCommandes> findLivreursParCommandes() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// on ne prends que les commandes livrées faites ce mois-ci (depuis le premier du mois)
		date.setDate(1);
		System.out.println(dateFormat.format(date));
		return em.createQuery("SELECT new fr.pizzeria.admin.metier.model.LivreurCommandes(l, COUNT(c.id)) FROM Livreur l, Commande c WHERE l.id = c.livreur.id AND c.supprime = false AND c.statut = 'LIVRE' AND c.dateCommande >= '" + dateFormat.format(date) + "' GROUP BY l.id ORDER BY COUNT(c.id) DESC", fr.pizzeria.admin.metier.model.LivreurCommandes.class).getResultList();
	}

}
