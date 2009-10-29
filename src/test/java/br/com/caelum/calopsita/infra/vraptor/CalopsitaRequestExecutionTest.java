package br.com.caelum.calopsita.infra.vraptor;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.infra.interceptor.MenuInterceptor;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.OutjectResult;

public class CalopsitaRequestExecutionTest {


	private Mockery mockery;
	private InterceptorStack stack;
	private CalopsitaRequestExecution execution;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		stack = mockery.mock(InterceptorStack.class);
		execution = new CalopsitaRequestExecution(stack, null);
	}

	@Test
	public void menuInterceptorMustBeIncludedAfterOutjectingResult() throws Exception {
		shouldIncludeMenuAfterOutjection();
		execution.execute();
		mockery.assertIsSatisfied();
	}

	private void shouldIncludeMenuAfterOutjection() {
		final Sequence execution = mockery.sequence("execution");
		mockery.checking(new Expectations() {
			{
				one(stack).add(OutjectResult.class); inSequence(execution);
				one(stack).add(MenuInterceptor.class); inSequence(execution);

				ignoring(anything());
			}
		});
	}
}
