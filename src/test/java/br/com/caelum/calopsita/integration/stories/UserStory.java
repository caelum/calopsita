package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

public class UserStory extends DefaultStory {

    @Test
    public void signUp() {
        given.iAmOnTheRootPage();
        when.iSignUpAs("ceci");
        then.iMustBeLoggedInAs("ceci");
    }

    @Test
    @Ignore
    public void loginWithValidUser() {
        given.iHaveAnUser("lucas");
        when.iLoginAs("lucas");
        then.iMustBeLoggedInAs("lucas");
    }

    @Test
    @Ignore
    public void loginWithInvalidUser() {
        given.iHaveAnUser("lucas");
        when.iLoginAs("caue");
        then.iMustNotBeLoggedIn();
    }

    @Test
    @Ignore
    public void logout() {
        given.iAmLoggedInAs("caue");
        when.iLogout("caue");
        then.iMustNotBeLoggedIn();
    }
}
