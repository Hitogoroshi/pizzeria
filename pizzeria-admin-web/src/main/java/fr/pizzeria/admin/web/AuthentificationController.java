package fr.pizzeria.admin.web;

import java.io.IOException;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.model.Utilisateur;

@WebServlet("/login")
public class AuthentificationController extends HttpServlet {

	public static final String URL = "/login";

	private static final String VUE_LOGIN = "/WEB-INF/views/auth/login.jsp";

	public static final String AUTH_EMAIL = "auth_email";

	@Inject
	UtilisateurService userService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(VUE_LOGIN).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String motDePasse = req.getParameter("motDePasse");
		String url = req.getParameter("url");
		Utilisateur utilisateur;
		try {
			utilisateur = userService.findOneUtilisateur(email);
			if (utilisateur.getMotDePasse().equals(userService.encode(motDePasse))) {
				req.getSession(true).setAttribute(AUTH_EMAIL, email);
				resp.sendRedirect(req.getContextPath() + ((url != null && !url.isEmpty()) ? url : "/pizzas/list"));
			} else {
				req.setAttribute("msgErreur", "Email ou Mot de passe incorret");
				req.getRequestDispatcher(VUE_LOGIN).forward(req, resp);
			}
		} catch (EJBException e) {
			req.setAttribute("msgErreur", "Email ou Mot de passe incorret");
			req.getRequestDispatcher(VUE_LOGIN).forward(req, resp);
		}
	}

	/**
	 * utiliser pour les test
	 * 
	 * @param userService
	 */
	public void setUserService(UtilisateurService userService) {
		this.userService = userService;
	}

}
