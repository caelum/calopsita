package br.com.caelum.calopsita.infra;

import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vraptor.LogicException;
import org.vraptor.view.ViewException;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class AuthenticationInterceptorTest {


	private Mockery mockery;
	private AuthenticationInterceptor interceptor;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private InterceptorStack stack;
	private HttpSession session;

	@Before
	public void setUp() throws Exception {
		request = mockery.mock(HttpServletRequest.class);
		response = mockery.mock(HttpServletResponse.class);
		stack = mockery.mock(InterceptorStack.class);
		session = mockery.mock(HttpSession.class);
		SessionUser sessionUser = new SessionUser(session);
		interceptor = new AuthenticationInterceptor(sessionUser, request, response);
	}

	@After
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}

	@Test
	public void authorizeIfThereIsAUserInTheSession() throws Exception {
		givenThereIsAUserInTheSession();

		shouldExecuteFlow();

		whenInterceptOccurs();
	}
	@Test
	public void redirectIfThereIsNotAUserInTheSession() throws Exception {
		givenThereIsNotAUserInTheSession();

		shouldNotExecuteFlow();
		shouldRedirectToLoginPage();

		whenInterceptOccurs();
	}
	private void shouldRedirectToLoginPage() throws IOException {
		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(containsString("/")));
				allowing(request);
			}
		});
	}

	private void givenThereIsNotAUserInTheSession() {
		mockery.checking(new Expectations() {
			{
				one(session).getAttribute("currentUser");
				will(returnValue(null));
			}
		});
	}

	private void givenThereIsAUserInTheSession() {
		mockery.checking(new Expectations() {
			{
				one(session).getAttribute("currentUser");
				will(returnValue(new User()));
			}
		});

	}

	private void shouldExecuteFlow() throws ViewException, LogicException {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});
	}
	private void shouldNotExecuteFlow() throws ViewException, LogicException {
		mockery.checking(new Expectations() {
			{
				never(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});

	}

	private void whenInterceptOccurs() throws LogicException, ViewException {
		interceptor.intercept(stack, null, null);
	}
}
