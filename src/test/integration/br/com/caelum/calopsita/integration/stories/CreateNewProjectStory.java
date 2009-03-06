package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

/**
 * In order to have something to manage 
 * As a Developer leader 
 * I want to create a project
 * 
 */
public class CreateNewProjectStory extends DefaultStory {

    @Test
    public void createProject() {
        given.thereIsAnUserNamed("Caue");
        given.iAmLoggedInAs("Caue");
        when.iAddTheProject("CalopsitaProject");
        then.project("CalopsitaProject").appearsOnList();
    }

    @Test
    public void createTwoProjects() {
        given.thereIsAnUserNamed("Caue");
        given.iAmLoggedInAs("Caue");
        when.iAddTheProject("CalopsitaProject");
        when.iAddTheProject("TatameProject");
        then.project("CalopsitaProject").appearsOnList();
        then.project("TatameProject").appearsOnList();
    }

    @Test
    public void listingProjects() {
        given.thereIsAnUserNamed("Caue");
        given.iAmLoggedInAs("Caue");
        given.thereIsAProjectNamed("CalopsitaProject").ownedBy("Caue");
        when.iListProjects();
        then.project("CalopsitaProject").appearsOnList();
    }

    @Test
    public void authentication() {
        given.iAmNotLogged();
        when.iOpenProjectPageDirectly();
        then.iAmBackToLoginPage();
    }
}