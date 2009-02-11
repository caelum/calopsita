package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

public class CreateNewProject extends DefaultScenario {

    @Test
    public void createProjectWhenThereIsNoProjectYet() {
        given.iAmOnTheRootPage();
        when.iAddAProject();
        then.projectAppearsOnList();
    }

}
