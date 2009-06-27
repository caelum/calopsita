package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> divide the project's work in groups <br>
 * <b>As a</b> developer <br>
 * <b>I want to</b> create iterations <br>
 *
 */
public class CreateAnIterationStory extends DefaultStory {
    @Test
    public void iterationCreation() throws Exception {
        given.thereIsAnUserNamed("David").and()
             .thereIsAProjectNamed("Papyrus")
                 .ownedBy("David").and()
             .iAmLoggedInAs("David");

        when.iOpenProjectPageOf("Papyrus").and()
            .iOpenIterationsPage().and()
            .iAddTheIteration("create and update an incident")
                .withStartDate(today())
                .withEndDate(tomorrow());

        then.theIteration("create and update an incident").appearsOnList();
    }
}
