package br.com.caelum.calopsita.infra.vraptor;

import java.util.Set;

import org.vraptor.VRaptorException;
import org.vraptor.component.ComponentType;
import org.vraptor.plugin.VRaptorPlugin;
import org.vraptor.webapp.WebApplication;

/**
 * Começo de um plugin para registrar interceptors globais
 * @author sergio
 */
public class GlobalInterceptorsPlugin implements VRaptorPlugin {

	public void init(WebApplication webapp) throws VRaptorException {
		Set<ComponentType> components = webapp.getComponentManager().getComponents();
		for (ComponentType componentType : components) {
			webapp.getComponentManager().register(new GlobalInterceptorsComponentWrapper(componentType));
		}
	}

}
