package br.com.caelum.calopsita.infra.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.model.PluginConfig;
import br.com.caelum.calopsita.plugins.DefaultMenus;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Component
public class MenuInterceptor implements Interceptor {

	private final HttpServletRequest request;
	private final Parameters parameters;
	private final Result result;
	private final Set<PluginConfig> plugins;

	public MenuInterceptor(Parameters parameters, Result result, HttpServletRequest request, Set<PluginConfig> plugins) {
		this.parameters = parameters;
		this.result = result;
		this.request = request;
		this.plugins = plugins;

	}
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		String path = request.getContextPath();
		Menu menu = new Menu(path);
		new DefaultMenus().includeMenus(menu, parameters);
		for (PluginConfig config : plugins) {
			config.includeMenus(menu, parameters);
		}
		result.include("menu", menu);
		stack.next(method, resourceInstance);
	}


}
