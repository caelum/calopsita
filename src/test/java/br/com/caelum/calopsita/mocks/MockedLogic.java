package br.com.caelum.calopsita.mocks;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import br.com.caelum.vraptor.view.LogicResult;

public class MockedLogic implements LogicResult {

	private final Mockery mockery;

	public MockedLogic() {
		mockery = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		mockery.checking(new Expectations() {
			{
				ignoring(anything());
			}
		});
	}

	public <T> T forwardTo(Class<T> type) {
		return mockery.mock(type);
	}

	public <T> T redirectTo(Class<T> type) {
		return mockery.mock(type);
	}

}
