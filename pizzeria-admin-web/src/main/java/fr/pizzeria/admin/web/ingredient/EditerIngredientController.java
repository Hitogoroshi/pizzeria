package fr.pizzeria.admin.web.ingredient;

import fr.pizzeria.admin.metier.IngredientService;
import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Ingredient;
import fr.pizzeria.model.Pizza;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@WebServlet("/ingredients/edit")
public class EditerIngredientController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(EditerIngredientController.class.getName());

	public static final String URL = "/ingredients/edit";
	private static final String VUE_EDITER_INGREDIENT = "/WEB-INF/views/ingredient/editerIngredient.jsp";

	@Inject
	private IngredientService ingredientService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");

		if (code == null || code.isEmpty()) {
			resp.setStatus(400); // Bad Request
			req.setAttribute("msgErreur", "Code obligatoire pour editer une pizza");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_INGREDIENT).forward(req, resp);
		} else {

			Ingredient ingredient = this.ingredientService.findOneIngredient(code);
			if (ingredient == null) {
				sendErrorIngredientInconnue(req, resp);
			} else {
				req.setAttribute("ingredient", ingredient);
				this.getServletContext().getRequestDispatcher(VUE_EDITER_INGREDIENT).forward(req, resp);
			}
		}

	}

	private void sendErrorIngredientInconnue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "Code ingredient inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_INGREDIENT).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String code = req.getParameter("code");
		String nom = req.getParameter("nom");
		// ISSUE USA008
		String quantite = req.getParameter("quantite");
		String seuil = req.getParameter("seuil");

		if (isBlank(nom) || isBlank(code)) {
			req.setAttribute("ingredient", this.ingredientService.findOneIngredient(code));
			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_INGREDIENT).forward(req, resp);
		} else {
			Ingredient ingredientAvecCode = new Ingredient(Integer.valueOf(id), code, nom);
			if(quantite != null){
				ingredientAvecCode.setQuantite(Double.parseDouble(quantite));
			}if(seuil != null){
				ingredientAvecCode.setSeuil(Double.parseDouble(seuil));
			}
			ingredientService.updateIngredient(ingredientAvecCode);
			resp.sendRedirect(req.getContextPath() + "/ingredients/list");
		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}

}
