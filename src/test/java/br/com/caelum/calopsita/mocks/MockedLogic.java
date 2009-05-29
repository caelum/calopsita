package br.com.caelum.calopsita.mocks;

import org.jmock.Expectations;
import org.jmock.Mockery;

import br.com.caelum.vraptor.view.LogicResult;

public class MockedLogic implements LogicResult {

	private final Mockery mockery;

	public MockedLogic() {
		mockery = new Mockery();

		mockery.checking(new Expectations() {
			{
				ignoring(anything());
			}
		});
	}
	@Override
	public <T> T forwardTo(Class<T> type) {
		return mockery.mock(type);
	}

	@Override
	public <T> T redirectTo(Class<T> type) {
		return mockery.mock(type);
	}

}
