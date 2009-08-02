package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ognl.OgnlParametersProvider;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;

public class CalopsitaProvider extends PicoProvider {

	@Override
	protected void registerBundledComponents(ComponentRegistry registry) {
		super.registerBundledComponents(registry);
		registry.register(OgnlParametersProvider.class, OgnlParametersProvider.class);
	}
}
