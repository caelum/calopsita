package br.com.caelum.calopsita.model;

import br.com.caelum.calopsita.infra.vraptor.CalopsitaResult;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class DefaultParameters implements Parameters {

	private final CalopsitaResult result;

	public DefaultParameters(CalopsitaResult result) {
		this.result = result;
	}

	public boolean contains(String parameterName) {
		return result.getIncludedObjects().containsKey(parameterName);
	}

	public <T> T get(String parameterName) {
		return (T) result.getIncludedObjects().get(parameterName);
	}
}
