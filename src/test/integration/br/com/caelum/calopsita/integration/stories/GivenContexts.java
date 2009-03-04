package br.com.caelum.calopsita.integration.stories;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;
import br.com.caelum.seleniumdsl.Page;

public class GivenContexts {

    private final Browser browser;
    private final Session session;
    private Project project;
    private User user;

    public GivenContexts(Browser browser, Session session) {
        this.browser = browser;
        this.session = session;
    }

    public void iAmOnTheRootPage() {
        browser.open("/calopsita");
    }

    public void iHaveAnUser(String login) {
        user = new User();
        user.setLogin(login);
        user.setEmail(login + "@caelum.com.br");
        user.setName(login);
        user.setPassword(login);
        session.save(user);
        session.flush();
    }

    public void iAmLoggedInAs(String login) {
        iHaveAnUser(login);
        iAmOnTheRootPage();
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Login");
        Form form = currentPage.form("form");
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

    public GivenContexts iHaveGotTheProject(String name) {
        project = new Project();
        project.setId(1L);
        project.setDescription(name);
        project.setName(name);
        return this;
    }

    public void ownedBy(String user) {
        iHaveAnUser(user);
        project.setOwner(this.user);
        session.save(user);
        session.flush();
    }

}
