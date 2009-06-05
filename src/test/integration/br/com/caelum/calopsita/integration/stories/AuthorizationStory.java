package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> keep my system secure <br>
 * <b>As </b> Morelli <br>
 * <b>I want to</b> assure that only the project members can see or modify the project <br>
 * @author lucascs
 *
 */
public class AuthorizationStory extends DefaultStory {

	@Test
	public void authorization() throws Exception {
		given.thereIsAnUserNamed("ceci").and()
			 .thereIsAnUserNamed("caue").and()
			 .thereIsAProjectNamed("C4lopsita").ownedBy("caue").and()
			 .iAmLoggedInAs("ceci");
		when.iDirectlyOpenProjectPageOf("C4lopsita");
		then.iAmNotAllowedToSeeTheProject();
	}
	@Test
	public void authorizationAsColaborator() throws Exception {
		given.thereIsAnUserNamed("ceci").and()
		     .thereIsAnUserNamed("caue").and()
		     .thereIsAProjectNamed("C4lopsita").ownedBy("caue").withColaborator("ceci").and()
		     .iAmLoggedInAs("ceci");
		when.iDirectlyOpenProjectPageOf("C4lopsita");
		then.project("C4lopsita").appearsOnScreen();
	}

	@Test
	public void authorizationOfCardPages() {
		given.thereIsAnUserNamed("ceci").and()
			.thereIsAnUserNamed("lucas").and()
			.thereIsAProjectNamed("C4lopsita").ownedBy("lucas")
				.withACardNamed("Authorization")
					.whichDescriptionIs("Security is important").and()
			.iAmLoggedInAs("ceci");

		when.iDirectlyOpenCardPageOf("Authorization");
		then.iAmNotAllowedToSeeTheProject();
	}

	@Test
	public void authorizationOfIterationPages() {
		given.thereIsAnUserNamed("ceci").and()
			.thereIsAnUserNamed("lucas").and()
			.thereIsAProjectNamed("C4lopsita").ownedBy("lucas")
				.withAnIterationWhichGoalIs("Authorize Everyone")
			.iAmLoggedInAs("ceci");

		when.iDirectlyOpenIterationPageOf("Authorize Everyone");
		then.iAmNotAllowedToSeeTheProject();
	}

}
