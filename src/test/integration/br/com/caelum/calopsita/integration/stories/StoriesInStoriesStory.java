package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> organize my work/project in a better way <br>
 * <b>As a</b> Nano or Olivia <br>
 * <b>I want</b> to create stories in another stories <br>
 * @author lucascs
 *
 */
public class StoriesInStoriesStory extends DefaultStory {
	
	@Test
	public void addAStoryInAnotherStory() throws Exception {
		given.thereIsAnUserNamed("lipe").and()
			.thereIsAProjectNamed("tattoos")
				.ownedBy("lipe")
				.withACardNamed("left arm tatoo")
					.whichDescriptionIs("I want my arm full of tatoos").and()
			.iAmLoggedInAs("lipe");
		when.iOpenProjectPageOf("tattoos").and()
		    .iOpenStoriesPage()
			.iOpenThePageOfStoryNamed("left arm tatoo").and()
			.iAddTheSubstory("draw some notes").withDescription("tatoo some musical notes, and cleffs");
		then.theStory("draw some notes").appearsOnList();
		
	}
}
