package fr.pizzeria.admin.web.newsletter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.EMailService;

@WebServlet("/newsletter")
public class NewsletterController extends HttpServlet {

	private static final String VUE_NEWSLETTER = "/WEB-INF/views/newsletter/newsletter.jsp";
	@Inject
	private EMailService eMailService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_NEWSLETTER).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String pizzaPromo = req.getParameter("pizzaPromo");
		
		Logger.getLogger(NewsletterController.class.getName()).log(Level.INFO, pizzaPromo);
		
		try {
			eMailService.envoyeEmailPromotionPizza(pizzaPromo);
			req.setAttribute("msg_success", "Le mail a été envoyé à tout les clients abonnés");
			doGet(req, resp);
		}catch (MessagingException e) {
				Logger.getLogger(EMailService.class.getName()).log(Level.WARNING, "Cannot send mail", e);
				req.setAttribute("msg", "Une erreur est survenue. Message d'erreur :"+e);
				doGet(req, resp);
		}
		
	}

}
