package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;
/**
 * In order to have something to manage
 * As a Developer leader
 * I want to create a project
 *
 */
public class CreateNewProjectStory extends DefaultStory {

    @Test
    @Ignore
    public void createProjectWhenThereIsNoProjectYet() {
        given.iAmLoggedInAs("Caue");
        when.iAddTheProject("Calopsita");
        then.project("Calopsita").appearsOnList();
    }
    @Test
    @Ignore
    public void listingProjects() {
    	given.iAmLoggedInAs("Caue");
    	given.iHaveGotTheProject("calopsita").ownedBy("Caue");
    	when.iListProjects();
    	then.project("Calopsita").appearsOnList();
    }

    @Test
    public void authentication() {
        given.iAmNotLogged();
        when.iOpenProjectPage();
        then.iAmBackToLoginPage();
    }
}