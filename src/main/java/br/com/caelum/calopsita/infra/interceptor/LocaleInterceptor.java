package br.com.caelum.calopsita.infra.interceptor;

import java.util.Locale;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Interceptor;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class LocaleInterceptor implements Interceptor {

	private final Result result;

	public LocaleInterceptor(Result result) {
		this.result = result;
	}
	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		result.include("locale", Locale.getDefault().getLanguage());
		stack.next(method, resourceInstance);
	}

}
