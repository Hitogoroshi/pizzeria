package fr.pizzeria.admin.web;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogoutControleurTest {
	public static final String AUTH_EMAIL = "auth_email";

	@Mock
	HttpServletRequest req;

	@Mock
	HttpServletResponse resp;
	
	@Mock
	HttpSession hs;

	@Test
	public void test() {
		when(req.getSession(true)).thenReturn(hs);
		
		req.getSession(true).setAttribute(AUTH_EMAIL, "test");
		LogoutControleur lct = new LogoutControleur();
		try {
			lct.doGet(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			fail("an error has been raised");
		} catch (IOException e) {
			e.printStackTrace();
			fail("an error has been raised");
		}
		verify(hs).invalidate();
		doThrow(IllegalStateException.class).when(hs).invalidate();
		try {
			req.getSession(true).invalidate();
			fail("session is not invalidate");
		} catch (IllegalStateException e) {
			assert (true);
		}
	}

}
