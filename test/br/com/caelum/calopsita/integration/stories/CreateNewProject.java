package br.com.caelum.calopsita.integration.stories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.seleniumdsl.Browser;

public class CreateNewProject {

    private GivenContexts given;
    private WhenActions when;
    private ThenAsserts then;
    private SeleniumFactory factory;

    @Before
    public void setUp() {
        factory = new SeleniumFactory();
        Browser browser = factory.getBrowser();
        given = new GivenContexts(browser);
        when = new WhenActions(browser);
        then = new ThenAsserts(browser);
    }

    @Test
    public void createProjectWhenThereIsNoProjectYet() {
        given.iAmOnTheRootPage();
        when.iAddAProject();
        then.projectAppearsOnList();
    }

    @After
    public void tearDown() {
        factory.close();
    }
}
