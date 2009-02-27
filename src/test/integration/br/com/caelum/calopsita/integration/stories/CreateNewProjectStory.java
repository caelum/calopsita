package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

public class CreateNewProjectStory extends DefaultStory {

    @Test
    @Ignore
    public void createProjectWhenThereIsNoProjectYet() {
        given.iAmOnTheRootPage();
        when.iAddAProject();
        then.projectAppearsOnList();
    }
}