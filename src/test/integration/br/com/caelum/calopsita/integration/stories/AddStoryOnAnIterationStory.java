package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> organize my work <br>
 * <b>As a</b> project owner <br>
 * <b>I want</b> to put existent stories in an iteration <br>
 *
 * @author lucascs
 */
public class AddStoryOnAnIterationStory extends DefaultStory {

	@Test
	public void addAStoryInAnIteration() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("new release").and()
				.withAStoryNamed("support IE8").whichDescriptionIs("IE8 must be supported").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("new release").and()
			.iAddTheStory("support IE8").inThisIteration();
		then.theStory("support IE8").appearsOnTodoList();
	}


	@Test
	public void promiscuousStoriesBugDontHappen() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("new release").and()
				.withAStoryNamed("support IE8").whichDescriptionIs("IE8 must be supported").and()
			.thereIsAProjectNamed("Tatanka")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("get promiscuous").and()
				.withAStoryNamed("I am promiscuous").whichDescriptionIs("You know what it means").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("new release");
		then.theStory("support IE8").appearsOnBacklog().and()
			.theStory("I am promiscuous").notAppearsOnBacklog();
		when.iOpenProjectPageOf("Tatanka").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("get promiscuous");
		then.theStory("support IE8").notAppearsOnBacklog().and()
			.theStory("I am promiscuous").appearsOnBacklog();
	}
}
