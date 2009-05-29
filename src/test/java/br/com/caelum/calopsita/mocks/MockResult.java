package br.com.caelum.calopsita.mocks;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.view.Results;

public class MockResult implements Result {

	@Override
	public void include(String key, Object value) {

	}

	@Override
	public <T extends View> T use(Class<T> view) {
		if (view.equals(Results.page())) {
			return view.cast(new MockedPage());
		}
		return view.cast(new MockedLogic());
	}

	@Override
	public boolean used() {
		return false;
	}

}
