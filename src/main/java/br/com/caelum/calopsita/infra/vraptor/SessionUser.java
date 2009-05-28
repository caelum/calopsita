package br.com.caelum.calopsita.infra.vraptor;

import javax.servlet.http.HttpSession;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SessionScoped
@Resource
public class SessionUser {

	private final HttpSession session;

	public SessionUser(HttpSession session) {
		this.session = session;
	}

	public void setUser(User user) {
		this.session.setAttribute("currentUser", user);
	}

	public User getUser() {
		return (User) this.session.getAttribute("currentUser");
	}

}
