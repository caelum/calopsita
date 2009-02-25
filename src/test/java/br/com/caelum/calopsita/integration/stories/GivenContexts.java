package br.com.caelum.calopsita.integration.stories;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.User;
import br.com.caelum.seleniumdsl.Browser;

public class GivenContexts {

	private final Browser browser;
	private final Session session;

	public GivenContexts(Browser browser, Session session) {
		this.browser = browser;
		this.session = session;
	}

	public void iAmOnTheRootPage() {
		browser.open("/calopsita");
	}

	public void iHaveAnUser(String login) {
		User user = new User();
		user.setLogin(login);
		user.setEmail(login + "@caelum.com.br");
		user.setName(login);
		user.setPassword(login);
		session.save(user);
	}

	public void iAmLoggedInAs(String string) {
		// TODO Auto-generated method stub

	}

}
