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
        	.iStartTheIteration("start singing");
        then.theCurrentIterationIs("start singing");
    }
    
    @Test
    @Ignore
    public void endingIteration() throws Exception {
        given.thereIsAnUserNamed("vinicius").and()
        .thereIsAProjectNamed("arca").ownedBy("vinicius")
            .withAnIterationWhichGoalIs("start singing").startingYesterday().and()
        .iAmLoggedInAs("vinicius");
    
    when.iOpenProjectPageOf("arca");
    when.iAddTheIteration("create and update an incident").withStartDate("04/04/2000").withEndDate("04/05/2000");
    
    then.theIteration("create and update an incident").appearsOnList();
    }
}
