package br.com.caelum.calopsita.infra;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.mocks.MockHttpSession;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.DefaultResourceClass;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.util.test.MockResult;

public class AuthenticationInterceptorTest {


	private Mockery mockery;
	private AuthenticationInterceptor interceptor;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private InterceptorStack stack;
	private SessionUser sessionUser;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		request = mockery.mock(HttpServletRequest.class);
		response = mockery.mock(HttpServletResponse.class);
		stack = mockery.mock(InterceptorStack.class);
		sessionUser = new SessionUser(new MockHttpSession());
		interceptor = new AuthenticationInterceptor(sessionUser, request, response, new MockResult());
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
	@Test
	public void shouldBypassUsersAndHomeController() throws Exception {
		assertFalse(interceptor.accepts(anyResourceMethodOf(HomeController.class)));
		assertFalse(interceptor.accepts(anyResourceMethodOf(UsersController.class)));
	}

	@Test(expected=InterceptionException.class)
	public void shouldRethrowExceptions() throws Exception {
		givenThereIsNotAUserInTheSession();
		givenResponseThrowsException();

		whenInterceptOccurs();
	}
	private void givenResponseThrowsException() throws IOException {

		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(any(String.class)));
				will(throwException(new IOException()));

				ignoring(anything());
			}
		});
	}

	private ResourceMethod anyResourceMethodOf(Class<?> clazz) {
		return new DefaultResourceMethod(new DefaultResourceClass(clazz), clazz.getDeclaredMethods()[0]);
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
		sessionUser.setUser(null);
	}

	private void givenThereIsAUserInTheSession() {
		sessionUser.setUser(new User());

	}

	private void shouldExecuteFlow() {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});
	}
	private void shouldNotExecuteFlow() {
		mockery.checking(new Expectations() {
			{
				never(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});

	}

	private void whenInterceptOccurs() {
		interceptor.intercept(stack, null, null);
	}
}
