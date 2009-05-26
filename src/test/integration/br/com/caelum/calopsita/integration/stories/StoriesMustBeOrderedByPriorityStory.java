package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> know what i need to do <br>
 * <b>As a</b> project developer <br>
 * <b>I want</b> be able to order stories by priority <br>
 * @author caueguerra
 *
 */
public class StoriesMustBeOrderedByPriorityStory extends DefaultStory {
	@Test
	public void storiesMustBeOrderedOutOfIterations() throws Exception {
		given.thereIsAnUserNamed("caue").and()
			.thereIsAProjectNamed("htmlunit")
				.ownedBy("caue")
				.withACardNamed("step1")
					.whichDescriptionIs("this is just step 1")
					.withPriority(3).and()
				.withACardNamed("step2")
					.whichDescriptionIs("step 2 duh")
					.withPriority(1).and()
			.iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("htmlunit").and()
		    .iOpenStoriesPage();
		then.theCard("step1")
				.appearsOnCardsListAtPosition(2)
			.theCard("step2")
				.appearsOnCardsListAtPosition(1);
	}

	@Test
	public void storiesMustBeOrderedInIterations() throws Exception {
		given.thereIsAnUserNamed("caue").and()
			.thereIsAProjectNamed("htmlunit")
				.ownedBy("caue")
				.withAnIterationWhichGoalIs("make it works")
					.withACardNamed("step1")
						.whichDescriptionIs("this is just step 1")
						.withPriority(3).and()
					.withACardNamed("step2")
						.whichDescriptionIs("step 2 duh")
						.withPriority(1).also()
				.withACardNamed("step3")
					.whichDescriptionIs("step 3 duh")
					.withPriority(5).and()
				.withACardNamed("step4")
					.whichDescriptionIs("step 4 duh")
					.withPriority(2).and()
			.iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("htmlunit").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("make it works");
		then.theCard("step1").appearsOnCardsListAtPosition(2)
			.theCard("step2").appearsOnCardsListAtPosition(1)
			.theCard("step3").appearsOnBacklogListAtPosition(2)
			.theCard("step4").appearsOnBacklogListAtPosition(1);

	}
}
