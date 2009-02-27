package br.com.caelum.calopsita.integration.stories;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;
import br.com.caelum.seleniumdsl.Page;

public class WhenActions {

    private final Browser browser;

    public WhenActions(Browser browser) {
        this.browser = browser;
    }

    public void iAddAProject() {
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=New Project");
        Form form = currentPage.form("form");
        form.field("project.name").type("Calopsita");
        form.field("project.description").type("Projeto para gerenciamento de projetos");
        form.submit();
    }

    public void iSignUpAs(String login) {
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Sign Up");
        Form form = currentPage.form("form");
        form.field("user.name").type(login);
        form.field("user.login").type(login);
        form.field("user.email").type(login + "@caelum.com.br");
        form.field("user.password").type(login);
        form.field("user.confirmation").type(login);
        form.submit();
    }

    public void iLoginAs(String login) {
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Login");
        Form form = currentPage.form("form");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iLogout() {
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Logout");
    }
}
