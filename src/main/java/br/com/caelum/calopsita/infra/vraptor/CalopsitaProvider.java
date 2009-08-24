package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ognl.OgnlParametersProvider;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;
import br.com.caelum.vraptor.util.hibernate.SessionCreator;
import br.com.caelum.vraptor.util.hibernate.SessionFactoryCreator;

public class CalopsitaProvider extends PicoProvider {

	@Override
	protected void registerBundledComponents(ComponentRegistry registry) {
		super.registerBundledComponents(registry);
		registry.register(OgnlParametersProvider.class, OgnlParametersProvider.class);

		registry.register(SessionCreator.class, SessionCreator.class);
		registry.register(SessionFactoryCreator.class, SessionFactoryCreator.class);
	}
}
