package fr.pizzeria.admin.statistique;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.StatistiqueService;

@WebServlet(urlPatterns = {"/statistiques"})
public class StatistiquesController extends HttpServlet {

	private static final String VUE_STATISTIQUES = "/WEB-INF/views/statistiques/statistiques.jsp";

	@Inject
	private StatistiqueService statistiqueService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("PizzasParVentes", statistiqueService.findPizzasParVentes());
		req.setAttribute("ClientsParCommandes", statistiqueService.findClientsParCommandes());
		req.setAttribute("LivreursParCommandes", statistiqueService.findLivreursParCommandes());
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE_STATISTIQUES);
		dispatcher.forward(req, resp);
	}
	
}
