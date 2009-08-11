package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> not spend time with already done cards <br>
* <b>As a</b> Nano <br>
* <b>I want</b> to list only to do cards on some listings <br>
 * @author Lucas Cavalcanti
 *
 */
public class DontShowDoneCardsStory extends DefaultStory {

	@Test
	public void dontShowDoneCardsOnCardsListing() {
		given.thereIsAnUserNamed("moreira").and()
			.thereIsAProjectNamed("Brasilia").ownedBy("moreira")
			.withACardNamed("I need to do this").whichDescriptionIs("I haven't done it yet").and()
			.withACardNamed("I've already done").whichDescriptionIs("Nothing more to do")
				.done().and()
			.iAmLoggedInAs("moreira");
		when.iOpenProjectPageOf("Brasilia")
			.iOpenCardsPage();
		then.theCard("I need to do this").appearsOnList().and()
			.theCard("I've already done").notAppearsOnList();
	}
	@Test
	@Ignore
	public void showDoneCardsOnListingAllCards() {
		given.thereIsAnUserNamed("moreira").and()
			.thereIsAProjectNamed("Brasilia").ownedBy("moreira")
			.withACardNamed("I need to do this").whichDescriptionIs("I haven't done it yet").and()
			.withACardNamed("I've already done").whichDescriptionIs("Nothing more to do")
				.done().and()
			.iAmLoggedInAs("moreira");
		when.iOpenProjectPageOf("Brasilia")
			.iOpenCardsPage()
			.iOpenAllCardsPage();
		then.theCard("I need to do this").appearsOnList().and()
			.theCard("I've already done").appearsOnList();
	}
	@Test
	public void dontShowDoneCardsOnPrioritization() {
		given.thereIsAnUserNamed("moreira").and()
				.thereIsAProjectNamed("Brasilia").ownedBy("moreira")
				.withACardNamed("I need to do this")
					.prioritizable()
					.whichDescriptionIs("I haven't done it yet").and()
				.withACardNamed("I've already done")
					.prioritizable()
					.whichDescriptionIs("Nothing more to do")
				.done().and()
			.iAmLoggedInAs("moreira");
		when.iOpenProjectPageOf("Brasilia")
			.iOpenCardsPage().and()
			.iOpenPriorizationPage();
		then.theCard("I need to do this").appearsOnPriority(0).and()
			.theCard("I've already done").notAppearsOnPage();
	}
}
