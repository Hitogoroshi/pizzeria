package fr.pizzeria.admin.filter;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pizzeria.admin.web.AuthentificationController;

@WebFilter("/*")
public class AuthentificationFilter implements Filter {

	private static final String[] NO_FILTER_URL = { "/api", "/login", "/static" };

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) resp;
		String emailAuthentifie = (String) httpRequest.getSession().getAttribute(AuthentificationController.AUTH_EMAIL);

		boolean noFilter = Stream.of(NO_FILTER_URL).anyMatch(httpRequest.getRequestURI()::contains);

		if (!noFilter && emailAuthentifie == null) {
			httpResponse.sendRedirect(httpRequest.getServletContext().getContextPath() + AuthentificationController.URL + "?url=" + httpRequest.getServletPath());
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}
