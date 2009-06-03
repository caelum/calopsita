package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;
import br.com.caelum.calopsita.model.Gadgets;

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
	@Test
	public void editACardIncludingPriorizationGadget() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withACardNamed("support IE8")
					.whichDescriptionIs("Micro$oft must be supported").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
			.iOpenCardsPage().and()
			.iEditTheCard("support IE8")
				.addingGadget(Gadgets.PRIORITIZATION);
		then.theCard("support IE8").isPrioritizable();
	}
	@Test
	public void editACardExcludingPriorizationGadget() {
		given.thereIsAnUserNamed("sergio").and()
			.thereIsAProjectNamed("IEs4Linux")
				.ownedBy("sergio")
				.withACardNamed("support IE8")
					.prioritizable()
					.whichDescriptionIs("Micro$oft must be supported").and()
			.iAmLoggedInAs("sergio");
		when.iOpenProjectPageOf("IEs4Linux").and()
			.iOpenCardsPage().and()
			.iEditTheCard("support IE8")
			.removingGadget(Gadgets.PRIORITIZATION);
		then.theCard("support IE8").isNotPrioritizable();
	}

}
