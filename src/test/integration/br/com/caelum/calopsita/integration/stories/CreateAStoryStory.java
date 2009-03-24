package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

/**
 * In order to plan what has to be done
 * As a project client
 * I want to create and edit stories (with name and description)
 * @author ceci
 */
public class CreateAStoryStory extends DefaultStory {

	@Test
	public void storyCreation() throws Exception {
		given.thereIsAnUserNamed("David");
		given.thereIsAProjectNamed("Papyrus").ownedBy("David");
		given.iAmLoggedInAs("David");
		
		when.iOpenProjectPageOf("Papyrus");
		when.iAddTheStory("Incidents").withDescription("create and update an incident");
		
		then.theStory("Incidents").appearsOnList();
	}
	
	@Test
	public void storyUpdate() throws Exception {
		given.thereIsAnUserNamed("Sonson");
		given.thereIsAProjectNamed("OpenMeetings")
			 .ownedBy("Sonson")
			 .withStoryNamed("Cinderella")
			 .whichDescriptionIs("She loses her shoe.");
		given.iAmLoggedInAs("Sonson");
		
		when.iOpenProjectPageOf("OpenMeetings");
		when.iEditTheStory("Cinderella").changingDescriptionTo("Her sisters go blind.");
		
		then.theStory("Cinderella").hasDescription("Her sisters go blind.");
	}
}
