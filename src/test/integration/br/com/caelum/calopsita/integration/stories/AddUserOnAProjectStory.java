package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

/**
 * In order to have a development team
 * As a Developer leader
 * I want to add users on a project
 * @author lucascs
 *
 */
public class AddUserOnAProjectStory extends DefaultStory {

	@Test
	@Ignore
	public void addingColaborators() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue");
		given.iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("C4lopsita");
		when.iAdd("ceci").asColaborator();
		then.thisUserAppearsOnColaboratorsList("ceci");
	}
	@Test
	@Ignore
	public void authorization() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue");
		given.iAmLoggedInAs("ceci");
		when.iOpenProjectPageOf("C4lopsita");
		then.iShouldSeeTheError("not allowed to see the project");
	}
	
}
