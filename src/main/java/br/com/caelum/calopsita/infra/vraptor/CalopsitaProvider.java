package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.core.Converters;
import br.com.caelum.vraptor.core.RequestExecution;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;

public class CalopsitaProvider extends PicoProvider {

	@Override
	protected void registerComponents(ComponentRegistry container) {
		super.registerComponents(container);
		container.register(RequestExecution.class, CalopsitaRequestExecution.class);
		container.register(Converters.class, CalopsitaConverters.class);
	}
}

