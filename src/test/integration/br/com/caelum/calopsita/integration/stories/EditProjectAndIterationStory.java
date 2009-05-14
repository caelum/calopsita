package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> make mistakes with no fear <br>
 * <b>As a</b> Fabs <br>
 * <b>I want to</b> edit a project or iteration <br>
 * 
 */
public class EditProjectAndIterationStory extends DefaultStory {

	@Test
	@Ignore
	public void editIteration() {
		given.thereIsAnUserNamed("ferreira").and()
			.thereIsAProjectNamed("scala").ownedBy("ferreira")
				.withAnIterationWhichGoalIs("support DSLs")
					.startingAt(today())
					.endingAt(tomorrow()).and()
			.iAmLoggedInAs("ferreira");
					
		when.iOpenProjectPageOf("scala").and()
			.iOpenThePageOfIterationWithGoal("support DSLs").and()
			.iEditTheIteration().withGoal("support Continuations")
				.withStartDate("10/10/10").withEndDate("11/11/10");
		then.theIteration("support Continuations").startsAt("10/10/10").and().endsAt("11/11/10");
	}
}
