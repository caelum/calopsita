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

    public void iSignUp() {
        Page currentPage = browser.currentPage();
        currentPage.navigate("link=Sign Up");
        Form form = currentPage.form("form");
        form.field("user.name").type("Cauê Guerra");
        form.field("user.login").type("caueguerra");
        form.field("user.email").type("caue.guerra@caelum.com.br");
        form.field("user.password").type("123456");
        form.field("user.confirmation").type("123456");
        form.submit();
    }
}
