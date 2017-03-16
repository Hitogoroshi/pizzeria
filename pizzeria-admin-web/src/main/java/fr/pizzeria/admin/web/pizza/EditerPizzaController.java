package fr.pizzeria.admin.web.pizza;

import fr.pizzeria.admin.metier.IngredientService;
import fr.pizzeria.admin.metier.PizzaService;
import fr.pizzeria.model.CategoriePizza;
import fr.pizzeria.model.Ingredient;
import fr.pizzeria.model.Pizza;
import fr.pizzeria.model.PizzaIngredientId;
import fr.pizzeria.model.PizzaIngredients;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@WebServlet("/pizzas/edit")
public class EditerPizzaController extends HttpServlet {

    private static final Logger LOG = Logger
            .getLogger(EditerPizzaController.class.getName());

    public static final String URL = "/pizzas/edit";
    private static final String VUE_EDITER_PIZZA = "/WEB-INF/views/pizzas/editerPizza.jsp";

    @Inject
    private PizzaService pizzaService;
    
    @Inject
	private IngredientService ingredientService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");

        if (code == null || code.isEmpty()) {
            resp.setStatus(400); // Bad Request
            req.setAttribute("msgErreur",
                    "Code obligatoire pour editer une pizza");
            this.getServletContext().getRequestDispatcher(VUE_EDITER_PIZZA)
                    .forward(req, resp);
        } else {

            Pizza pizza = this.pizzaService.findOnePizzaWithIngredients(code);
            if (pizza == null) {
                sendErrorPizzaInconnue(req, resp);
            } else {
                req.setAttribute("pizza", pizza);
                req.setAttribute("listeIngredient", ingredientService.findAll());
                this.getServletContext()
                        .getRequestDispatcher(VUE_EDITER_PIZZA)
                        .forward(req, resp);
            }
        }

    }

    private void sendErrorPizzaInconnue(HttpServletRequest req,
                                        HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(400); // Bad Request
        req.setAttribute("msgErreur", "Code pizza inconnu");
        this.getServletContext().getRequestDispatcher(VUE_EDITER_PIZZA)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String actif = req.getParameter("actif");
        String code = req.getParameter("code");
        String nom = req.getParameter("nom");
        String urlImage = req.getParameter("urlImage");
        String prix = req.getParameter("prix");
        String[] ingredients = req.getParameterValues("ingredient");
        String categorie = req.getParameter("categorie");
        String[] qteIngredient = req.getParameterValues("qteIngredient");

        if (isBlank(nom) || isBlank(urlImage) || isBlank(prix)) {
            req.setAttribute("pizza", this.pizzaService.findOnePizza(code));
            req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
            this.getServletContext().getRequestDispatcher(VUE_EDITER_PIZZA)
                    .forward(req, resp);
        } else {
            Pizza pizzaAvecId = new Pizza(Integer.valueOf(id), code, nom, new BigDecimal(prix), CategoriePizza.valueOf(categorie), urlImage, Boolean.valueOf(actif));
            
            if (ingredients != null) {
				//ma Pizza sans id en a desormais un
				Pizza maPizza = pizzaAvecId;
				maPizza.resetIngredient();
				for (int i = 0; i< ingredients.length;i++){
					Ingredient monIngredient = ingredientService.findOneIngredient(ingredients[i]);
					PizzaIngredients pizzaI = new PizzaIngredients();
					pizzaI.setId(new PizzaIngredientId(maPizza,monIngredient) );
					pizzaI.setQuantiteRequise(Double.parseDouble(qteIngredient[i]));
					ingredientService.saveQteIngredient(pizzaI);
					maPizza.addIngredient(pizzaI);
					
				}
				pizzaService.updatePizza(maPizza);

			}
            resp.sendRedirect(req.getContextPath()
                    + "/pizzas/list");
        }
    }

    /**
	 * setter utiliser lors des tests du controller
	 * @param pizzaService
	 */
	public void setPizzaService(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	/**
	 * setter utiliser lors des tests du controller
	 * @param pizzaService
	 */
	public void setIngredientService(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}
    
    protected boolean isBlank(String param) {
        return param == null || param.isEmpty();
    }

}
