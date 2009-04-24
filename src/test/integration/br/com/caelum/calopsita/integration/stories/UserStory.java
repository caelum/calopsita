package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> join a project <br> 
 * <b>As a</b> Developer or Client <br>
 * <b>I want to</b> create a user
 * 
 */
public class UserStory extends DefaultStory {

    @Test
    public void signUpWithANewUser() {
    	given.theUserDoesntExist("ceci");
        given.iAmOnTheRootPage();
        when.iSignUpAs("ceci");
        then.iMustBeLoggedInAs("ceci");
    }

    @Test
    public void signUpWithAnExistingUser() {
        given.thereIsAnUserNamed("lucas");
        given.iAmOnTheRootPage();
        when.iSignUpAs("lucas");
        then.iShouldSeeTheError("User already exists");
    }

    @Test
    public void loginWithValidUser() {
        given.thereIsAnUserNamed("lucas");
        given.iAmOnTheRootPage();
        when.iLoginAs("lucas");
        then.iMustBeLoggedInAs("lucas");
    }

    @Test
    public void loginWithInvalidUser() {
        given.thereIsAnUserNamed("lucas");
        given.iAmOnTheRootPage();
        when.iLoginAs("caue");
        then.iShouldSeeTheError("Login invalid");
    }

    @Test
    public void logout() {
        given.thereIsAnUserNamed("lucas");
        given.iAmLoggedInAs("lucas");
        when.iLogout();
        then.iMustNotBeLoggedIn();
    }
    
    
	@Test
	public void loggedUserAccessingRootDirectoryIsRedirectedToUserMainPage() {
		given.thereIsAnUserNamed("adriano");
		given.iAmLoggedInAs("adriano");
		when.iChangeTheUrlToCalopsitasRoot();
		then.iMustBeInMyMainPage();
		then.iMustBeLoggedInAs("adriano");
	}
}
