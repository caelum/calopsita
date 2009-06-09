package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
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
	@Ignore
	public void savingAPrioritizationAutomatically() {
		given.thereIsAnUserNamed("Pedro").and()
			.thereIsAProjectNamed("Instant Silvio")
				.withACardNamed("Record Silvio's voice")
					.whichDescriptionIs("Laugh as Silvio")
					.prioritizable().and()
				.withACardNamed("Deploy website")
					.whichDescriptionIs("I need a white page with Silvio's face")
					.prioritizable().and()
			.iAmLoggedInAs("Pedro");
		when.iOpenProjectPageOf("Instant Silvio").and()
			.iOpenCardsPage().and()
			.iOpenPriorizationPage().and()
			.iLowerPriorityOf("Deploy website").and()
			.iLowerPriorityOf("Record Silvio's voice").and()
			.iRefreshCurrentPage();
		then.theCard("Deploy website").appearsOnPriority(1).and()
			.theCard("Record Silvio's voice").appearsOnPriority(2);
	}
}
