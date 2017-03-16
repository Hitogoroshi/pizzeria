package fr.pizzeria.admin.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.model.Client;

@WebServlet("/clients/edit")
public class EditerClientController extends HttpServlet {

	public static final String URL = "/clients/edit";
	private static final String VUE_EDITER_CLIENT = "/WEB-INF/views/clients/editerClient.jsp";

	@Inject
	private ClientService clientService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");

		if (email == null || email.isEmpty()) {
			resp.setStatus(400); // Bad Request
			req.setAttribute("msgErreur", "Email obligatoire pour editer un client");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_CLIENT).forward(req, resp);
		} else {

			Client client = this.clientService.findOneClient(email);
			if (client == null) {
				sendErrorClientInconnu(req, resp);
			} else {
				req.setAttribute("client", client);
				this.getServletContext().getRequestDispatcher(VUE_EDITER_CLIENT).forward(req, resp);
			}
		}

	}

	private void sendErrorClientInconnu(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(400); // Bad Request
		req.setAttribute("msgErreur", "Email client inconnu");
		this.getServletContext().getRequestDispatcher(VUE_EDITER_CLIENT).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		//String password = req.getParameter("password");
		String oldEmail = req.getParameter("oldEmail");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");
		String adresse = req.getParameter("adresse");
		String telephone = req.getParameter("telephone");
		boolean abonne = req.getParameter("abonne") == null ? false : true; // null
																			// ou
																			// on
		String password = this.clientService.findOneClient(oldEmail).getPassword();
		if (isBlank(nom) || isBlank(prenom) || isBlank(email) || isBlank(oldEmail) || isBlank(adresse)
				|| isBlank(telephone)) {
			
			req.setAttribute("client", this.clientService.findOneClient(oldEmail));
			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			this.getServletContext().getRequestDispatcher(VUE_EDITER_CLIENT).forward(req, resp);
		} else {
			Client clientAvecId = null;
			try {
				clientAvecId = new Client(Integer.valueOf(id), nom, prenom, email, password, adresse, telephone,
						abonne);
			} catch (NumberFormatException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE,"Exception Handle inside Client Controller",e);
			}

			clientService.updateClient(oldEmail, clientAvecId);
			resp.sendRedirect(this.getServletContext().getContextPath() + "/clients/list");
		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}

}
