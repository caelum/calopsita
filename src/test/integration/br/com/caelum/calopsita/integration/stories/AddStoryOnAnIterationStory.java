package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * <b>In order to</b> organize my wok <br>
* <b>As a</b> project owner <br>
* <b>I want</b> to put existent stories in an iteration <br>
 * @author lucascs
 *
 */
public class AddStoryOnAnIterationStory extends DefaultStory {
	
	@Test
	@Ignore
	public void addAStoryInAnIteration() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withAnIterationWhichGoalIs("new release")
				.withAStoryNamed("support IE8").whichDescriptionIs("IE8 must be supported").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
			.iOpenThePageOfIterationWithGoal("new release").and()
			.iAddTheStory("support IE8");
		then.theStory("support IE8").appearsOnList();
	}
}
