package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

public class StoriesMustBeOrderedByPriorityStory extends DefaultStory {
	@Test
	public void storiesMustBeOrderedInBacklog() throws Exception {
		given.thereIsAnUserNamed("caue").and()
			.thereIsAProjectNamed("htmlunit")
				.ownedBy("caue")
				.withAStoryNamed("step1")
					.whichDescriptionIs("this is just step 1")
					.withPriority(3)
				.withAStoryNamed("step2")
					.whichDescriptionIs("step 2 duh").and()
			.iAmLoggedInAs("caue");
	}
	
	@Test
	public void storiesMustBeOrderedInIterations() throws Exception {
		given.thereIsAnUserNamed("caue").and()
			.thereIsAProjectNamed("htmlunit")
				.ownedBy("caue")
				.withAnIterationWhichGoalIs("make it works")
				.withAStoryNamed("step1")
					.whichDescriptionIs("this is just step 1")
					.withPriority(3)
					.insideIterationWithGoal("make it works")
				.withAStoryNamed("step2")
					.whichDescriptionIs("step 2 duh")
					.withPriority(1)
					.insideIterationWithGoal("make it works").and()
			.iAmLoggedInAs("caue");
	}
}
