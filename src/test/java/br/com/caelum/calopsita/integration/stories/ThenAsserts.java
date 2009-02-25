package br.com.caelum.calopsita.integration.stories;

import static org.hamcrest.Matchers.containsString;

import org.junit.Assert;

import br.com.caelum.seleniumdsl.Browser;

public class ThenAsserts {

    private final Browser browser;

    public ThenAsserts(Browser browser) {
        this.browser = browser;
    }

    public void projectAppearsOnList() {
        // TODO Auto-generated method stub

    }

    public void iMustBeLoggedInAs(String login) {
        String div = this.browser.currentPage().div("user").innerHTML();
        Assert.assertThat(div, containsString(login));
        Assert.assertThat(div, containsString("logout"));
    }

    public void iMustNotBeLoggedIn() {
        // TODO Auto-generated method stub

    }

}
