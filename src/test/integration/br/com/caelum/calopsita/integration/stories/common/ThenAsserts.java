package br.com.caelum.calopsita.integration.stories.common;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hibernate.Session;
import org.joda.time.LocalDate;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.ContentTag;

public class ThenAsserts {

    private final Browser browser;
    private String name;
	private String divName;
	private final Session session;

    public ThenAsserts(Browser browser, Session session) {
        this.browser = browser;
		this.session = session;
    }

    private ContentTag div(String name) {
    	return this.browser.currentPage().div(name);
    }
    public void iMustBeLoggedInAs(String login) {
        ContentTag div = div("user");
        assertThat(div, allOf(containsText(login), containsText("Logout")));
    }

    public void iShouldSeeTheError(String error) {
        assertThat(div("errors"), containsText(error));
    }

    public void iMustNotBeLoggedIn() {
        assertThat(div("login"), containsText("Login"));
    }

    public void iAmBackToLoginPage() {
        ContentTag div = div("login");
        assertThat(div, containsText("Login"));
        assertThat(div, containsText("Password"));
    }

    public ThenAsserts project(String projectName) {
        this.name = projectName;
        this.divName = "projects";
        return this;
    }

    public ThenAsserts appearsOnList() {
        assertThat(div(divName), containsText(name));
        return this;
    }

    public ThenAsserts appearsOnTodoList() {
    	this.divName = "todo_cards";
    	appearsOnList();
    	return this;
    }

	public void thisUserAppearsOnColaboratorsList(String userName) {
		assertThat(div("colaborators"), containsText(userName));
	}

    public void notAppearsOnList() {
        assertThat(div(divName), not(containsText(name)));
    }

	public void iAmNotAllowedToSeeTheProject() {
		assertThat(div("index"), containsText("not allowed to see this project"));
	}

	public ThenAsserts appearsOnScreen() {
		assertThat(div("projects"), containsText(name));
		return this;
	}

	@Factory
	public static Matcher<ContentTag> containsText(final String string) {
		return new TypeSafeMatcher<ContentTag>() {
			private String innerHTML;
			@Override
			public boolean matchesSafely(ContentTag item) {
				innerHTML = item.innerHTML();
				return innerHTML.contains(string);
			}
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
		assertThat(div(divName), containsText(description));
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
		appearsOnList("cards").atPosition(i);
		return this;
	}

	public ThenAsserts atPosition(int i) {
		assertThat(browser.currentPage().invoke("$('#" + divName + " li:nth(" + (i - 1) + ")').html()"), containsString(name));
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
		assertThat(div("done_cards"), containsText(name));
	}

	public ThenAsserts notAppearsOnBacklog() {
		assertThat(div("backlog"), not(containsText(name)));
		return this;
	}

	public ThenAsserts appearsOnList(String divName) {
		this.divName = divName;
		appearsOnList();
		return this;
	}

	public ThenAsserts shouldNotAppearOnCardList() {
		assertThat(div("cards"), not(containsText(name)));
		return this;
	}

	public void deletionLinkDoesnotAppearForProject(String projectName) {
		String result = this.browser.currentPage().invoke("$('[name=\"delete " + projectName + "\"]').length");
		assertThat("Deletion link appears when it shouldnt", result, is("0.0"));
	}

	public void theCurrentIterationIs(String iterationGoal) {
		assertThat(div("current"), containsText(iterationGoal));
	}

	public void theCurrentIterationEndsToday() {
        assertThat(div("current"), containsText(new LocalDate().toString("MM/dd/yyyy")));
    }
	public ThenAsserts startsAt(LocalDate localDate) {
		assertThat(div("start_day"), containsText(localDate.toString("dd")));
		assertThat(div("start_month"), containsText(localDate.toString("MMM")));
		assertThat(div("start_year"), containsText(localDate.toString("yyyy")));
		return this;
	}

	public void endsAt(LocalDate date) {
		assertThat(div("end_day"), containsText(date.toString("dd")));
		assertThat(div("end_month"), containsText(date.toString("MMM")));
		assertThat(div("end_year"), containsText(date.toString("yyyy")));
	}

    public void theIterationThatAppearsIs(String goal) {
        assertThat(div("iteration_text"), containsText("Goal: " + goal));
    }

	public void isPrioritizable() {
		session.flush();
		Long count = (Long) session.createQuery("select count(*) from PrioritizableCard c where c.card.name = :name")
			.setParameter("name", name).uniqueResult();

		assertThat(count, is(1l));

	}

	public ThenAsserts isNotPrioritizable() {
		session.flush();
		Long count = (Long) session.createQuery("select count(*) from PrioritizableCard c where c.card.name = :name")
			.setParameter("name", name).uniqueResult();

		assertThat(count, is(0l));
		return this;

	}

	public ThenAsserts theIterationTimeline() {
		this.divName = "timeline";
		return this;
	}

	public ThenAsserts showsToday() {
	    LocalDate today = new LocalDate();
	    assertThat(div("today_year"), containsText(today.toString("yyyy")));
	    assertThat(div("today_day"), containsText(today.toString("dd")));
	    assertThat(div("today_month"), containsText(today.toString("MMM")));
		return this;
	}

	public ThenAsserts showsItBegan(LocalDate whenItBegan) {
		if (whenItBegan != null) {
		    assertThat(div("start_year"), containsText(whenItBegan.toString("yyyy")));
	        assertThat(div("start_day"), containsText(whenItBegan.toString("dd")));
	        assertThat(div("start_month"), containsText(whenItBegan.toString("MMM")));
		} else {
			assertThat(div("start_date"), containsText("?"));
		}
		return this;
	}

	public void showsItEnds(LocalDate whenItEnds) {
		if (whenItEnds != null) {
		    assertThat(div("end_year"), containsText(whenItEnds.toString("yyyy")));
            assertThat(div("end_day"), containsText(whenItEnds.toString("dd")));
            assertThat(div("end_month"), containsText(whenItEnds.toString("MMM")));
		} else {
			assertThat(div("end_date"), containsText("?"));
		}
	}

	public ThenAsserts appearsOnPriority(int priority) {
		assertThat(div("level_" + priority), containsText(this.name));
		return this;
	}

	public void notAppearsOnPage() {
		assertThat(div("main"), not(containsText(this.name)));
	}

	public void isPlannable() {
		session.flush();
		Long count = (Long) session.createQuery("select count(*) from PlanningCard c where c.card.name = :name")
			.setParameter("name", name).uniqueResult();

		assertThat(count, is(1l));
	}

	public ThenAsserts theCardType(String name) {
		this.divName = "cardTypes";
		this.name = name;
		return this;
	}

	public void hasCreator(String creator) {
		this.div("cards").contains(" by " + creator);
	}
}
