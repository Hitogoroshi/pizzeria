package fr.pizzeria.admin.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutControleur extends HttpServlet {
	
	private static final long serialVersionUID = -2715090172680674915L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession(true).invalidate();
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}
