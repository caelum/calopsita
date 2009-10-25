package br.com.caelum.calopsita.infra.vraptor;

import java.lang.annotation.Annotation;

import br.com.caelum.calopsita.model.Plugin;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.StereotypeHandler;

@Component
@ApplicationScoped
public class PluginStereotypeHandler implements StereotypeHandler {

	public void handle(Class<?> type) {
	}

	public Class<? extends Annotation> stereotype() {
		return Plugin.class;
	}

}
