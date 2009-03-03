package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

/**
 * In order to join a project
 * As a Developer or Client
 * I want to create a user
 *
 */
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
    public void logout() {
        given.iAmLoggedInAs("lucas");
        when.iLogout();
        then.iMustNotBeLoggedIn();
    }
}
