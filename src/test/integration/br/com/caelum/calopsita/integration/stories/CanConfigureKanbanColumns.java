package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> create a kanban visualization <br>
 * <b>As a</b> developer <br>
 * <b>I want to</b> create and edit kanban columns <br>
 */
public class CanConfigureKanbanColumns extends DefaultStory {

	@Test
	public void canDefineProjectsKanbanColumns() throws Exception {
		given.thereIsAnUserNamed("Atoji").and()
			.thereIsAProjectNamed("MyInstants").ownedBy("Atoji").and()
			.iAmLoggedInAs("Atoji");

		when.iOpenProjectPageOf("MyInstants").and()
			.iOpenKanbanConfigurationPage().and()
			.addAKanbanColumnNamed("Integration");
		
		then.theKanbanColumn("Integration").appearsOnList();
	}
}
