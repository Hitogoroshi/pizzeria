package fr.pizzeria.admin.client;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;

import fr.pizzeria.admin.metier.ClientService;
import fr.pizzeria.admin.metier.EMailService;
import fr.pizzeria.model.Client;

@WebServlet("/clients/new")
public class NouveauClientController extends HttpServlet {

	public static final String URL = "/clients/new";
	private static final String VUE_NOUVEAU_CLIENT = "/WEB-INF/views/clients/editerClient.jsp";
	@Inject
	private ClientService clientService;
	@Inject
	private EMailService eMailService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("client", new Client());
		this.getServletContext().getRequestDispatcher(VUE_NOUVEAU_CLIENT).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String nom = req.getParameter("nom");
		String prenom = req.getParameter("prenom");
		String adresse = req.getParameter("adresse");
		String telephone = req.getParameter("telephone");
		boolean abonne = req.getParameter("abonne") == null ? false : true; 
		String password = RandomStringUtils.random(10,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_-@#&'(!?)$%?:;/.?,");
		
	
		if (isBlank(nom) || isBlank(prenom) || isBlank(email) || isBlank(adresse) || isBlank(telephone)) {
			req.setAttribute("msgErreur", "Tous les paramètres sont obligatoires !");
			this.getServletContext().getRequestDispatcher(VUE_NOUVEAU_CLIENT).forward(req, resp);
		} else {
			Client clientSansId = null;

			clientSansId = new Client(nom, prenom, email, adresse, telephone, abonne);
			clientSansId.setPasswordEncrypt(password);

			if (!clientService.isEmailTaken(email).isEmpty()) {
				req.setAttribute("msgErreur", "l'email existe deja ");
				req.setAttribute("client", clientSansId);
				this.getServletContext().getRequestDispatcher(VUE_NOUVEAU_CLIENT).forward(req, resp);
			} else {
				eMailService.envoyeEmailPasswordModification(email, prenom, nom, password);
				clientService.saveClient(clientSansId);
				resp.sendRedirect(this.getServletContext().getContextPath() + "/clients/list");
			}
		}
	}

	protected boolean isBlank(String param) {
		return param == null || param.isEmpty();
	}
	
	
}
