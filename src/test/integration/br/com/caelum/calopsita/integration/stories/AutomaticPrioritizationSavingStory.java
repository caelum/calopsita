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
					.prioritizable()
					.whichDescriptionIs("Laugh as Silvio").and()
			.iAmLoggedInAs("Pedro");
		when.iOpenProjectPageOf("Instant Silvio").and()
			.iOpenCardsPage().and()
			.iOpenPriorizationPage().and()
			.iLowerPriorityOf("Record Silvios voice").and()
			.iRefreshCurrentPage();
		then.theCard("Record Silvios voice").appearsOnPriority(1);
	}
}
