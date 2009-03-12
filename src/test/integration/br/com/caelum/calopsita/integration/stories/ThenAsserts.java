package br.com.caelum.calopsita.integration.stories;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.junit.Assert;

import br.com.caelum.seleniumdsl.Browser;

public class ThenAsserts {

    private final Browser browser;
    private String projectName;

    public ThenAsserts(Browser browser) {
        this.browser = browser;
    }

    public void iMustBeLoggedInAs(String login) {
        String div = this.browser.currentPage().div("user").innerHTML();
        Assert.assertThat(div, containsString(login));
        Assert.assertThat(div, containsString("Logout"));
    }

    public void iShouldSeeTheError(String error) {
        String div = browser.currentPage().div("errors").innerHTML();
        Assert.assertThat(div, containsString(error));
    }

    public void iMustNotBeLoggedIn() {
        String div = this.browser.currentPage().div("user").innerHTML();
        Assert.assertThat(div, containsString("Login"));
    }

    public void iAmBackToLoginPage() {
        String div = this.browser.currentPage().div("login").innerHTML();
        Assert.assertThat(div, containsString("Login"));
        Assert.assertThat(div, containsString("Password"));
    }

    public ThenAsserts project(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void appearsOnList() {
        String div = this.browser.currentPage().div("projects").innerHTML();
        Assert.assertThat(div, containsString(projectName));
    }

	public void thisUserAppearsOnColaboratorsList(String string) {
		
	}

    public void notAppearsOnList() {
        String div = this.browser.currentPage().div("projects").innerHTML();
        Assert.assertThat(div, not(containsString(projectName)));
    }

	public void iAmNotAllowedToSeeTheProject() {
		String div = this.browser.currentPage().div("index").innerHTML();
		Assert.assertThat(div, containsString("not allowed to see this project"));
	}
}
