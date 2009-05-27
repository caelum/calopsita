package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> have no need for editing iteration dates <br>
 * <b>As a</b> Vinicius <br>
 * <b>I want to</b> start and finish iterations with buttons <br>
 * 
 */
public class StartAndFinishIterationStory extends DefaultStory {
    @Test
    public void startingIteration() throws Exception {
        given.thereIsAnUserNamed("vinicius").and()
        	.thereIsAProjectNamed("arca").ownedBy("vinicius")
        		.withAnIterationWhichGoalIs("start singing").and()
        	.iAmLoggedInAs("vinicius");
        when.iOpenProjectPageOf("arca").and()
            .iOpenIterationsPage().and()
        	.iStartTheIteration("start singing");
        then.theCurrentIterationIs("start singing");
        when.iOpenProjectPageOf("arca");
        then.theIterationThatAppearsIs("start singing");
    }
    
    @Test
    public void endingIteration() throws Exception {
        given.thereIsAnUserNamed("vinicius").and()
        .thereIsAProjectNamed("arca").ownedBy("vinicius")
            .withAnIterationWhichGoalIs("start singing").startingYesterday().and()
        .iAmLoggedInAs("vinicius");
    
        when.iOpenProjectPageOf("arca").and()
            .iOpenIterationsPage().and()
            .iEndTheIteration("start singing");

        then.theCurrentIterationEndsToday();
    }
}
