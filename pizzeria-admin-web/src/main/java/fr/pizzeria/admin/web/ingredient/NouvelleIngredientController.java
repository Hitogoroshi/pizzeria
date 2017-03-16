package fr.pizzeria.admin.web.ingredient;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.IngredientService;
import fr.pizzeria.model.Ingredient;

@WebServlet("/ingredients/new")
public class NouvelleIngredientController extends HttpServlet {

	public static final String URL = "/ingredients/new";
	private static final String VUE_NOUVELLE_INGREDIENT = "/WEB-INF/views/ingredient/editerIngredient.jsp";
	@Inject
	private IngredientService ingredientService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("ingredient", new Ingredient());
		req.setAttribute("Referer", req.getHeader("Referer"));
		this.getServletContext().getRequestDispatcher(VUE_NOUVELLE_INGREDIENT).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nom = req.getParameter("nom");
		String code = req.getParameter("code");
		String referer = req.getParameter("Referer");
		// ISSUE USA008
		String quantite = req.getParameter("quantite");
		String seuil = req.getParameter("seuil");


		if (isBlank(nom) || isBlank(code)) {
			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			req.setAttribute("ingredient", new Ingredient());
			this.getServletContext().getRequestDispatcher(VUE_NOUVELLE_INGREDIENT).forward(req, resp);
		} else {
			Ingredient ingredientSansId = new Ingredient(code, nom);
			//ISSUE USA008
			if(quantite != null){
				ingredientSansId.setQuantite(Double.parseDouble(quantite));
			}if(seuil != null){
				ingredientSansId.setSeuil(Double.parseDouble(seuil));
			}
			if (ingredientService.saveIngredient(ingredientSansId)) {
				resp.sendRedirect(referer);
			} else {
				req.setAttribute("msgErreur", "Un autre ingrédient a déjà ce code");
				req.setAttribute("ingredient", new Ingredient());
				this.getServletContext().getRequestDispatcher(VUE_NOUVELLE_INGREDIENT).forward(req, resp);
			}

		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
}
