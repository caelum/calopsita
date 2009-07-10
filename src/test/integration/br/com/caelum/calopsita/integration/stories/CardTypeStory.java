package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;
import br.com.caelum.calopsita.model.Gadgets;

/**
 * <b>In order to</b> spend less time adding cards <br>
 * <b>As a</b> Olivia and Morelli <br>
 * <b>I want</b> to create predefined card types on admin page of the project,
 * 	choosing a name and a list of gadgets for this type. <br>
 * 	At card creation, I should choose the card type and respective gadgets will
 * 	be checked <br>
 * @author lucascs
 *
 */
public class CardTypeStory extends DefaultStory {


	@Test
	public void createACardType() {
		given.thereIsAnUserNamed("kung").and()
			.thereIsAProjectNamed("CuKung'er").ownedBy("kung").and()
			.iAmLoggedInAs("kung");
		when.iOpenProjectPageOf("CuKung'er").and()
			.iOpenAdminPage().and()
			.iAddTheCardType("Story");
		then.theCardType("Story").appearsOnList();
	}

	@Test
	@Ignore
	public void createACardOfAGivenType() {
		given.thereIsAnUserNamed("kung").and()
			.thereIsAProjectNamed("CuKung'er").ownedBy("kung")
				.withACardTypeNamed("Story")
					.withGadgets(Gadgets.PLANNING).and()
			.iAmLoggedInAs("kung");
		when.iOpenProjectPageOf("CuKung'er").and()
			.iAddTheCard("Describe something")
				.withType("Story")
				.withDescription("I want to describe stuff");
		then.theCard("Describe something")
				.isNotPrioritizable().and()
				.isPlannable();

	}

}
