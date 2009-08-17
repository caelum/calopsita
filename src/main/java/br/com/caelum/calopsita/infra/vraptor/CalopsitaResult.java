package br.com.caelum.calopsita.infra.vraptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.core.DefaultResult;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.Container;

@Component
public class CalopsitaResult implements Result {

	private final Map<String, Object> included = new HashMap<String, Object>();
	private final DefaultResult delegate;

	public CalopsitaResult(HttpServletRequest request, Container container) {
		delegate = new DefaultResult(request, container);
	}

	public Map<String, Object> getIncludedObjects() {
		return included;
	}

	public void include(String key, Object value) {
		included.put(key, value);
		delegate.include(key, value);
	}

	public <T extends View> T use(Class<T> view) {
		return delegate.use(view);
	}

	public boolean used() {
		return delegate.used();
	}

}
