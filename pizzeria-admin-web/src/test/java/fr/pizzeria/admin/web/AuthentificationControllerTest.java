package fr.pizzeria.admin.web;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pizzeria.admin.metier.UtilisateurService;
import fr.pizzeria.model.Utilisateur;

@RunWith(MockitoJUnitRunner.class)
public class AuthentificationControllerTest {
	
	@Mock
	HttpServletRequest req;
	
	@Mock
	HttpServletResponse resp;
	
	@Mock 
	private EntityManager em;
	
	@Mock
	private TypedQuery<Utilisateur> query;
	
	@Mock
	HttpSession hs;
	
	private UtilisateurService service;
	
	@Before
	public void setUp() {
		service = new UtilisateurService();
		service.setEm(em);	
	}
	
	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		Utilisateur util = new Utilisateur("admin", "admin", "admin@admin.fr", service.encode("admin"));
		
		when(req.getParameter("email")).thenReturn("admin@admin.fr");
		when(req.getParameter("motDePasse")).thenReturn("admin");
		
		when(req.getSession(true)).thenReturn(hs);
		
		when(em.createQuery("select u from Utilisateur u where u.email=:email", Utilisateur.class)).thenReturn(query);
		when(query.setParameter("email", "admin@admin.fr")).thenReturn(query);
		when(query.getSingleResult()).thenReturn(util);
		
		AuthentificationController controllerTest = new AuthentificationController();
		controllerTest.setUserService(service);
		controllerTest.doPost(req, resp);
		verify(hs).setAttribute(AuthentificationController.AUTH_EMAIL, "admin@admin.fr");
	}
}
