package br.com.caelum.calopsita.infra.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.vraptor.Interceptor;
import org.vraptor.LogicException;
import org.vraptor.LogicFlow;
import org.vraptor.LogicRequest;
import org.vraptor.view.ViewException;

import br.com.caelum.calopsita.model.User;

public class AuthenticationInterceptor implements Interceptor {
	private final HttpSession session;
	private User user;

	public AuthenticationInterceptor(HttpSession session) {
		this.session = session;
	}

	public void intercept(LogicFlow flow) throws LogicException, ViewException {
		this.user = (User) session.getAttribute(User.class.getName());
		if (this.user == null) {
			try {
				LogicRequest logicRequest = flow.getLogicRequest();
				HttpServletRequest request = logicRequest.getRequest();
				logicRequest.getResponse().sendRedirect(request.getContextPath() + "/home/login");
			} catch (IOException e) {
				throw new LogicException(e);
			}
		} else {
			flow.execute();
		}
	}
}
