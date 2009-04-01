package br.com.caelum.calopsita.infra;

import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import javax.servlet.http.HttpSession;

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

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.model.User;

public class AuthenticationInterceptorTest {

	
	private Mockery mockery;
	private AuthenticationInterceptor interceptor;
	private LogicFlow flow;
	private VRaptorServletRequest request;
	private VRaptorServletResponse response;
	private HttpSession session;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		flow = mockery.mock(LogicFlow.class);
		session = mockery.mock(HttpSession.class);
		interceptor = new AuthenticationInterceptor(session);
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
				one(session).getAttribute(User.class.getName());
				will(returnValue(null));
			}
		});
	}

	private void givenThereIsAUserInTheSession() {
		mockery.checking(new Expectations() {
			{
				one(session).getAttribute(User.class.getName());
				will(returnValue(new User()));
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
}
