package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> plan my team's work <br>
 * <b>As a</b> client <br>
 * <b>I want</b> remove cards from an existing iteration <br>
 * @author lucascs
 *
 */
public class RemoveCardOfAnIterationStory extends DefaultStory {

	@Test
	public void removeACardOfAnIteration() {
		given.thereIsAnUserNamed("harry").and()
			.thereIsAProjectNamed("vim4dummies")
				.ownedBy("harry")
				.withAnIterationWhichGoalIs("kill all emacsians")
					.withACardNamed("buy a weapon of mass destruction")
						.planningCard()
						.whichDescriptionIs("This way we'll kill'em all").and()
			.iAmLoggedInAs("harry");
		when.iOpenProjectPageOf("vim4dummies").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("kill all emacsians").and()
			.iRemoveTheCard("buy a weapon of mass destruction").ofThisIteration();
		then.theCard("buy a weapon of mass destruction").appearsOnBacklog();
	}
}
