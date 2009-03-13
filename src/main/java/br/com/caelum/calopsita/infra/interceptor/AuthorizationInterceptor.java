package br.com.caelum.calopsita.infra.interceptor;

import java.io.IOException;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.view.ViewException;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.ProjectRepository;

public class AuthorizationInterceptor implements Interceptor {

	private final User user;
	private final ProjectRepository repository;
	
	public AuthorizationInterceptor(User user, ProjectRepository repository) {
		this.user = user;
		this.repository = repository;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		LogicRequest logicRequest = flow.getLogicRequest();
		String id = logicRequest.getRequest().getURLData().getParameter("project.id");
		if (id != null) {
			Project loaded = repository.get(Long.valueOf(id));
			if (loaded != null && !loaded.getOwner().equals(user)) {
				try {
					logicRequest.getResponse().sendRedirect(logicRequest.getRequest().getContextPath() + "/home/notAllowed/");
				} catch (IOException e) {
					throw new LogicException(e);
				}
				return;
			} 
		}
		flow.execute();
	}
	
}
