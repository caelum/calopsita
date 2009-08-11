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
	@Ignore
	public void dontShowDoneCardsOnCardsListing() {
		given.thereIsAnUserNamed("moreira").and()
			.thereIsAProjectNamed("Brasilia").ownedBy("moreira")
			.withACardNamed("I need to do this").whichDescriptionIs("I haven't done it yet").and()
			.withACardNamed("I've done yet").whichDescriptionIs("Nothing more to do")
				.done().and()
			.iAmLoggedInAs("moreira");
		when.iOpenCardsPage();
		then.theCard("I need to do this").appearsOnList().and()
			.theCard("I've done yet").notAppearsOnList();
	}
	@Test
	@Ignore
	public void dontShowDoneCardsOnPrioritization() {
		given.thereIsAnUserNamed("moreira").and()
				.thereIsAProjectNamed("Brasilia").ownedBy("moreira")
				.withACardNamed("I need to do this").whichDescriptionIs("I haven't done it yet").and()
				.withACardNamed("I've done yet").whichDescriptionIs("Nothing more to do")
				.done().and()
			.iAmLoggedInAs("moreira");
		when.iOpenPriorizationPage();
		then.theCard("I need to do this").appearsOnPriority(0).and()
			.theCard("I've done yet").notAppearsOnPage();
	}
}
