package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

/**
 * <b>In order to</b> define what is more important <br /> 
 * <b>As a</b> project client <br /> 
 * <b>I want to</b> be able to prioritize stories using an integer number <br />
 * @author ceci
 */
public class PrioritizeStoriesStory extends DefaultStory{

	@Test
	public void prioritizeStoriesWithDifferentPriorities() throws Exception {
		given.thereIsAnUserNamed("Doni").and()
			.thereIsAProjectNamed("Mirror").ownedBy("Doni")
				.withAStoryNamed("Remove all annotations")
					.whichDescriptionIs("annotations are useless").and()
				.withAStoryNamed("Remove all generics")
					.whichDescriptionIs("we want obfuscated code").and()
			.iAmLoggedInAs("Doni");
		when.iOpenProjectPageOf("Mirror").and()
			.iOpenPriorizationPage().and()
			.iLowerPriorityOf("Remove all annotations").and()
			.iSaveThePriorization();
		then.theStory("Remove all generics").appearsOnStoriesListAtPosition(1).and()
			.theStory("Remove all annotations").appearsOnStoriesListAtPosition(2);
	}
	
}
