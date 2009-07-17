package br.com.caelum.calopsita.integration.stories;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> make my client tell me why and what he wants something
 * done <br>
 * <b>As a</b> Vinicius and Alexandre <br>
 * <b>I want to</b> create templates for a certain type of cards, such as
 * "In order to", "As" and <br>
 * "I want to". After that, all the created fields should be required to write a
 * new card of that type.
 * 
 * @author ceci
 */
public class CardTemplatesStory extends DefaultStory {

	@Test
	public void createACardTemplate() throws Exception {
		given.thereIsAnUserNamed("leo").and()
			 .thereIsAProjectNamed("ospresente").ownedBy("leo").and()
			 .iAmLoggedInAs("leo");
		when.iOpenProjectPageOf("ospresente").and()
			.iOpenAdminPage().and()
			.iAddTheCardType("Story")
				.withTemplate("In order to", "As", "I want to").and()
			.iAddTheCard("Appear on profile").withType("Story");
		then.theFormShouldContainFields("In order to", "As", "I want to");
	}
}
