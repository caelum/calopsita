package br.com.caelum.calopsita.integration.stories;

import org.junit.Ignore;
import org.junit.Test;

import br.com.caelum.calopsita.integration.stories.common.DefaultStory;

/**
 * <b>In order to</b> be able to make a mistake with no fear <br>
 * <b>As</b> Fabs<br>
 * <b>I want to</b> delete stories, iterations and projects that I have created<br>
 * 
 */
public class DeleteEverythingStory extends DefaultStory {
    @Test
    @Ignore
    public void deleteIteration() throws Exception {
        given.thereIsAnUserNamed("kung").and()
        .thereIsAProjectNamed("Vraptor 3")
            .ownedBy("kung")
            .withAnIterationWhichGoalIs("make it work")
            .withAStoryNamed("support Vraptor 2")
                .whichDescriptionIs("some stuff should be backward compatible")
                .insideThisIteration()
            .withAnIterationWhichGoalIs("i18n").and()
        .iAmLoggedInAs("kung");
        when.iOpenProjectPageOf("Vraptor 3").and()
            .iDeleteTheIterationWithGoal("make it work");
        then.theIteration("make it work").notAppearsOnList();
        when.iOpenThePageOfIterationWithGoal("i18n");
        then.theStory("support Vraptor 2")
            .appearsOnBacklog();
    }

	@Test
	@Ignore
	public void deleteAStory() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("method-finder").ownedBy("fabs")
				.withAStoryNamed("Support everything").whichDescriptionIs("That is a mistake");
		when.iOpenProjectPageOf("method-finder").and()
			.iDeleteTheStory("Support everything");
		then.theStory("Support everything").shouldNotAppearOnStoryList();
	}
}
