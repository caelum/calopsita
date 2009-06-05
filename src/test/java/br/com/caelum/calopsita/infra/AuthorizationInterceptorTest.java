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

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;
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
		interceptor = new AuthorizationInterceptor(sessionUser, repository, request, response, mockery.mock(MethodInfo.class));
		mockery.checking(new Expectations() {
			{
				allowing(session).getAttribute("currentUser");
				will(returnValue(user));
			}
		});
	}

	@After
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}

	@Test
	public void authorizeIfThereIsNoProjectOnTheRequest() throws Exception {
		givenThereIsNoProjectOnRequest();

		shouldExecuteFlow();

		whenInterceptOccurs();
	}
	@Test
	public void authorizeIfThereIsNoProjectWithGivenId() throws Exception {
		givenThereIsAProjectOnRequest();
		givenThatThisProjectDoesntExist();

		shouldExecuteFlow();

		whenInterceptOccurs();
	}
	@Test
	public void authorizeIfUserIsTheOwnerOfTheProject() throws Exception {
		givenThereIsAProjectOnRequest();
		Project project = givenThatThisProjectExist();
		givenThatUserOwnsTheProject(project);

		shouldExecuteFlow();

		whenInterceptOccurs();
	}
	@Test
	public void authorizeIfUserIsAColaboratorOfTheProject() throws Exception {
		givenThereIsAProjectOnRequest();
		Project project = givenThatThisProjectExist();
		givenThatUserIsAColaboratorOfTheProject(project);

		shouldExecuteFlow();

		whenInterceptOccurs();
		mockery.assertIsSatisfied();
	}
	@Test
	public void redirectIfUserIsNotTheOwnerOfTheProject() throws Exception {
		givenThereIsAProjectOnRequest();
		Project project = givenThatThisProjectExist();
		givenThatUserIsNotTheOwner(project);

		shouldNotExecuteFlow();
		shouldRedirectToNotAllowedPage();

		whenInterceptOccurs();
	}

	private void givenThatUserIsAColaboratorOfTheProject(Project project) {
		project.getColaborators().add(user);
	}


	private void shouldRedirectToNotAllowedPage() throws IOException {

		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(containsString("/home/notAllowed/")));
				allowing(request);
			}
		});
	}

	private void givenThatUserIsNotTheOwner(Project project) {
		User owner = new User();
		owner.setLogin("TheOwner");
		project.setOwner(owner);
	}

	private void givenThatUserOwnsTheProject(Project project) {
		project.setOwner(user);
	}

	private Project givenThatThisProjectExist() {

		final Project project = new Project();
		mockery.checking(new Expectations() {
			{
				one(repository).get(3l);
				will(returnValue(project));
			}
		});
		return project;
	}

	private void givenThatThisProjectDoesntExist() {
		mockery.checking(new Expectations() {
			{
				one(repository).get(3l);
				will(returnValue(null));
			}
		});
	}

	private void givenThereIsAProjectOnRequest() {
		mockery.checking(new Expectations() {
			{
				one(request).getParameter("project.id");
				will(returnValue("3"));
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

	private void givenThereIsNoProjectOnRequest() {
		mockery.checking(new Expectations() {
			{
				one(request).getParameter("project.id");
				will(returnValue(null));
			}
		});
	}
}
