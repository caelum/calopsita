package br.com.caelum.calopsita.infra.vraptor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.interceptor.RepositoryInterceptor;
import br.com.caelum.vraptor.proxy.ObjenesisProxifier;

public class SessionCreatorTest {


	private Mockery mockery;
	private SessionCreator creator;
	private SessionFactory factory;
	private RepositoryInterceptor interceptor;
	private org.hibernate.classic.Session session;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		factory = mockery.mock(SessionFactory.class);
		interceptor = new RepositoryInterceptor(null);
		session = mockery.mock(org.hibernate.classic.Session.class);

		creator = new SessionCreator(factory, interceptor, new ObjenesisProxifier());
	}

	@Test
	public void shouldOpenSessionWithInterceptorOnCreate() throws Exception {
		shouldOpenSessionWithInterceptor();
		creator.create();
		Session session = creator.getInstance();
		callAnyMethodOn(session);
		shouldCloseSessionOnDestroy();
		creator.destroy();
		mockery.assertIsSatisfied();
	}

	private void callAnyMethodOn(Session sess) {

		mockery.checking(new Expectations() {
			{
				one(session).beginTransaction();
			}
		});
		sess.beginTransaction();
	}

	private void shouldCloseSessionOnDestroy() {

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
				will(returnValue(session));
			}
		});
	}
}
