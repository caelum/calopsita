package br.com.caelum.calopsita.infra;

import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.http.VRaptorServletRequest;
import org.vraptor.http.VRaptorServletResponse;
import org.vraptor.view.ViewException;

import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class AuthorizationInterceptorTest {

	
	private Mockery mockery;
	private ProjectRepository repository;
	private User user;
	private AuthorizationInterceptor interceptor;
	private LogicFlow flow;
	private VRaptorServletRequest request;
	private VRaptorServletResponse response;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		repository = mockery.mock(ProjectRepository.class);
		user = new User();
		flow = mockery.mock(LogicFlow.class);
		
		interceptor = new AuthorizationInterceptor(user, repository);
		request = mockery.mock(VRaptorServletRequest.class);
		response = mockery.mock(VRaptorServletResponse.class);
		mockery.checking(new Expectations() {
			{
				LogicRequest logicRequest = mockery.mock(LogicRequest.class);
				allowing(flow).getLogicRequest();
				will(returnValue(logicRequest));

				allowing(logicRequest).getRequest();
				will(returnValue(request));
				
				allowing(logicRequest).getResponse();
				will(returnValue(response));
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
	public void redirectIfUserIsNotTheOwnerOfTheProject() throws Exception {
		givenThereIsAProjectOnRequest();
		Project project = givenThatThisProjectExist();
		givenThatUserIsNotTheOwner(project);
		
		shouldNotExecuteFlow();
		shouldRedirectToNotAllowedPage();
		
		whenInterceptOccurs();
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
				one(flow).execute();
			}
		});
	}
	private void shouldNotExecuteFlow() throws ViewException, LogicException {
		mockery.checking(new Expectations() {
			{
				never(flow).execute();
			}
		});
		
	}

	private void whenInterceptOccurs() throws LogicException, ViewException {
		interceptor.intercept(flow);
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
