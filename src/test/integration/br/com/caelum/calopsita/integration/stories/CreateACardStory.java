package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> plan what has to be done <br>
 * <b>As a</b> project client <br>
 * <b>I want to</b> create and edit stories (with name and description) <br>
 * 
 */
public class CreateACardStory extends DefaultStory {

	@Test
	public void storyCreation() throws Exception {
		given.thereIsAnUserNamed("David").and()
			.thereIsAProjectNamed("Papyrus").ownedBy("David").and()
			.iAmLoggedInAs("David");
		
		when.iOpenProjectPageOf("Papyrus").and()
		    .iOpenCardsPage().and()
			.iAddTheCard("Incidents").withDescription("create and update an incident");
		
		then.theCard("Incidents").appearsOnList();
	}
	
	@Test
	public void storyUpdate() throws Exception {
		given.thereIsAnUserNamed("Sonson").and()
		     .thereIsAProjectNamed("OpenMeetings")
			     .ownedBy("Sonson")
			     .withACardNamed("Cinderella")
			     .whichDescriptionIs("She loses her shoe.").and()
			 .iAmLoggedInAs("Sonson");
		
		when.iOpenProjectPageOf("OpenMeetings").and()
		    .iOpenCardsPage().and()
		    .iEditTheCard("Cinderella").changingDescriptionTo("Her sisters go blind.");
		
		then.theCard("Cinderella").hasDescription("Her sisters go blind.");
	}
}
