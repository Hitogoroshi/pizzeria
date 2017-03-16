package fr.pizzeria.admin.web.utilisateur;

import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.admin.web.AuthentificationController;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Contrôleur de la page Liste des utilisateurs.
 */
@WebServlet("/utilisateurs/list")
public class ListerUtilisateurController extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(ListerUtilisateurController.class.getName());

  private static final String VUE_LISTER_UTILISATEURS = "/WEB-INF/views/utilisateurs/listerUtilisateurs.jsp";
  private static final String ACTION_EDITER = "editer";
  private static final String ACTION_SUPPRIMER = "supprimer";

  @Inject private UtilisateurService utilisateurService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
//	  LOG.log(Level.INFO, "Entrée page liste users");
    req.setAttribute("listeUtilisateurs", this.utilisateurService.findAll());
    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(VUE_LISTER_UTILISATEURS);
    dispatcher.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action"); // editer ou supprimer
    String email = req.getParameter("email"); // identifiant de l'utilisateur

    switch (action) {

      case ACTION_EDITER:
        resp.sendRedirect(this.getServletContext().getContextPath()
            + EditerUtilisateurController.URL
            + "?email="
            + email);
        break;
      case ACTION_SUPPRIMER:
    	String emailAuthentifie = (String) req.getSession().getAttribute(AuthentificationController.AUTH_EMAIL);
    	
    	if (!emailAuthentifie.equals(email)) {
    		utilisateurService.deleteUtilisateur(email);
            req.setAttribute("msg", "L'utilisateur dont l'email est " + email + " a été supprimé");
		} else {
			req.setAttribute("msg", "Suppression impossible : c'est votre email !");
			resp.setStatus(400);
		}
    	
        doGet(req, resp);
        break;
      default:
        req.setAttribute("msg", "Action inconnue");
        resp.setStatus(400);
        doGet(req, resp);
        break;
    }
  }
}
