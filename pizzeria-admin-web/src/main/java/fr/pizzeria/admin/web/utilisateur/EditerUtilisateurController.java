package fr.pizzeria.admin.web.utilisateur;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.model.Utilisateur;

@WebServlet("/utilisateurs/edit")
public class EditerUtilisateurController extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(EditerUtilisateurController.class.getName());

	public static final String URL = "/utilisateurs/edit";
	private static final String VUE_EDITER_UTILISATEUR = "/WEB-INF/views/utilisateurs/editerUtilisateur.jsp";

	@Inject
	private UtilisateurService utilisateurService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");

		if (email == null || email.isEmpty()) {
			resp.setStatus(400); // Bad Request
			req.setAttribute("msgErreur", "Email obligatoire pour editer un utilisateur");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_UTILISATEUR).forward(req, resp);
		} else {

			Utilisateur utilisateur = this.utilisateurService.findOneUtilisateur(email);
			if (utilisateur == null) {
				sendErrorUtilisateurInconnu(req, resp);
			} else {
				req.setAttribute("utilisateur", utilisateur);
				this.getServletContext().getRequestDispatcher(VUE_EDITER_UTILISATEUR).forward(req, resp);
			}
		}

	}

	private void sendErrorUtilisateurInconnu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "Email utilisateur inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_UTILISATEUR).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");
		String motDePasse = req.getParameter("motDePasse");
		String confirmationMotDePasse = req.getParameter("confirmationMotDePasse");

		if (isBlank(email)) {
			req.setAttribute("utilisateur", this.utilisateurService.findOneUtilisateur(email));
			req.setAttribute("msgErreur", "L'email est obligatoire !");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_UTILISATEUR).forward(req, resp);

		} else if (!motDePasse.equals(confirmationMotDePasse)) {
			req.setAttribute("msgErreur", "Les mots de passe sont différents !");
			req.setAttribute("utilisateur", this.utilisateurService.findOneUtilisateur(email));
			this.getServletContext().getRequestDispatcher(VUE_EDITER_UTILISATEUR).forward(req, resp);

		} else {
			String mdpEncode = null;
			if (!isBlank(motDePasse) && !isBlank(confirmationMotDePasse)) {
				mdpEncode = utilisateurService.encode(motDePasse);
			}
			Utilisateur utilisateurAvecId = new Utilisateur(new Integer(id), nom, prenom, email, mdpEncode);

			utilisateurService.updateUtilisateur(email, utilisateurAvecId);
			resp.sendRedirect(this.getServletContext().getContextPath() + "/utilisateurs/list");
		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
}
