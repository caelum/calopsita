package br.com.caelum.calopsita.infra.vraptor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.interceptor.RepositoryInterceptor;

public class SessionCreatorTest {


	private Mockery mockery;
	private SessionCreator creator;
	private SessionFactory factory;
	private RepositoryInterceptor interceptor;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		factory = mockery.mock(SessionFactory.class);
		interceptor = new RepositoryInterceptor(null);

		creator = new SessionCreator(factory, interceptor);
	}

	@Test
	public void shouldOpenSessionWithInterceptorOnCreate() throws Exception {
		shouldOpenSessionWithInterceptor();
		creator.create();
		Session session = creator.getInstance();
		shouldCloseSessionOnDestroy(session);
		creator.destroy();
		mockery.assertIsSatisfied();
	}

	private void shouldCloseSessionOnDestroy(final Session session) {

		mockery.checking(new Expectations() {
			{
				one(session).close();
			}
		});
	}

	private void shouldOpenSessionWithInterceptor() {

		mockery.checking(new Expectations() {
			{
				one(factory).openSession(interceptor);
				will(returnValue(mockery.mock(org.hibernate.classic.Session.class)));
			}
		});
	}
}
