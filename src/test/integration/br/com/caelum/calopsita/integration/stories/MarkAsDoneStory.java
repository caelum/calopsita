package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> follow the iteration's progress <br>
 * <b>As a</b> client or developer <br>
 * <b>I want</b> to flag a story as done. <br>
 * @author lucascs
 *
 */
public class MarkAsDoneStory extends DefaultStory {

	@Test
	public void flagStoryAsDone() {
		given.thereIsAnUserNamed("kung").and()
			.thereIsAProjectNamed("Vraptor 3")
				.ownedBy("kung")
				.withAnIterationWhichGoalIs("make it work")
					.withAStoryNamed("support Vraptor 2")
						.whichDescriptionIs("some stuff should be backward compatible").and()
			.iAmLoggedInAs("kung");
		when.iOpenProjectPageOf("Vraptor 3").and()
			.iOpenThePageOfIterationWithGoal("make it work").and()
			.iFlagTheStory("support Vraptor 2").asDone();
		then.theStory("support Vraptor 2").appearsAsDone();
	}
}
