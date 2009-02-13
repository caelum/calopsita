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
}
