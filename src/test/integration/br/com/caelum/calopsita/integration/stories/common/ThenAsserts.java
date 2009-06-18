package br.com.caelum.calopsita.integration.stories.common;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.LocalDate;
import org.junit.Assert;

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

    public ThenAsserts appearsOnTodoList() {
    	this.divName = "todo_cards";
    	appearsOnList();
    	return this;
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
		assertThat(browser.currentPage().div("projects"), divContainsString(name));
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

	public ThenAsserts theCard(String cardName) {
		this.name = cardName;
		this.divName = "cards";
		return this;
	}

	public void hasDescription(String description) {
		assertThat(browser.currentPage().div(divName), divContainsString(description));
	}

	public ThenAsserts theIteration(String iterationGoal) {
        this.name = iterationGoal;
        this.divName = "iterations";
        return this;
    }

	public void iMustBeInMyMainPage() {
		assertThat(browser.currentPage().title(), containsString("Project"));
	}

	public ThenAsserts appearsOnCardsListAtPosition(int i) {
		assertThat(browser.currentPage().div("cards_"+i), divContainsString(name));
		return this;
	}

	public ThenAsserts appearsOnBacklogListAtPosition(int i) {
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
		assertThat(browser.currentPage().div("done_cards"), divContainsString(name));
	}

	public ThenAsserts notAppearsOnBacklog() {
		assertThat(this.browser.currentPage().div("backlog"), not(divContainsString(name)));
		return this;
	}

	public ThenAsserts appearsOnList(String divName) {
		this.divName = divName;
		appearsOnList();
		return this;
	}

	public ThenAsserts shouldNotAppearOnCardList() {
		assertThat(this.browser.currentPage().div("cards"), not(divContainsString(name)));
		return this;
	}

	public void deletionLinkDoesnotAppearForProject(String projectName) {
		String result = this.browser.currentPage().invoke("$('[name=\"delete " + projectName + "\"]').length");
		assertThat("Deletion link appears when it shouldnt", result, is("0.0"));
	}

	public void theCurrentIterationIs(String iterationGoal) {
		assertThat(this.browser.currentPage().div("current"), divContainsString(iterationGoal));
	}

	public void theCurrentIterationEndsToday() {
        assertThat(this.browser.currentPage().div("current"), divContainsString(new LocalDate().toString("yyyy-MM-dd")));
    }
	public ThenAsserts startsAt(String date) {
		assertThat(this.browser.currentPage().div("iteration_text"), divContainsString("Start Date: " + date));
		return this;
	}

	public void endsAt(String date) {
		assertThat(this.browser.currentPage().div("iteration_text"), divContainsString("End Date: " + date));
	}

    public void theIterationThatAppearsIs(String goal) {
        assertThat(this.browser.currentPage().div("iteration_text"), divContainsString("Goal: " + goal));
    }

	public void isPrioritizable() {
		WhenActions actions = new WhenActions(browser, null);
		actions.iOpenPriorizationPage();
		assertThat(this.browser.currentPage().div("level_0"), divContainsString(this.name));
	}

	public void isNotPrioritizable() {
		WhenActions actions = new WhenActions(browser, null);
		actions.iOpenPriorizationPage();
		Assert.assertFalse(this.browser.currentPage().div("level_0").exists());

	}

	public ThenAsserts theIterationTimeline() {
		this.divName = "timeline";
		return this;
	}

	public ThenAsserts showsToday() {
	    LocalDate today = new LocalDate();
	    assertThat(this.browser.currentPage().div("today_year"), divContainsString(today.toString("yyyy")));
	    assertThat(this.browser.currentPage().div("today_day"), divContainsString(today.toString("dd")));
	    assertThat(this.browser.currentPage().div("today_month"), divContainsString(today.toString("MMM")));
		return this;
	}

	public ThenAsserts showsItBegan(LocalDate whenItBegan) {
		if (whenItBegan != null) {
		    assertThat(this.browser.currentPage().div("start_year"), divContainsString(whenItBegan.toString("yyyy")));
	        assertThat(this.browser.currentPage().div("start_day"), divContainsString(whenItBegan.toString("dd")));
	        assertThat(this.browser.currentPage().div("start_month"), divContainsString(whenItBegan.toString("MMM")));
		} else {
			assertThat(this.browser.currentPage().div("start_date"), divContainsString("?"));
		}
		return this;
	}

	public void showsItEnds(LocalDate whenItEnds) {
		if (whenItEnds != null) {
		    assertThat(this.browser.currentPage().div("end_year"), divContainsString(whenItEnds.toString("yyyy")));
            assertThat(this.browser.currentPage().div("end_day"), divContainsString(whenItEnds.toString("dd")));
            assertThat(this.browser.currentPage().div("end_month"), divContainsString(whenItEnds.toString("MMM")));
		} else {
			assertThat(this.browser.currentPage().div("end_date"), divContainsString("?"));
		}
	}

	public ThenAsserts appearsOnPriority(int priority) {
		assertThat(this.browser.currentPage().div("level_" + priority), divContainsString(this.name));
		return this;
	}

	public void notAppearsOnPage() {
		assertThat(this.browser.currentPage().div("main"), not(divContainsString(this.name)));
	}
}
