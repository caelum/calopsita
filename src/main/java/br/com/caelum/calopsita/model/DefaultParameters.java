package br.com.caelum.calopsita.model;

import br.com.caelum.calopsita.infra.vraptor.CalopsitaResult;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class DefaultParameters implements Parameters {

	private final CalopsitaResult result;

	public DefaultParameters(Result result) {
		this.result = (CalopsitaResult) result;
	}

	public boolean contains(String parameterName) {
		return result.included().get(parameterName) != null;
	}

	public <T> T get(String parameterName) {
		return (T) result.included().get(parameterName);
	}
}
