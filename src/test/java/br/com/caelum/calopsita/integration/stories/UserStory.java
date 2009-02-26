package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

public class UserStory extends DefaultStory {

    @Test
    public void signUpWithANewUser() {
        given.iAmOnTheRootPage();
        when.iSignUpAs("ceci");
        then.iMustBeLoggedInAs("ceci");
    }

    @Test
    public void signUpWithAnExistingUser() {
        given.iHaveAnUser("lucas");
        given.iAmOnTheRootPage();
        when.iSignUpAs("lucas");
        then.iShouldSeeTheError("User already exists");
    }

    @Test
    public void loginWithValidUser() {
        given.iHaveAnUser("lucas");
        given.iAmOnTheRootPage();
        when.iLoginAs("lucas");
        then.iMustBeLoggedInAs("lucas");
    }

    @Test
    public void loginWithInvalidUser() {
        given.iHaveAnUser("lucas");
        given.iAmOnTheRootPage();
        when.iLoginAs("caue");
        then.iShouldSeeTheError("Login invalid");
    }

    @Test
    @Ignore
    public void logout() {
        given.iAmLoggedInAs("caue");
        when.iLogout("caue");
        // then.iMustNotBeLoggedIn();
    }
}
