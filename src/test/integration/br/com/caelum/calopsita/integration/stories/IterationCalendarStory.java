package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> easily see how many day are gone and how many days I still have to work on my iteration <br>
 * <b>As a</b> Fabs <br>
 * <b>I want</b> to see a timeline of my iteration, clearly indicating today and the start and end days, when they exist <br>
 * 
 * @author ceci
 */
public class IterationCalendarStory extends DefaultStory {

	@Test
	public void currentIterationWithStartAndEndDays() throws Exception {
		given.thereIsAnUserNamed("Ferreira").and()
			.thereIsAProjectNamed("Hoops").ownedBy("Ferreira")
				.withAnIterationWhichGoalIs("Allow attributes on fields")
				.startingAt(oneWeekAgo()).endingAt(nextWeek()).and()
			.iAmLoggedInAs("Ferreira");
		when.iOpenProjectPageOf("Ferreira").and()
		    .iOpenThePageOfIterationWithGoal("Allow attributes on fields");
		then.theIterationTimeline()
				.showsToday()
				.showsWhenItBegan(oneWeekAgo())
				.showsWhenItEnds(nextWeek());
	}
}
