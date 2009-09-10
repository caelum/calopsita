package br.com.caelum.calopsita.mocks;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import br.com.caelum.vraptor.view.PageResult;

public class MockedPage implements PageResult {

	private final Mockery mockery;

	public MockedPage() {
		mockery =  new Mockery() {
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
	public void forward() {
	}

	public void forward(String url) {
	}

	public void include() {
	}

	public void redirect(String url) {
	}

	public <T> T of(Class<T> controllerType) {
		return mockery.mock(controllerType);
	}

}
