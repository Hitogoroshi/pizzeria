package fr.pizzeria.admin.email;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.EMailService;

@WebServlet(urlPatterns = {"/historique"})
public class HistoriqueEmailsController extends HttpServlet {
	
	private static final String VUE_HISTORIQUE = "/WEB-INF/views/newsletter/historique.jsp";
	
	@Inject
	private EMailService emailService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("listeEmails", emailService.findAll());
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE_HISTORIQUE);
		dispatcher.forward(req, resp);
	}

}
