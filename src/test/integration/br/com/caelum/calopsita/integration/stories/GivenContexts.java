package br.com.caelum.calopsita.integration.stories;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;
import br.com.caelum.seleniumdsl.Page;

public class GivenContexts {

    private final Browser browser;
    private final Session session;
    private Project project;

    public GivenContexts(Browser browser, Session session) {
        this.browser = browser;
        this.session = session;
    }

    public void iAmOnTheRootPage() {
        browser.open("/calopsita");
    }

    public void thereIsAnUserNamed(String login) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        session.saveOrUpdate(user);
        session.flush();
    }

    public void iAmLoggedInAs(String login) {
        iAmOnTheRootPage();
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Login");
        Form form = currentPage.form("login");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iAmNotLogged() {
        iAmOnTheRootPage();
        if (browser.currentPage().div("user").contains("Logout")) {
            browser.currentPage().click("link=Logout");
        }
    }

    public GivenContexts thereIsAProjectNamed(String name) {
        project = new Project();
        project.setId(1L);
        project.setDescription(name);
        project.setName(name);
        session.save(project);
        session.flush();
        return this;
    }

    public GivenContexts ownedBy(String login) {
        UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.setOwner(user);
        session.save(user);
        session.flush();
        return this;
    }

	public void theUserDoesntExist(String name) {
		session.createQuery("delete from User u where u.name = :name").setParameter("name", name).executeUpdate();
	}

	public void withColaborator(String login) {
		UserDao userDao = new UserDao(session);
        User user = userDao.find(login);
        project.getColaborators().add(user);
        session.flush();
	}

}
