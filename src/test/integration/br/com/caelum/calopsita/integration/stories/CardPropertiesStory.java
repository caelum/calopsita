package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> make cards with different properties <br>
 * <b>As</b> Olivia or Morelli <br>
 * <b>I want</b> to choose with checkboxes what properties a card has,
 * 		either on addition or on edition. <br>
 *
 * @author lucascs
 */
public class CardPropertiesStory extends DefaultStory {

	@Test
	public void addAPrioritizableCard() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
		    .iOpenCardsPage().and()
			.iAddTheCard("support IE8")
				.prioritizable()
				.withDescription("Micro$oft must be supported");
		then.theCard("support IE8").isPrioritizable();
	}

}
