package fr.pizzeria.admin.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
@WebFilter("/*")
public class PerformanceFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		long before = System.currentTimeMillis();
		chain.doFilter(req, resp);
		long after = System.currentTimeMillis();
		Map<String, List<Long>> urlPerfs = (Map<String, List<Long>>) httpRequest.getServletContext().getAttribute("PERF_MAP");
		if(urlPerfs == null) {
			urlPerfs = new HashMap<>();
		}
		String chemin = httpRequest.getRequestURI();
		
		List<Long> tempsExec = urlPerfs.get(chemin);
		if(tempsExec == null) {
			tempsExec = new ArrayList<>();
			urlPerfs.put(chemin, tempsExec);
		}
		urlPerfs.get(chemin).add(after - before);
		
		httpRequest.getServletContext().setAttribute("PERF_MAP", urlPerfs);	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	
}
