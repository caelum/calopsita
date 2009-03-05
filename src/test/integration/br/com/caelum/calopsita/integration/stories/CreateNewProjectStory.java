package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

/**
 * In order to have something to manage As a Developer leader I want to create a
 * project
 * 
 */
public class CreateNewProjectStory extends DefaultStory {

    @Test
    public void createProject() {
        given.iHaveAnUser("Caue");
        given.iAmLoggedInAs("Caue");
        when.iAddTheProject("CalopsitaProject");
        then.project("CalopsitaProject").appearsOnList();
    }

    @Test
    public void createTwoProjects() {
        given.iHaveAnUser("Caue");
        given.iAmLoggedInAs("Caue");
        when.iAddTheProject("CalopsitaProject");
        when.iAddTheProject("TatameProject");
        then.project("CalopsitaProject").appearsOnList();
        then.project("TatameProject").appearsOnList();
    }

    @Test
    public void listingProjects() {
        given.iHaveAnUser("Caue");
        given.iAmLoggedInAs("Caue");
        given.iHaveAProject("CalopsitaProject").ownedBy("Caue");
        when.iListProjects();
        then.project("CalopsitaProject").appearsOnList();
    }

    @Test
    public void authentication() {
        given.iAmNotLogged();
        when.iOpenProjectPage();
        then.iAmBackToLoginPage();
    }
}