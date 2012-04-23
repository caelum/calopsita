package br.com.caelum.calopsita.infra;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

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
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.DefaultResourceClass;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class AuthenticationInterceptorTest {


	private Mockery mockery;
	private AuthenticationInterceptor interceptor;
	private InterceptorStack stack;
	private SessionUser sessionUser;
	private Result result;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		stack = mockery.mock(InterceptorStack.class);
		sessionUser = new SessionUser(new MockHttpSession());
		result = mockery.mock(Result.class);
		interceptor = new AuthenticationInterceptor(sessionUser, result);
	}

	@After
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}

	@Test
	public void authorizeIfThereIsAUserInTheSession() throws Exception {
		givenThereIsAUserInTheSession();

		shouldExecuteFlow();
		shouldAddLoggedUserIntoRequest();

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

	private ResourceMethod anyResourceMethodOf(Class<?> clazz) {
		return new DefaultResourceMethod(new DefaultResourceClass(clazz), clazz.getDeclaredMethods()[0]);
	}

	private void shouldRedirectToLoginPage() throws IOException {
		mockery.checking(new Expectations() {
			{
				one(result).redirectTo(HomeController.class);
				will(returnValue(new HomeController()));
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

	private void shouldAddLoggedUserIntoRequest() {
		mockery.checking(new Expectations() {
			{
				one(result).include("currentUser", sessionUser.getUser());
			}
		});
	}

	private void whenInterceptOccurs() {
		interceptor.intercept(stack, null, null);
	}
}
