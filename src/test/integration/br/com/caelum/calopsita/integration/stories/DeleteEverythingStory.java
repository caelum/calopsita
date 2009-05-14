package br.com.caelum.calopsita.integration.stories;

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
    public void deleteAnIterationAndConfirm() throws Exception {
        given.thereIsAnUserNamed("kung").and()
        .thereIsAProjectNamed("Vraptor 3").ownedBy("kung")
            .withAnIterationWhichGoalIs("make it work")
            .withAStoryNamed("support Vraptor 2")
                .whichDescriptionIs("some stuff should be backward compatible")
                .insideThisIteration()
            .withAnIterationWhichGoalIs("i18n").and()
        .iAmLoggedInAs("kung");
        when.iOpenProjectPageOf("Vraptor 3").and()
            .iDeleteTheIterationWithGoal("make it work").andConfirm("deletion");
        then.theIteration("make it work").notAppearsOnList();
        when.iOpenThePageOfIterationWithGoal("i18n");
        then.theStory("support Vraptor 2")
            .appearsOnBacklog();
    }
    
    @Test
    public void deleteAnIterationAndDontConfirm() throws Exception {
        given.thereIsAnUserNamed("kung").and()
        .thereIsAProjectNamed("Vraptor 3").ownedBy("kung")
            .withAnIterationWhichGoalIs("make it work")
            .withAStoryNamed("support Vraptor 2")
                .whichDescriptionIs("some stuff should be backward compatible")
                .insideThisIteration()
            .withAnIterationWhichGoalIs("i18n").and()
        .iAmLoggedInAs("kung");
        when.iOpenProjectPageOf("Vraptor 3").and()
            .iDeleteTheIterationWithGoal("make it work").andDontConfirm("deletion");
        then.theIteration("make it work").appearsOnList();
    }

	@Test
	public void deleteAStoryAndConfirm() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("method-finder").ownedBy("fabs")
				.withAStoryNamed("Support everything").whichDescriptionIs("That is a mistake").ownedBy("fabs").and()
			.iAmLoggedInAs("fabs");
		when.iOpenProjectPageOf("method-finder").and()
			.iDeleteTheStory("Support everything").andConfirm("deletion");
		then.theStory("Support everything").shouldNotAppearOnStoryList();
	}
	@Test
	public void deleteAStoryAndDontConfirm() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("method-finder").ownedBy("fabs")
				.withAStoryNamed("Support everything").whichDescriptionIs("That is a mistake").ownedBy("fabs").and()
			.iAmLoggedInAs("fabs");
		when.iOpenProjectPageOf("method-finder").and()
			.iDeleteTheStory("Support everything").andDontConfirm("deletion");
		then.theStory("Support everything").appearsOnList();
	}
	@Test
	public void deleteAStoryAndSubstories() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("method-finder").ownedBy("fabs")
				.withAStoryNamed("Support everything").whichDescriptionIs("That is a mistake").ownedBy("fabs")
					.withASubstoryNamed("support continuations").whichDescriptionIs("continuations is good").and()
			.iAmLoggedInAs("fabs");
		when.iOpenProjectPageOf("method-finder").and()
			.iDeleteTheStory("Support everything").andConfirm("deletion").andConfirm("substories");
		then.theStory("Support everything").shouldNotAppearOnStoryList().and()
			.theStory("support continuations").shouldNotAppearOnStoryList();
	}
	@Test
	public void deleteAStoryButNotSubstories() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("method-finder").ownedBy("fabs")
				.withAStoryNamed("Support everything").whichDescriptionIs("That is a mistake").ownedBy("fabs")
					.withASubstoryNamed("support continuations").whichDescriptionIs("continuations is good").and()
			.iAmLoggedInAs("fabs");
		when.iOpenProjectPageOf("method-finder").and()
			.iDeleteTheStory("Support everything").andConfirm("deletion").andDontConfirm("substories");
		then.theStory("Support everything").shouldNotAppearOnStoryList().and()
			.theStory("support continuations").appearsOnList();
	}
	
	
	@Test
	public void deleteMyProject() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("goat").ownedBy("fabs")
			.iAmLoggedInAs("fabs");
		when.iDeleteTheProject("goat").andConfirm("deletion");
		then.project("goat").notAppearsOnList();
	}
	@Test
	public void deleteMyProjectButNotConfirm() {
		given.thereIsAnUserNamed("fabs").and()
			.thereIsAProjectNamed("goat").ownedBy("fabs")
			.iAmLoggedInAs("fabs");
		when.iDeleteTheProject("goat").andDontConfirm("deletion");
		then.project("goat").appearsOnList();
	}
}
