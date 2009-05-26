package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> organize my work <br>
 * <b>As a</b> project owner <br>
 * <b>I want</b> to put existent cards in an iteration <br>
 *
 * @author lucascs
 */
public class AddCardOnAnIterationStory extends DefaultStory {

	@Test
	public void addACardInAnIteration() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("new release").and()
				.withACardNamed("support IE8").whichDescriptionIs("IE8 must be supported").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("new release").and()
			.iAddTheCard("support IE8").inThisIteration();
		then.theCard("support IE8").appearsOnTodoList();
	}


	@Test
	public void promiscuousCardsBugDontHappen() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("new release").and()
				.withACardNamed("support IE8").whichDescriptionIs("IE8 must be supported").and()
			.thereIsAProjectNamed("Tatanka")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("get promiscuous").and()
				.withACardNamed("I am promiscuous").whichDescriptionIs("You know what it means").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("new release");
		then.theCard("support IE8").appearsOnBacklog().and()
			.theCard("I am promiscuous").notAppearsOnBacklog();
		when.iOpenProjectPageOf("Tatanka").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("get promiscuous");
		then.theCard("support IE8").notAppearsOnBacklog().and()
			.theCard("I am promiscuous").appearsOnBacklog();
	}
}
