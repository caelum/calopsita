package br.com.caelum.calopsita.infra.interceptor;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Interceptor;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.resource.ResourceMethod;
@Intercepts
public class AuthorizationInterceptor implements Interceptor {

	private final ProjectRepository repository;
	private final SessionUser user;
	private final HttpServletResponse response;
	private final HttpServletRequest request;
	private final MethodInfo parameters;

	public AuthorizationInterceptor(SessionUser user, ProjectRepository repository, HttpServletRequest request, HttpServletResponse response, MethodInfo parameters) {
		this.user = user;
		this.repository = repository;
		this.request = request;
		this.response = response;
		this.parameters = parameters;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return !Arrays.asList(UsersController.class, HomeController.class)
				.contains(method.getMethod().getDeclaringClass());
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {

		if (user.getUser() != null && repository.hasInconsistentValues(parameters.getParameters(), user.getUser())) {
			try {
				response.sendRedirect(request.getContextPath() + "/home/notAllowed/");
			} catch (IOException e) {
				throw new InterceptionException(e);
			}
			return;
		}
		stack.next(method, resourceInstance);
	}

}
