package br.com.caelum.calopsita.integration.stories.common;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.ContentTag;

public class ThenAsserts {

    private final Browser browser;
    private String name;
	private String divName;

    public ThenAsserts(Browser browser) {
        this.browser = browser;
    }

    public void iMustBeLoggedInAs(String login) {
        ContentTag div = this.browser.currentPage().div("user");
        assertThat(div, divContainsString(login));
        assertThat(div, divContainsString("Logout"));
    }

    public void iShouldSeeTheError(String error) {
        assertThat(browser.currentPage().div("errors"), divContainsString(error));
    }

    public void iMustNotBeLoggedIn() {
        assertThat(this.browser.currentPage().div("login"), divContainsString("Login"));
    }

    public void iAmBackToLoginPage() {
        ContentTag div = this.browser.currentPage().div("login");
        assertThat(div, divContainsString("Login"));
        assertThat(div, divContainsString("Password"));
    }

    public ThenAsserts project(String projectName) {
        this.name = projectName;
        this.divName = "projects";
        return this;
    }

    public ThenAsserts appearsOnList() {
        assertThat(this.browser.currentPage().div(divName), divContainsString(name));
        return this;
    }
    
    public void appearsOnTodoList() {
    	this.divName = "todo_stories";
    	appearsOnList();
    }

	public void thisUserAppearsOnColaboratorsList(String userName) {
		assertThat(this.browser.currentPage().div("colaborators"), divContainsString(userName));
	}

    public void notAppearsOnList() {
        assertThat(this.browser.currentPage().div(divName), not(divContainsString(name)));
    }

	public void iAmNotAllowedToSeeTheProject() {
		assertThat(this.browser.currentPage().div("index"), divContainsString("not allowed to see this project"));
	}

	public ThenAsserts appearsOnScreen() {
		assertThat(browser.currentPage().div("project"), divContainsString(name));
		return this;
	}
	

	@Factory
	public static Matcher<ContentTag> divContainsString(final String string) {
		return new TypeSafeMatcher<ContentTag>() {
			private String innerHTML;
			@Override
			public boolean matchesSafely(ContentTag item) {
				innerHTML = item.innerHTML();
				return innerHTML.contains(string);
			}
			@Override
			public void describeTo(Description description) {
				description
					.appendText("a div containing '")
					.appendText(string)
					.appendText("'");
			}
		};
	}

	public ThenAsserts theStory(String storyName) {
		this.name = storyName;
		this.divName = "stories";
		return this;
	}

	public void hasDescription(String storyDescription) {
		assertThat(browser.currentPage().div("stories"), divContainsString(storyDescription));
	}

	public ThenAsserts theIteration(String iterationGoal) {
        this.name = iterationGoal;
        this.divName = "iterations";
        return this;
    }

	public void iMustBeInMyMainPage() {
		assertThat(browser.currentPage().title(), containsString("Project"));
	}
	
	public ThenAsserts appearsOnStoriesListAtPosition(int i) {
		assertThat(browser.currentPage().div("stories_"+i), divContainsString(name));
		return this;
	}
	
	public ThenAsserts appearsOnOtherStoriesListAtPosition(int i) {
		assertThat(browser.currentPage().div("backlog_"+i), divContainsString(name));
		return this;
	}

	public ThenAsserts appearsOnBacklog() {
		this.divName = "backlog";
		appearsOnList();
		return this;
	}

	public ThenAsserts and() {
		return this;
	}

	public void appearsAsDone() {
		assertThat(browser.currentPage().div("done_stories"), divContainsString(name));
	}

	public ThenAsserts notAppearsOnBacklog() {
		assertThat(this.browser.currentPage().div("backlog"), not(divContainsString(name)));
		return this;
	}

	public void appearsOnList(String divName) {
		this.divName = divName;
		appearsOnList();
	}

	public ThenAsserts shouldNotAppearOnStoryList() {
		assertThat(this.browser.currentPage().div("stories"), not(divContainsString(name)));
		return this;
	}

	public void deletionLinkDoesnotAppearForProject(String projectName) {
		String result = this.browser.currentPage().invoke("$('[name=\"delete " + projectName + "\"]').length");
		assertThat("Deletion link appears when it shouldnt", result, is("0.0"));
	}

	public void theCurrentIterationIs(String iterationGoal) {
		assertThat(this.browser.currentPage().div("current"), divContainsString(iterationGoal));
	}
}
