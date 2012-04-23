package br.com.caelum.calopsita.infra.interceptor;

import java.util.Arrays;

import br.com.caelum.calopsita.controller.HomeController;
import br.com.caelum.calopsita.controller.UsersController;
import br.com.caelum.calopsita.infra.vraptor.SessionUser;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
@Intercepts
public class AuthorizationInterceptor implements Interceptor {

	private final ProjectRepository repository;
	private final SessionUser user;
	private final MethodInfo parameters;
	private final UserRepository userRepository;
	private final Result result;

	public AuthorizationInterceptor(SessionUser user, UserRepository userRepository, ProjectRepository repository, Result result, MethodInfo parameters) {
		this.user = user;
		this.userRepository = userRepository;
		this.repository = repository;
		this.result = result;
		this.parameters = parameters;
	}

	public boolean accepts(ResourceMethod method) {
		return method.getMethod().getName().equals("toggleNewbie")
			|| !Arrays.asList(UsersController.class, HomeController.class)
				.contains(method.getMethod().getDeclaringClass());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method,
			Object resourceInstance) throws InterceptionException {

		if (user.getUser() != null && repository.hasInconsistentValues(parameters.getParameters(), user.getUser())) {
			result.redirectTo(HomeController.class).notAllowed();
			return;
		}
		if (user.getUser() != null) {
			user.getUser().setRepository(userRepository);
		}
		stack.next(method, resourceInstance);
	}

}
