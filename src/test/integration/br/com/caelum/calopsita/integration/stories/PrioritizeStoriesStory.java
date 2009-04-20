package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * <b>In order to</b> define what is more important <br /> 
 * <b>As a</b> project client <br /> 
 * <b>I want to</b> be able to prioritize stories using an integer number <br />
 * @author ceci
 */
public class PrioritizeStoriesStory extends DefaultStory{

	@Test
	@Ignore
	public void prioritizeStoriesWithDifferentPriorities() throws Exception {
		given.thereIsAnUserNamed("Doni").and()
			.thereIsAProjectNamed("Mirror").ownedBy("Doni")
				.withAStoryNamed("Remove all annotations").and()
				.withAStoryNamed("Remove all generics").and()
			.iAmLoggedInAs("Doni");
		when.iOpenProjectPageOf("Mirror").and()
			.iOpenPriorizationPage().and()
			.iDrag("Remove all generics", toTheTopPosition()).and()
			.iSaveThePriorization();
		then.theStory("Remove all generics").appearsOnStoriesListAtPosition(0).and()
			.theStory("Remove all annotations").appearsOnStoriesListAtPosition(1);
	}
	
	private int toTheTopPosition() {
		return 1;
	}
}
