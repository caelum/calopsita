package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> define what is more important <br />
 * <b>As a</b> project client <br />
 * <b>I want to</b> be able to prioritize cards using an integer number <br />
 *
 * @author ceci
 */
public class PrioritizeCardsStory extends DefaultStory{

	@Test
	public void prioritizeCardsWithDifferentPriorities() throws Exception {
		given.thereIsAnUserNamed("Doni").and()
			.thereIsAProjectNamed("Mirror").ownedBy("Doni")
				.withACardNamed("Remove all annotations")
					.whichDescriptionIs("annotations are useless").and()
				.withACardNamed("Remove all generics")
					.whichDescriptionIs("we want obfuscated code").and()
			.iAmLoggedInAs("Doni");
		when.iOpenProjectPageOf("Mirror").and()
		    .iOpenCardsPage().and()
			.iOpenPriorizationPage().and()
			.iLowerPriorityOf("Remove all annotations").and()
			.iSaveThePriorization();
		then.theCard("Remove all generics").appearsOnCardsListAtPosition(1).and()
			.theCard("Remove all annotations").appearsOnCardsListAtPosition(2);
	}

}
