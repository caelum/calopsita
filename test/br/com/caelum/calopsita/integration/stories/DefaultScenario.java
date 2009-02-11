package br.com.caelum.calopsita.integration.stories;

import org.junit.After;
import org.junit.Before;

import br.com.caelum.seleniumdsl.Browser;

public class DefaultScenario {
    protected GivenContexts given;
    protected WhenActions when;
    protected ThenAsserts then;
    private SeleniumFactory factory;

    @Before
    public void setUp() {
        factory = new SeleniumFactory();
        Browser browser = factory.getBrowser();
        given = new GivenContexts(browser);
        when = new WhenActions(browser);
        then = new ThenAsserts(browser);
    }

    @After
    public void tearDown() {
        factory.close();
    }
}
