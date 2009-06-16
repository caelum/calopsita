package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to </b> keep focused while working on current iteration <br>
 * <b>As a</b> Nano <br>
 * <b>I want to </b> see only cards from current iteration on current iteration page <br>
 * @author lucascs
 *
 */
public class WorkModeOnCurrentIterationStory extends DefaultStory {


	@Test
	public void currentIterationPageOnlyShowsIterationCards() {
		given.thereIsAnUserNamed("doni").and()
			.thereIsAProjectNamed("Mirror").ownedBy("doni")
			.withACardNamed("Support .Net")
				.whichDescriptionIs("We will never do it").and()
			.withAnIterationWhichGoalIs("Deploy on sf")
				.startingYesterday()
				.ending(tomorrow())
				.withACardNamed("Remove TODOs")
					.whichDescriptionIs("clean the project").and()
			.iAmLoggedInAs("doni");
		when.iOpenProjectPageOf("Mirror");
		then.theCard("Remove TODOs").appearsOnTodoList().and()
			.theCard("Support .Net").notAppearsOnPage();
	}
	@Test
	public void iterationPageShowsIterationCardsAndBackLogCards() {
		given.thereIsAnUserNamed("doni").and()
			.thereIsAProjectNamed("Mirror").ownedBy("doni")
			.withACardNamed("Support .Net")
				.whichDescriptionIs("We will never do it").and()
			.withAnIterationWhichGoalIs("Deploy on sf")
				.withACardNamed("Remove TODOs")
					.whichDescriptionIs("clean the project").and()
			.iAmLoggedInAs("doni");
		when.iOpenProjectPageOf("Mirror").and()
			.iOpenThePageOfIterationWithGoal("Deploy on sf");
		then.theCard("Remove TODOs").appearsOnList("iteration_cards").and()
			.theCard("Support .Net").appearsOnBacklog();
	}
}
