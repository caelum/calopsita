package br.com.caelum.calopsita.infra;

import static org.junit.Assert.assertFalse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.DefaultResourceClass;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;

public class HibernateInterceptorTest {

	private Mockery mockery;
	private HibernateInterceptor interceptor;
	private InterceptorStack stack;
	private Session session;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		stack = mockery.mock(InterceptorStack.class);
		session = mockery.mock(Session.class);
		interceptor = new HibernateInterceptor(session);
	}

	@Test
	public void openAndCloseTransactionWhenFlowIsSuccessful() throws Exception {
		givenThatFlowWasSuccessful();

		Transaction t = shouldBeginATransaction(session);
		shouldCommitTheTransaction(t);

		whenInterceptOccurs();
		mockery.assertIsSatisfied();
	}
	@Test(expected=InterceptionException.class)
	public void rollbackTransactionWhenFlowFails() throws Exception {
		givenThatFlowThrowsAException();

		Transaction t = shouldBeginATransaction(session);
		shouldRollbackTheTransaction(t);

		whenInterceptOccurs();
		mockery.assertIsSatisfied();
	}

	@Test
	public void shouldBypassUsersAndHomeController() throws Exception {
		assertFalse(interceptor.accepts(anyResourceMethodOf(HomeController.class)));
	}

	private ResourceMethod anyResourceMethodOf(Class<?> clazz) {
		return new DefaultResourceMethod(new DefaultResourceClass(clazz), clazz.getDeclaredMethods()[0]);
	}

	private void shouldRollbackTheTransaction(final Transaction t) {
		mockery.checking(new Expectations() {
			{
				one(t).isActive();
				will(returnValue(true));

				one(t).rollback();
			}
		});
	}

	private void givenThatFlowThrowsAException() {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
				will(throwException(new InterceptionException("")));
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

				allowing(session).getTransaction();
				will(returnValue(transaction));
			}
		});
		return transaction;
	}

	private void givenThatFlowWasSuccessful() {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});
	}
	private void whenInterceptOccurs() {
		interceptor.intercept(stack, null, null);
	}
}
