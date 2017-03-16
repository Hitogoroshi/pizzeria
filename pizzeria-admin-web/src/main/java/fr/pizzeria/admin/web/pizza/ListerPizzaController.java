package fr.pizzeria.admin.web.pizza;

import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.model.Pizza;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Contrôleur de la page Liste des pizzas.
 */
@WebServlet(urlPatterns = { "/pizzas/list", "/pizzas/list/active", "/pizzas/list/inactive" })
public class ListerPizzaController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(ListerPizzaController.class.getName());

	private static final String VUE_LISTER_PIZZAS = "/WEB-INF/views/pizzas/listerPizzas.jsp";
	private static final String PATH_ACTIF = "/pizzas/list/active";
	private static final String PATH_INACTIF = "/pizzas/list/inactive";
	private static final String PATH_ALL = "/pizzas/list";
	private static final String ACTION_EDITER = "editer";
	private static final String ACTION_SUPPRIMER = "supprimer";
	private static final String ACTION_TOGGLE = "toggle";
	private static final String ACTIVE_ATTIBUTE = "active";

	@Inject
	private PizzaService pizzaService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("listePizzas", this.pizzaService.findAllWithIngredient());
		String path = req.getServletPath();
		String active;
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE_LISTER_PIZZAS);
		switch (path) {
			case PATH_ACTIF:
				active = "Actives";
				req.setAttribute(ACTIVE_ATTIBUTE, active);
				break;
			case PATH_INACTIF:
				active = "Inactives";
				req.setAttribute(ACTIVE_ATTIBUTE, active);
				break;
			case PATH_ALL:
				active = "Toutes";
				req.setAttribute(ACTIVE_ATTIBUTE, active);
				break;
			default:
				active = "Actives";
				req.setAttribute(ACTIVE_ATTIBUTE, active);
		}
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action"); // editer ou supprimer
		String code = req.getParameter("code"); // identifiant de la pizza

		switch (action) {
			case ACTION_EDITER:
				resp.sendRedirect(this.getServletContext().getContextPath() + EditerPizzaController.URL + "?code=" + code);
				break;
			case ACTION_SUPPRIMER:
				pizzaService.deletePizza(code);
				req.setAttribute("msg", "La pizza code = " + code + " a bien été supprimée");
				doGet(req, resp);
				break;
			case ACTION_TOGGLE:
				Pizza pizza = pizzaService.findOnePizzaWithIngredients(code);
				pizza.toggleActif();
				pizzaService.updatePizza(pizza);
				String reponseString = pizza.isActif() ? "réactivée" : "désactivée";
				req.setAttribute("msg_success", "La pizza code = " + code + " a bien été " + reponseString);
				doGet(req, resp);
				break;
			default:
				req.setAttribute("msg", "Action inconnue");
				resp.setStatus(400);
				doGet(req, resp);
				break;
		}
	}
}
