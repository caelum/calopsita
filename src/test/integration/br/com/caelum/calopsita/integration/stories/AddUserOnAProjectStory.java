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
		given.thereIsAnUserNamed("lucas").and()
		     .thereIsAProjectNamed("C4lopsita").ownedBy("lucas").and()
		     .iAmLoggedInAs("lucas");
		when.iOpenProjectPageOf("C4lopsita").and()
		    .iOpenAdminPage();
		then.thisUserAppearsOnColaboratorsList("lucas");
	}
	@Test
	public void showColaborators() throws Exception {
		given.thereIsAnUserNamed("ceci").and()
		     .thereIsAnUserNamed("caue").and()
		     .thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci").and()
		     .iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("C4lopsita").and()
            .iOpenAdminPage();
		then.thisUserAppearsOnColaboratorsList("ceci");
	}
	@Test
	public void addingColaborators() throws Exception {
		given.thereIsAnUserNamed("ceci").and()
		     .thereIsAnUserNamed("caue").and()
		     .thereIsAProjectNamed("C4lopsita").ownedBy("caue").and()
		     .iAmLoggedInAs("caue");
		when.iOpenProjectPageOf("C4lopsita").and()
		    .iOpenAdminPage().and()
		    .iAdd("ceci").asColaborator();
		then.thisUserAppearsOnColaboratorsList("ceci");
	}

	@Test
	public void listingProjectsAsColaborator() throws Exception {
		given.thereIsAnUserNamed("ceci").and()
		     .thereIsAnUserNamed("caue").and()
		     .thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci").and()
		     .iAmLoggedInAs("ceci");
		when.iListProjects();
		then.project("C4lopsita").appearsOnList();
	}
}
