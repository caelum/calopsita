package br.com.caelum.calopsita.infra.interceptor;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
public class AuthenticationInterceptor implements Interceptor {
	private final SessionUser sessionUser;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

    public AuthenticationInterceptor(SessionUser sessionUser, HttpServletRequest request, HttpServletResponse response) {
		this.sessionUser = sessionUser;
		this.request = request;
		this.response = response;
    }

	@Override
	public boolean accepts(ResourceMethod method) {
		return !Arrays.asList(UsersController.class, HomeController.class)
			.contains(method.getMethod().getDeclaringClass());
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {
		if (this.sessionUser.getUser() == null) {
			try {
				response.sendRedirect(request.getContextPath() + "/home/login/");
			} catch (IOException e) {
				throw new InterceptionException(e);
			}
        } else {
            stack.next(method, resourceInstance);
        }
	}
}
