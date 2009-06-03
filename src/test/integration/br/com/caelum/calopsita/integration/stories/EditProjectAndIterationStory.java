package br.com.caelum.calopsita.integration.stories;

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
	public void editIteration() {
		given.thereIsAnUserNamed("ferreira").and()
			.thereIsAProjectNamed("scala").ownedBy("ferreira")
				.withAnIterationWhichGoalIs("support DSLs")
					.starting(today())
					.ending(tomorrow()).and()
			.iAmLoggedInAs("ferreira");

		when.iOpenProjectPageOf("scala").and()
		    .iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("support DSLs").and()
			.iEditTheIteration().withGoal("support Continuations")
				.withStartDate("10/10/2010").withEndDate("11/11/2010").and()
			.iOpenIterationsPage().and()
			.iOpenThePageOfIterationWithGoal("support Continuations");
		then.theIteration("support Continuations").startsAt("10/10/2010").and().endsAt("11/11/2010");
	}


	@Test
	public void editProjectsDescription() {
		given.thereIsAnUserNamed("matiello").and()
			.thereIsAProjectNamed("python Graphs").ownedBy("matiello")
				.whichDescriptionIs("A graph library").and()
			.iAmLoggedInAs("matiello");
		when.iOpenProjectPageOf("python Graphs").and()
		    .iOpenAdminPage().and()
			.iChangeDescriptionTo("A graph library written in python");
		then.project("python").hasDescription("A graph library written in python");
	}
}
