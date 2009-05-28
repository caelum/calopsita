package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SessionScoped
@Resource
public class SessionUser {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
