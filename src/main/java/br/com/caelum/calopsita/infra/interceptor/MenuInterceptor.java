package br.com.caelum.calopsita.infra.interceptor;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.calopsita.model.Menu;
import br.com.caelum.calopsita.model.Parameters;
import br.com.caelum.calopsita.plugins.DefaultPlugin;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class MenuInterceptor implements Interceptor {

	private final HttpServletRequest request;
	private final Parameters parameters;
	private final Result result;

	public MenuInterceptor(Parameters parameters, Result result, HttpServletRequest request) {
		this.parameters = parameters;
		this.result = result;
		this.request = request;

	}
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		String path = request.getContextPath();
		Menu menu = new Menu(path);
		new DefaultPlugin().includeMenus(menu, parameters);
		result.include("menu", menu);
		stack.next(method, resourceInstance);
	}


}
