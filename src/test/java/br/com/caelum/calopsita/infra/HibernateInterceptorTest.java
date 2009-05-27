package br.com.caelum.calopsita.infra;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vraptor.LogicException;
import org.vraptor.view.ViewException;

import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class HibernateInterceptorTest {

	private Mockery mockery;
	private HibernateInterceptor interceptor;
	private InterceptorStack stack;
	private SessionFactory factory;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		stack = mockery.mock(InterceptorStack.class);
		factory = mockery.mock(SessionFactory.class);
		interceptor = new HibernateInterceptor(factory);
	}

	@After
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}

	@Test
	public void openAndCloseTransactionWhenFlowIsSuccessful() throws Exception {
		givenThatFlowWasSuccessful();

		Session session = shouldOpenASession();
		Transaction t = shouldBeginATransaction(session);
		shouldCommitTheTransaction(t);

		whenInterceptOccurs();
	}
	@Test(expected=InterceptionException.class)
	public void rollbackTransactionWhenFlowFails() throws Exception {
		givenThatFlowThrowsAException();

		Session session = shouldOpenASession();
		Transaction t = shouldBeginATransaction(session);
		shouldRollbackTheTransaction(t);

		whenInterceptOccurs();
	}
	private void shouldRollbackTheTransaction(final Transaction t) {
		mockery.checking(new Expectations() {
			{
				one(t).rollback();
			}
		});
	}

	private void givenThatFlowThrowsAException() throws ViewException, LogicException {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
				will(throwException(new ViewException()));
			}
		});

	}

	private void shouldCommitTheTransaction(final Transaction t) {

		mockery.checking(new Expectations() {
			{
				one(t).commit();
			}
		});
	}

	private Transaction shouldBeginATransaction(final Session session) {

		final Transaction transaction = mockery.mock(Transaction.class);
		mockery.checking(new Expectations() {
			{
				one(session).beginTransaction();
				will(returnValue(transaction));
			}
		});
		return transaction;
	}

	private Session shouldOpenASession() {

		final Session session = mockery.mock(org.hibernate.classic.Session.class);
		mockery.checking(new Expectations() {
			{
				one(factory).getCurrentSession();
				will(returnValue(session));
			}
		});
		return session;
	}

	private void givenThatFlowWasSuccessful() throws ViewException, LogicException {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});
	}
	private void whenInterceptOccurs() throws LogicException, ViewException {
		interceptor.intercept(stack, null, null);
	}
}
