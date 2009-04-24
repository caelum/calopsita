package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> have a development team <br>
 * <b>As a</b> Developer leader <br>
 * <b>I want to</b> add users on a project <br>
 * @author lucascs
 *
 */
public class AddUserOnAProjectStory extends DefaultStory {

	@Test
	public void openProjectPage() throws Exception {
		given.thereIsAnUserNamed("lucas");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("lucas");
		given.iAmLoggedInAs("lucas");
		when.iOpenProjectPageOf("C4lopsita");
		then.project("C4lopsita").appearsOnScreen();
	}
	@Test
	public void showColaborators() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci");
		given.iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("C4lopsita");
		then.thisUserAppearsOnColaboratorsList("ceci");
	}
	@Test
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
	public void authorization() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue");
		given.iAmLoggedInAs("ceci");
		when.iDirectlyOpenProjectPageOf("C4lopsita");
		then.iAmNotAllowedToSeeTheProject();
	}
	@Test
	public void authorizationAsColaborator() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci");
		given.iAmLoggedInAs("ceci");
		when.iDirectlyOpenProjectPageOf("C4lopsita");
		then.project("C4lopsita").appearsOnScreen();
	}
	
	@Test
	public void listingProjectsAsColaborator() throws Exception {
		given.thereIsAnUserNamed("ceci");
		given.thereIsAnUserNamed("caue");
		given.thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci");
		given.iAmLoggedInAs("ceci");
		when.iListProjects();
		then.project("C4lopsita").appearsOnList();
	}
}
