package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@SessionScoped
@Component
public class SessionUser {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setRepository(UserRepository repository) {
		getUser().setRepository(repository);
	}

}
