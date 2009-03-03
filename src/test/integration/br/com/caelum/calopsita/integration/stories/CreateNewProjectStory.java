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
        when.iAddAProject();
        then.projectAppearsOnList();
    }

    @Test
    public void authentication() {
        given.iAmNotLogged();
        when.iOpenProjectPage();
        then.iAmBackToLoginPage();
    }
}