package br.com.caelum.calopsita.infra.vraptor;

import org.picocontainer.PicoContainer;

import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.plugins.Transformer;
import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.http.ognl.OgnlParametersProvider;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;
import br.com.caelum.vraptor.ioc.pico.Scanner;
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

	@Override
	protected void registerCustomComponents(PicoContainer picoContainer, Scanner scanner) {
		for (Class<? extends PluginConfig> plugin : scanner.getSubtypesOf(PluginConfig.class)) {
			getComponentRegistry().register(plugin, plugin);
		}
		for (Class<? extends Transformer> type : scanner.getSubtypesOf(Transformer.class)) {
			getComponentRegistry().register(type, type);
		}
	}
}
