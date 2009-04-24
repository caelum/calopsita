package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> plan my team's work <br>
 * <b>As a</b> client <br>
 * <b>I want</b> remove stories from an existing iteration <br>
 * @author lucascs
 *
 */
public class RemoveStoryOfAnIterationStory extends DefaultStory {
	
	@Test
	public void removeAStoryOfAnIteration() {
		given.thereIsAnUserNamed("harry").and()
			.thereIsAProjectNamed("vim4dummies")
				.ownedBy("harry")
				.withAnIterationWhichGoalIs("kill all emacsians")
				.withAStoryNamed("buy a weapon of mass destruction")
					.whichDescriptionIs("This way we'll kill'em all")
					.insideThisIteration().and()
			.iAmLoggedInAs("harry");
		when.iOpenProjectPageOf("vim4dummies").and()
			.iOpenThePageOfIterationWithGoal("kill all emacsians").and()
			.iRemoveTheStory("buy a weapon of mass destruction").ofThisIteration();
		then.theStory("buy a weapon of mass destruction").appearsOnBacklog();
	}
}
