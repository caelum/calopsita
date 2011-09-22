package br.com.caelum.calopsita.infra.interceptor;

import java.util.Arrays;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class AuthenticationInterceptor implements Interceptor {
	private final SessionUser sessionUser;
	private final Result result;

    public AuthenticationInterceptor(SessionUser sessionUser, Result result) {
		this.sessionUser = sessionUser;
		this.result = result;
    }

	public boolean accepts(ResourceMethod method) {
		return !Arrays.asList(UsersController.class, HomeController.class)
			.contains(method.getMethod().getDeclaringClass());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		if (this.sessionUser.getUser() == null) {
			result.redirectTo(HomeController.class).login();
        } else {
        	result.include("currentUser", sessionUser.getUser());
            stack.next(method, resourceInstance);
        }
	}
}
