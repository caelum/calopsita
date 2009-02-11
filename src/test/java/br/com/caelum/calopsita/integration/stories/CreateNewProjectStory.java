package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

public class CreateNewProjectStory extends DefaultStory {

    @Test
    public void createProjectWhenThereIsNoProjectYet() {
        given.iAmOnTheRootPage();
        when.iAddAProject();
        then.projectAppearsOnList();
    }
}