package br.com.caelum.calopsita.integration.stories;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;

public class WhenActions {

    private final Browser browser;

    public WhenActions(Browser browser) {
        this.browser = browser;
    }

    public void iAddAProject() {
        iClickOn("New Project");
        Form form = browser.currentPage().form("form");
        form.field("project.name").type("Calopsita");
        form.field("project.description").type("Projeto para gerenciamento de projetos");
        form.submit();
    }

    public void iSignUpAs(String login) {
        iClickOn("Sign Up");
        Form form = browser.currentPage().form("form");
        form.field("user.name").type(login);
        form.field("user.login").type(login);
        form.field("user.email").type(login + "@caelum.com.br");
        form.field("user.password").type(login);
        form.field("user.confirmation").type(login);
        form.submit();
    }

    public void iLoginAs(String login) {
        iClickOn("Login");
        Form form = browser.currentPage().form("form");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iLogout() {
        iClickOn("Logout");
    }

	public void iClickOn(String link) {
        browser.currentPage().navigate("link=" + link);
	}

	public void iOpenProjectPage() {
		// TODO Auto-generated method stub
		
	}
}
