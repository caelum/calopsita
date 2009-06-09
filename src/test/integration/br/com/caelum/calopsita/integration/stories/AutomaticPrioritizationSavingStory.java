package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> keep the priorization I made even when I forget to save it <br>
* <b>As </b> Fabs <br>
* <b>I want to </b> automatically save it while I change it <br>
 * @author lucascs
 *
 */
public class AutomaticPrioritizationSavingStory extends DefaultStory {


	@Test
	public void savingAPrioritizationAutomatically() {
		given.thereIsAnUserNamed("Pedro").and()
			.thereIsAProjectNamed("Instant Silvio")
				.ownedBy("Pedro")
				.withACardNamed("Record Silvios voice")
					.withPriority(3)
					.whichDescriptionIs("Laugh as Silvio").and()
			.iAmLoggedInAs("Pedro");
		when.iOpenProjectPageOf("Instant Silvio").and()
			.iOpenCardsPage().and()
			.iOpenPriorizationPage().and()
			.iChangePriorityOf("Record Silvios voice", to(1))
			.iRefreshCurrentPage();
		then.theCard("Record Silvios voice").appearsOnPriority(1);
	}
	@Test
	public void undoingAPrioritization() {
		given.thereIsAnUserNamed("Pedro").and()
			.thereIsAProjectNamed("Instant Silvio")
				.ownedBy("Pedro")
				.withACardNamed("Record Silvios voice")
					.withPriority(3)
					.whichDescriptionIs("Laugh as Silvio").and()
			.iAmLoggedInAs("Pedro");
		when.iOpenProjectPageOf("Instant Silvio").and()
			.iOpenCardsPage().and()
			.iOpenPriorizationPage().and()
			.iChangePriorityOf("Record Silvios voice", to(2)).and()
			.iUndoPriority();
		then.theCard("Record Silvios voice").appearsOnPriority(3);
	}
}
