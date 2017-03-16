package fr.pizzeria.admin.web.commande;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.admin.metier.CommandeService;
import fr.pizzeria.admin.metier.LivreurService;
import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.model.Client;
import fr.pizzeria.model.Commande;
import fr.pizzeria.model.Livreur;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.StatutCommande;
import fr.pizzeria.model.StatutCommandePaiement;

@WebServlet("/commandes/edit")
public class EditerCommandeController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(EditerCommandeController.class.getName());

	public static final String URL = "/commandes/edit";
	private static final String VUE_EDITER_COMMANDE = "/WEB-INF/views/commandes/editerCommande.jsp";

	@Inject
	private CommandeService commandeService;

	@Inject
	private PizzaService pizzaService;

	@Inject
	private ClientService clientService;

	@Inject
	private LivreurService livreurService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String code = req.getParameter("code");

		if (code == null || code.isEmpty()) {
			resp.setStatus(400); // Bad Request
			req.setAttribute("msgErreur", "Code obligatoire pour editer une commande");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_COMMANDE).forward(req, resp);

		} else {
			Commande commande = this.commandeService.findOneCommande(code);

			if (commande == null) {
				sendErrorCommandeInconnue(req, resp);
			} else {
				List<Livreur> livreursDisponibles = livreurService.findAll();
				List<Client> clients = clientService.findAll();
				List<Pizza> allPizzas = pizzaService.findAll();
				StatutCommande[] statuts = StatutCommande.values();
				StatutCommandePaiement[] statutsPaiement = StatutCommandePaiement.values();
				Map<Pizza, Integer> pizzaMap = new HashMap<>();
				allPizzas.forEach(p -> pizzaMap.put(p, 0));
				commande.getPizzas()
						.forEach(commandePizza -> pizzaMap.put(commandePizza.getPizza(), commandePizza.getQuantite()));

				req.setAttribute("commande", commande);
				req.setAttribute("statuts", statuts);
				// ajout du statut des paiements
				req.setAttribute("statutsPaiement", statutsPaiement);
				req.setAttribute("livreurs", livreursDisponibles);
				req.setAttribute("clients", clients);
				req.setAttribute("pizzas", allPizzas);
				req.setAttribute("pizzaMap", pizzaMap);

				this.getServletContext().getRequestDispatcher(VUE_EDITER_COMMANDE).forward(req, resp);
			}
		}
	}

	private void sendErrorCommandeInconnue(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "Numéro de commande inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_COMMANDE).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String numeroParam = req.getParameter("numero");
		String idParam = req.getParameter("id");
		String statutParam = req.getParameter("statut");
		// ajout du statut des paiements
		String statutPaiementParam = req.getParameter("statutPaiement");
		String dateParam = req.getParameter("date");
		String livreurIdParam = req.getParameter("livreur");
		String clientIdParam = req.getParameter("client");

		if (isBlank(numeroParam) || isBlank(statutPaiementParam) || isBlank(statutParam) || isBlank(dateParam)
				|| isBlank(livreurIdParam) || isBlank(clientIdParam) || isBlank(idParam)) {

			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_COMMANDE).forward(req, resp);

		} else {
			// Traitement des paramètres
			StatutCommande statut = StatutCommande.valueOf(statutParam);
			StatutCommandePaiement statutPaiement = StatutCommandePaiement.valueOf(statutPaiementParam);

			Calendar date = Calendar.getInstance();
			dateParam = dateParam.replace('T', ' ');
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				date.setTime(sdf.parse(dateParam));
			} catch (ParseException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "Exception Handle inside EditerCommandeController", e);
			}

			int livreurId = Integer.parseInt(livreurIdParam);
			Livreur l = new Livreur();
			l.setId(livreurId);

			int clientId = Integer.parseInt(clientIdParam);
			Client c = new Client();
			c.setId(clientId);

			int id = Integer.parseInt(idParam);

			// Ajout des pizzas
			Commande commandeId = new Commande(id, numeroParam, statutPaiement, statut, date, l, c);
			List<Pizza> allPizzas = pizzaService.findAll();
			List<Boolean> qteSupZero = new ArrayList<>();
			allPizzas.forEach(p -> {
				int qte = Integer.parseInt(req.getParameter(p.getCode()));
				if (qte > 0) {
					commandeId.addPizza(p, qte);
				}
				qteSupZero.add(quantitePizzaCommandee(req, p) > 0);
			});
			if (thereIsPizzaCommandee(qteSupZero)) {
				// Enregistrement de la commande
				commandeService.updateCommande(commandeId.getNumeroCommande(), commandeId);

				// Redirection
				resp.sendRedirect(req.getContextPath() + "/commandes/list");

			} else {
				String code = req.getParameter("code");
				Commande commande = this.commandeService.findOneCommande(code);

				List<Livreur> livreursDisponibles = livreurService.findAll();
				List<Pizza> pizzas = pizzaService.findAll();
				List<Client> clients = clientService.findAll();
				StatutCommande[] statuts = StatutCommande.values();
				StatutCommandePaiement[] statutsPaiement = StatutCommandePaiement.values();
				Map<Pizza, Integer> pizzaMap = new HashMap<>();
				allPizzas.forEach(p -> pizzaMap.put(p, 0));
				commande.getPizzas()
						.forEach(commandePizza -> pizzaMap.put(commandePizza.getPizza(), commandePizza.getQuantite()));
				req.setAttribute("msgErreur", "Il faut au moins une pizza de commander pour éditer une commande");
				commandeId.setDateCommande(Calendar.getInstance());
				req.setAttribute("commande", commandeId);
				req.setAttribute("statuts", statuts);
				req.setAttribute("statutsPaiement", statutsPaiement);
				req.setAttribute("livreurs", livreursDisponibles);
				req.setAttribute("clients", clients);
				req.setAttribute("pizzas", pizzas);
				req.setAttribute("pizzaMap", pizzaMap);
				this.getServletContext().getRequestDispatcher(VUE_EDITER_COMMANDE).forward(req, resp);
			}

		}
	}

	private boolean thereIsPizzaCommandee(List<Boolean> qteSupZero) {
		// renvoie vrai si au moins une valeur est à vrai
		return qteSupZero.stream().filter(q -> q).findAny().isPresent();
	}

	private int quantitePizzaCommandee(HttpServletRequest req, Pizza p) {
		String qte = req.getParameter(p.getCode());
		if (qte.isEmpty()) {
			return 0;
		}
		return Integer.parseInt(qte);
	}

	/**
	 * setter utiliser lors des tests du controller
	 * 
	 * @param commandeService
	 */
	public void setCommandeService(CommandeService commandeService) {
		this.commandeService = commandeService;
	}

	/**
	 * setter utiliser lors des tests du controller
	 * 
	 * @param clientService
	 */
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	/**
	 * setter utiliser lors des tests du controller
	 * 
	 * @param pizzaService
	 */
	public void setPizzaService(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	/**
	 * setter utiliser lors des tests du controller
	 * 
	 * @param livreurService
	 */
	public void setLivreurService(LivreurService livreurService) {
		this.livreurService = livreurService;
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
}
