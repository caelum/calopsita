package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.iogi.Instantiator;
import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ParametersProvider;
import br.com.caelum.vraptor.http.iogi.IogiParametersProvider;
import br.com.caelum.vraptor.http.iogi.VRaptorInstantiator;
import br.com.caelum.vraptor.ioc.spring.SpringProvider;
import br.com.caelum.vraptor.util.hibernate.SessionFactoryCreator;

public class CalopsitaProvider extends SpringProvider {

	@Override
	protected void registerCustomComponents(ComponentRegistry registry) {
		registry.register(SessionFactoryCreator.class, SessionFactoryCreator.class);
		registry.register(ParametersProvider.class, IogiParametersProvider.class);
		registry.register(Instantiator.class, VRaptorInstantiator.class);
	}

}
