package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

public class AddUserOnAProjectStory extends DefaultStory {

	@Test
	@Ignore
	public void addingColaborators() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue");
		given.iAmLoggedInAs("caue");
		
	}
	
}
