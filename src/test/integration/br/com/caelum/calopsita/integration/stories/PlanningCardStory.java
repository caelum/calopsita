package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 *<b>In order to</b> make planning easier <br>
* <b>As </b> Morelli <br>
* <b>I want</b> to mark a card as planning card.
* 	Only planning cards appears on Backlog at iteration
* 	planning page. <br>
 * @author lucascs
 *
 */
public class PlanningCardStory extends DefaultStory {

	@Test
	public void onlyPlanningCardsAppearsOnBacklog() {
		given.thereIsAnUserNamed("adriano").and()
			.thereIsAProjectNamed("Marriage").ownedBy("adriano")
			.withACardNamed("schedule date")
				.whichDescriptionIs("we need a date for marriage")
				.planningCard().and()
			.withACardNamed("get fat")
				.whichDescriptionIs("All married people get fat").and()
			.withAnIterationWhichGoalIs("Postpone").and()
			.iAmLoggedInAs("adriano");
		when.iOpenProjectPageOf("Marriage").and()
			.iOpenThePageOfIterationWithGoal("Postpone");
		then.theCard("schedule date").appearsOnBacklog().and()
			.theCard("get fat").notAppearsOnPage();
	}
	@Test
	public void addingAPlanningCard() {
		given.thereIsAnUserNamed("adriano").and()
			.thereIsAProjectNamed("Marriage").ownedBy("adriano")
			.withAnIterationWhichGoalIs("Postpone").and()
			.iAmLoggedInAs("adriano");
		when.iOpenProjectPageOf("Marriage").and()
			.iOpenCardsPage().and()
			.iAddTheCard("schedule date")
				.planningCard()
				.withDescription("we need a date for marriage").and()
			.iOpenThePageOfIterationWithGoal("Postpone");
		then.theCard("schedule date").appearsOnBacklog();
	}
}
