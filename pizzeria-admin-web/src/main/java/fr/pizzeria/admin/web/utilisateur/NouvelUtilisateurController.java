package fr.pizzeria.admin.web.utilisateur;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.model.Utilisateur;

@WebServlet("/utilisateurs/new")
public class NouvelUtilisateurController extends HttpServlet {

  public static final String URL = "/utilisateurs/new";
  private static final String VUE_NOUVEL_UTILISATEUR = "/WEB-INF/views/utilisateurs/editerUtilisateur.jsp";
  @Inject
  private UtilisateurService utilisateurService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setAttribute("utilisateur", new Utilisateur());
    this.getServletContext().getRequestDispatcher(VUE_NOUVEL_UTILISATEUR).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String nom = req.getParameter("nom");
    String prenom = req.getParameter("prenom");
    String email = req.getParameter("email");
    String motDePasse = req.getParameter("motDePasse");
    String confirmationMotDePasse = req.getParameter("confirmationMotDePasse");

    if (isBlank(email) || isBlank(motDePasse) || isBlank(confirmationMotDePasse)) {
	    req.setAttribute("msgErreur", "L'email et le mot de passe sont obligatoires !");
	    req.setAttribute("utilisateur", new Utilisateur(nom, prenom, email, motDePasse));
	    this.getServletContext().getRequestDispatcher(VUE_NOUVEL_UTILISATEUR).forward(req, resp);
	    
	} else if (!(motDePasse.equals(confirmationMotDePasse))) {
	    req.setAttribute("msgErreur", "Les mots de passe sont différents !");
	    req.setAttribute("utilisateur", new Utilisateur(nom, prenom, email, ""));
	    this.getServletContext().getRequestDispatcher(VUE_NOUVEL_UTILISATEUR).forward(req, resp);
	    
	} else if (!utilisateurService.isEmailTaken(email).isEmpty()) {
	    req.setAttribute("msgErreur", "L'email "+ email + " existe déjà dans la base de données !");
	    req.setAttribute("utilisateur", new Utilisateur(nom, prenom, email, motDePasse));
	    this.getServletContext().getRequestDispatcher(VUE_NOUVEL_UTILISATEUR).forward(req, resp);
	    
	} else {
		
		String mdpEncode = encode(motDePasse);
		
		Utilisateur utilisateurSansId = new Utilisateur(nom, prenom, email, mdpEncode);
		utilisateurService.saveUtilisateur(utilisateurSansId);;
		resp.sendRedirect(this.getServletContext().getContextPath() + "/utilisateurs/list");
	} 
  }

  protected boolean isBlank(String param) {
    return param == null || param.isEmpty();
  }
  
  protected static String encode(String password)
  {
      byte[] uniqueKey = password.getBytes();
      byte[] hash      = null;

      try
      {
          hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
      }
      catch (NoSuchAlgorithmException e)
      {
          throw new Error("No MD5 support in this VM.");
      }

      StringBuilder hashString = new StringBuilder();
      for (int i = 0; i < hash.length; i++)
      {
          String hex = Integer.toHexString(hash[i]);
          if (hex.length() == 1)
          {
              hashString.append('0');
              hashString.append(hex.charAt(hex.length() - 1));
          }
          else
              hashString.append(hex.substring(hex.length() - 2));
      }
      return hashString.toString();
  }
  
  
}
