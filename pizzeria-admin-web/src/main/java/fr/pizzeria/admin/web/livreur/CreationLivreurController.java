package fr.pizzeria.admin.web.livreur;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.LivreurService;
import fr.pizzeria.model.Livreur;

@WebServlet("/livreurs/new")
public class CreationLivreurController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(CreationLivreurController.class.getName());

	public static final String URL = "/livreurs/edit";
	private static final String VUE_EDITER_LIVREUR = "/WEB-INF/views/livreurs/editerLivreur.jsp";

	@Inject
	private LivreurService livreurService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("livreur", new Livreur());
		this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);

	}

	private void sendErrorLivreurInconnue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "id livreur inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");

		if (isBlank(nom) || isBlank(prenom)) {
			req.setAttribute("msgErreur", "Tout les champs sont obligatoires");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
		} else {
			Livreur newLivreur = new Livreur(nom, prenom);
			livreurService.saveLivreur(newLivreur);
			resp.sendRedirect(this.getServletContext().getContextPath() + "/livreurs/list");
		}

	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
}
