package br.com.caelum.calopsita.infra;

import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class AuthorizationInterceptorTest {


	private Mockery mockery;
	private ProjectRepository repository;
	private User user;
	private AuthorizationInterceptor interceptor;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private InterceptorStack stack;
	private MethodInfo info;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		repository = mockery.mock(ProjectRepository.class);
		user = new User();
		final HttpSession session = mockery.mock(HttpSession.class);
		SessionUser sessionUser = new SessionUser(session);

		request = mockery.mock(HttpServletRequest.class);
		response = mockery.mock(HttpServletResponse.class);
		stack = mockery.mock(InterceptorStack.class);
		info = mockery.mock(MethodInfo.class);
		interceptor = new AuthorizationInterceptor(sessionUser, mockery.mock(UserRepository.class), repository, request, response, info);
		mockery.checking(new Expectations() {
			{
				allowing(session).getAttribute("currentUser");
				will(returnValue(user));

				allowing(info).getParameters();
				will(returnValue(new Object[0]));
			}
		});
	}


	@Test
	public void authorizeIfThereIsNoInconsistentParametersOnTheRequest() throws Exception {
		givenThereIsNoInconsistencyOnRequest();

		shouldExecuteFlow();

		whenInterceptOccurs();
		mockery.assertIsSatisfied();
	}

	@Test
	public void redirectIfThereIsSomeInconsistency() throws Exception {
		givenThereIsSomeInconsistency();

		shouldNotExecuteFlow();
		shouldRedirectToNotAllowedPage();

		whenInterceptOccurs();
		mockery.assertIsSatisfied();
	}

	private void givenThereIsSomeInconsistency() {
		mockery.checking(new Expectations() {
			{
				one(repository).hasInconsistentValues(with(any(Object[].class)), with(any(User.class)));
				will(returnValue(true));
			}
		});
	}


	private void givenThereIsNoInconsistencyOnRequest() {
		mockery.checking(new Expectations() {
			{
				one(repository).hasInconsistentValues(with(any(Object[].class)), with(any(User.class)));
				will(returnValue(false));
			}
		});
	}

	private void shouldRedirectToNotAllowedPage() throws IOException {

		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(containsString("/home/notAllowed/")));
				allowing(request);
			}
		});
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

	private void whenInterceptOccurs()  {
		interceptor.intercept(stack, null, null);
	}

}
