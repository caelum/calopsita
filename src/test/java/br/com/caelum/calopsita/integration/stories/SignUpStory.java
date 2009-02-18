package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

public class SignUpStory extends DefaultStory {

    @Test
    public void signUp() {
        given.iAmOnTheRootPage();
        when.iSignUp();
        then.iMustBeLoggedIn();
    }
}
