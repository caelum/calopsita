package br.com.caelum.calopsita.mocks;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.view.Results;

public class MockResult implements Result {

	private final Map<String, Object> values = new HashMap<String, Object>();

	public void include(String key, Object value) {
		values.put(key, value);
	}

	public Object get(String key) {
		return values.get(key);
	}

	public <T extends View> T use(Class<T> view) {
		if (view.equals(Results.page())) {
			return view.cast(new MockedPage());
		}
		if (view.equals(Results.nothing())) {
			return null;
		}
		return view.cast(new MockedLogic());
	}

	public boolean used() {
		return false;
	}

}
