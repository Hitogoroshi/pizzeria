package fr.pizzeria.admin.listener;

import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.pizzeria.admin.batch.BatchClientDel;
import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.admin.metier.CommandeService;
import fr.pizzeria.admin.metier.EMailService;
import fr.pizzeria.admin.metier.IngredientService;
import fr.pizzeria.admin.metier.LivreurService;
import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Ingredient;
import fr.pizzeria.model.Livreur;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.PizzaIngredientId;
import fr.pizzeria.model.PizzaIngredients;
import fr.pizzeria.model.StatutCommande;
import fr.pizzeria.model.StatutCommandePaiement;
import fr.pizzeria.model.Utilisateur;

@WebListener
public class ApplicationListener implements ServletContextListener {

	@Inject
	PizzaService pizzaService;

	@Inject
	EMailService eMailService;

	@Inject
	ClientService clientService;

	@Inject
	UtilisateurService utilisateurService;

	@Inject
	LivreurService livreurService;

	@Inject
	IngredientService ingredientService;

	@Inject
	CommandeService commandeService;

	@Inject
	BatchClientDel bcd;

	private Map<String, Ingredient> ingredients = new HashMap<>();
	private List<PizzaIngredientId> pIng = new ArrayList<>();
	private List<Livreur> livreurs = new ArrayList<>();
	private List<Client> clients = new ArrayList<>();
	private List<Pizza> pizzas = new ArrayList<>();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		boolean dev = Boolean.parseBoolean(bundle.getString("prof.dev"));
		if (dev) {
			initIngredients();
			initPizzas();
			try {
				initClients();
			} catch (GeneralSecurityException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE,"Exception Handle inside Application Listener",e);
			}
			initLivreurs();
			initUtilisateurs();
			initCommandes();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	private void initIngredients() {
		ingredients.put("CHA", new Ingredient("CHA", "Champignon",20.00));
		ingredients.put("MOZ", new Ingredient("MOZ", "Mozzarella",20.00));
		ingredients.put("TOM", new Ingredient("TOM", "Tomate",20.00));
		ingredients.put("BAS", new Ingredient("BAS", "Basilic",20.00));
		ingredients.put("HUI", new Ingredient("HUI", "Huile d'olive",20.00));
		ingredients.put("JAM", new Ingredient("JAM", "Jambon",20.00));
		ingredients.put("CHE", new Ingredient("CHE", "Cheddar",20.00));
		ingredients.put("BLE", new Ingredient("BLE", "Bleu",20.00));
		ingredients.put("COM", new Ingredient("COM", "Comté",20.00));
		ingredients.put("BAR", new Ingredient("BAR", "Sauce barbecue",20.00));
		ingredients.put("BOE", new Ingredient("BOE", "Boeuf",20.00));
		ingredients.put("MER", new Ingredient("MER", "Merguez",20.00));
		ingredients.put("POU", new Ingredient("POU", "Poulet",20.00));
		ingredients.put("SAU", new Ingredient("SAU", "Saumon",20.00));

		ingredients.forEach((k, v) -> {
			ingredientService.saveIngredient(v);
		});
	}

	private void initPizzas() {
		Pizza p1 = new Pizza("MAR", "Margherita", new BigDecimal(12.50), CategoriePizza.SANS_VIANDE);
		p1.setUrlImage("/static/images/margarita.jpg");
		pizzas.add(p1);

		Pizza p2 = new Pizza("REI", "Reine", new BigDecimal(14.50), CategoriePizza.VIANDE);
		p2.setUrlImage("/static/images/reine.jpg");
		pizzas.add(p2);

		Pizza p3 = new Pizza("PEC", "Pêcheur", new BigDecimal(12.00), CategoriePizza.POISSON);
		p3.setUrlImage("/static/images/saumon.jpg");
		pizzas.add(p3);

		

		pizzas.forEach(p -> {
			pizzaService.savePizza(p);
		});
		
		PizzaIngredientId pi1 = new PizzaIngredientId(p1, ingredients.get("CHA"));
		PizzaIngredientId pi2= new PizzaIngredientId(p1, ingredients.get("MOZ"));
		PizzaIngredientId pi3= new PizzaIngredientId(p1, ingredients.get("TOM"));
		PizzaIngredientId pi4= new PizzaIngredientId(p1, ingredients.get("HUI"));
		
		PizzaIngredientId pi5= new PizzaIngredientId(p2, ingredients.get("CHA"));
		PizzaIngredientId pi6= new PizzaIngredientId(p2, ingredients.get("MOZ"));
		PizzaIngredientId pi7= new PizzaIngredientId(p2, ingredients.get("TOM"));
		PizzaIngredientId pi8= new PizzaIngredientId(p2, ingredients.get("HUI"));
		
		PizzaIngredientId pi9= new PizzaIngredientId(p3, ingredients.get("CHA"));
		PizzaIngredientId pi10= new PizzaIngredientId(p3, ingredients.get("MOZ"));
		PizzaIngredientId pi11= new PizzaIngredientId(p3, ingredients.get("TOM"));
		PizzaIngredientId pi12= new PizzaIngredientId(p3, ingredients.get("HUI"));
		
		
		p1.addIngredient(new PizzaIngredients(pi1,0.125));
		p1.addIngredient(new PizzaIngredients(pi2,0.225));
		p1.addIngredient(new PizzaIngredients(pi3,0.105));
		p1.addIngredient(new PizzaIngredients(pi4,0.130));
		
		p2.addIngredient(new PizzaIngredients(pi5,0.125));
		p2.addIngredient(new PizzaIngredients(pi6,0.225));
		p2.addIngredient(new PizzaIngredients(pi7,0.105));
		p2.addIngredient(new PizzaIngredients(pi8,0.130));
		
		p3.addIngredient(new PizzaIngredients(pi9,0.125));
		p3.addIngredient(new PizzaIngredients(pi10,0.225));
		p3.addIngredient(new PizzaIngredients(pi11,0.105));
		p3.addIngredient(new PizzaIngredients(pi12,0.130));

		pizzas.forEach(p -> {
			p.getIngredients().forEach(i->{
				ingredientService.saveQteIngredient(i);
			});
			pizzaService.updatePizza(p);
		});
		
	}

	private void initClients() throws GeneralSecurityException {
		Client clientUno = new Client("LeStalker", "Bob", "test1@googlemail.com", "5 rue lamer", "0612134565", true);
		clientUno.setPasswordEncrypt("kikou");
		clients.add(clientUno);
		Client clientDos = new Client("Rodriguez", "Robert", "test2@gmail.com", "18 rue pueblo", "0712134565", false);
		clientDos.setPasswordEncrypt("kikou");
		clients.add(clientDos);
		Client clientTres = new Client("HoldTheDoor", "Hodor", "test3@gmail.com", "15 bd des anglais", "0612145565",
				true);
		clientTres.setPasswordEncrypt("kikou");
		clients.add(clientTres);

		clients.forEach(c -> {
			clientService.saveClient(c);
		});
	}

	private void initUtilisateurs() {
		List<Utilisateur> utilisateurs = new ArrayList<>();

		utilisateurs.add(new Utilisateur("De Monmirail", "Basil", "basildm@gmail.com", utilisateurService.encode("admin")));
		utilisateurs.add(new Utilisateur("Montjoie", "Octave", "octavem@gmail.com", utilisateurService.encode("admin")));
		utilisateurs.add(new Utilisateur("admin", "admin", "admin@gmail.com", utilisateurService.encode("admin")));

		utilisateurs.forEach(u -> {
			utilisateurService.saveUtilisateur(u);
		});
	}

	private void initLivreurs() {
		livreurs.add(new Livreur("Hollande", "François"));
		livreurs.add(new Livreur("Cameron", "David"));
		livreurs.add(new Livreur("Targaryen", "Daenerys"));

		livreurs.forEach(l -> {
			livreurService.saveLivreur(l);
		});
	}

	private void initCommandes() {
		List<Commande> commandes = new ArrayList<>();

	

		Commande c1 = new Commande("CMD1",StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreurs.get(0), clients.get(0));
		Commande c2 = new Commande("CMD2",StatutCommandePaiement.PAYE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreurs.get(1), clients.get(1));
		Commande c3 = new Commande("CMD3",StatutCommandePaiement.NON_PAYEE, StatutCommande.NON_TRAITE, Calendar.getInstance(), livreurs.get(2), clients.get(2));

		commandes.add(c1);
		commandes.add(c2);
		commandes.add(c3);

		commandes.forEach(c -> {
			commandeService.saveCommande(c);
		});

		c1.addPizza(pizzas.get(0), 2);
		c1.addPizza(pizzas.get(1), 3);
		c1.addPizza(pizzas.get(2), 0);
		c2.addPizza(pizzas.get(0), 0);
		c2.addPizza(pizzas.get(1), 5);
		c2.addPizza(pizzas.get(2), 1);
		
		c3.addPizza(pizzas.get(0), 2);
		c3.addPizza(pizzas.get(1), 0);
		c3.addPizza(pizzas.get(2), 2);
		

		commandes.forEach(c -> {
			commandeService.updateCommande(c.getNumeroCommande(), c);
		});
	}
}
