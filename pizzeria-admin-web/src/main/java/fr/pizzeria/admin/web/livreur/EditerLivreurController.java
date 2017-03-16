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

@WebServlet("/livreurs/edit")
public class EditerLivreurController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(EditerLivreurController.class.getName());

	public static final String URL = "/livreurs/edit";
	private static final String VUE_EDITER_LIVREUR = "/WEB-INF/views/livreurs/editerLivreur.jsp";

	@Inject
	private LivreurService livreurService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String id = req.getParameter("id");

		if (id == null || id.isEmpty()) {
			resp.setStatus(400); // Bad Request
			this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
		} else {

			Livreur livreur = this.livreurService.findOneLivreur(id);
			if (livreur == null) {
				sendErrorLivreurInconnue(req, resp);
			} else {
				req.setAttribute("livreur", livreur);
				this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
			}
		}

	}

	private void sendErrorLivreurInconnue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "id livreur inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");

		if (isBlank(nom) || isBlank(prenom)) {
			req.setAttribute("livreur", this.livreurService.findOneLivreur(id));
			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
		} else if (livreurService.findLivreur(nom, prenom).size() > 0) {
			req.setAttribute("msgErreur", "Ce livreur est déjà présent en base");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_LIVREUR).forward(req, resp);
		} else {
			Livreur livreurAvecId = new Livreur(nom, prenom);
			livreurService.updateLivreur(id, nom, prenom, livreurAvecId.isActif());
			resp.sendRedirect(this.getServletContext().getContextPath() + "/livreurs/list");
		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
}
