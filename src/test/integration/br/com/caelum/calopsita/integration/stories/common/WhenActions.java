package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;

import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.Form;

public class WhenActions {

    private final Browser browser;
	private String user;
	private final Session session;
	private String storyName;
    private String iterationGoal;
	private String linkName;

    public WhenActions(Browser browser, Session session) {
        this.browser = browser;
		this.session = session;
    }

    public void iSignUpAs(String login) {
        iClickOn("Sign Up");
        Form form = browser.currentPage().form("signUp");
        form.field("user.name").type(login);
        form.field("user.login").type(login);
        form.field("user.email").type(login + "@caelum.com.br");
        form.field("user.password").type(login);
        form.field("user.confirmation").type(login);
        form.submit();
    }

    public void iLoginAs(String login) {
        Form form = browser.currentPage().form("login");
        form.field("user.login").type(login);
        form.field("user.password").type(login);
        form.submit();
    }

    public void iLogout() {
        iClickOn("Logout");
    }

    public void iClickOn(String link) {
        browser.currentPage().clickLink(link);
    }

    public void iOpenProjectPageDirectly() {
        browser.open("/calopsita/project/form");
    }

    public void iListProjects() {
    	browser.open("/calopsita/project/list");
    }

    public void iAddTheProject(String name) {
        iClickOn("New Project");
        Form form = browser.currentPage().form("addProject");
        form.field("project.name").type(name);
        form.field("project.description").type(name);
        form.submit();
    }

	public WhenActions iOpenProjectPageOf(String projectName) {
		iListProjects();
		iClickOn(projectName);
		return this;
	}

	public WhenActions iAdd(String user) {
		this.user = user;
		return this;
	}

	public void asColaborator() {
		iClickOn("Add Colaborator");
		browser.currentPage()
			.form("addColaborator")
				.select("colaborator.login").choose(user)
				.submit();
	}

	public WhenActions iDirectlyOpenProjectPageOf(String projectName) {
		Long id = (Long) session.createQuery("select id from Project p where p.name = :name")
			.setParameter("name", projectName).setMaxResults(1).uniqueResult();
		browser.open("/calopsita/project/" + id + "/admin/");
		return this;
	}

	public WhenActions iAddTheStory(String storyName) {
		this.storyName = storyName;
		this.linkName = "Add Story";
		return this;
	}
	public WhenActions iAddTheSubstory(String storyName) {
		this.storyName = storyName;
		this.linkName = "Add Substory";
		return this;
	}

	public void withDescription(String description) {
		iClickOn(linkName);
		browser.currentPage()
			.form("addStory")
				.field("story.name").type(storyName)
				.field("story.description").type(description)
				.submit();
		iClickOn(linkName);
		browser.currentPage().waitUntil("!($('#stories').is(':empty'))", 1000);
	}

	public WhenActions iEditTheStory(String storyName) {
		iClickOn(storyName);
		return this;
	}

	public void changingDescriptionTo(String storyDescription) {
		browser.currentPage().form("editStory")
							.field("story.description").type(storyDescription)
							.submit();
	}

	public WhenActions iAddTheIteration(String iterationGoal) {
		iClickOn("Add Iteration");
        this.iterationGoal = iterationGoal;
        return this;
    }

    public WhenActions withStartDate(String date) {
        browser.currentPage()
            .form("addIteration")
                .field("iteration.goal").type(iterationGoal)
                .field("iteration.startDate").type(date);
        return this;
    }

    public void withEndDate(String date) {
        browser.currentPage()
	        .form("addIteration")
	            .field("iteration.endDate").type(date)
	            .submit();
    }

	public WhenActions iOpenThePageOfIterationWithGoal(String goal) {
		iClickOn(goal);
		return this;
	}

	public WhenActions and() {
		return this;
	}

	public void iChangeTheUrlToCalopsitasRoot() {
		browser.open("/calopsita/");
	}

	public void inThisIteration() {
		browser.currentPage().dragAndDrop(storyName, "todo_stories");
		browser.currentPage().waitUntil("$('.ui-selected').length == 0", 2000);
	}

	public WhenActions iRemoveTheStory(String name) {
		this.storyName = name;
		return this;
	}

	public void ofThisIteration() {
		browser.currentPage().dragAndDrop(storyName, "backlog_list");
		browser.currentPage().waitUntil("$('.ui-selected').length == 0", 2000);
	}

	public WhenActions iOpenPriorizationPage() {
		iClickOn("Prioritize");
		return this;
	}

	public WhenActions iLowerPriorityOf(String storyName) {
		browser.currentPage().dragAndDrop(storyName, "lowerPriority");
		return this;
	}

	public WhenActions iSaveThePriorization() {
		browser.currentPage().form("prioritizationForm").submit();
		return this;
	}

	public WhenActions iFlagTheStory(String storyName) {
		this.storyName = storyName;
		return this;
	}

	public void asDone() {
		browser.currentPage().dragAndDrop(storyName, "done_stories");
		browser.currentPage().waitUntil("$('#done_stories .story').length > 0", 5000);
	}

	public WhenActions iOpenThePageOfStoryNamed(String storyName) {
		iClickOn(storyName);
		return this;
	}

    public WhenActions iDeleteTheIterationWithGoal(String goal) {
        browser.currentPage().click("delete " + goal);
        return this;
    }
	public WhenActions iDeleteTheStory(String storyName) {
		browser.currentPage().click("delete " + storyName);
		return this;
	}

	public WhenActions andConfirm(String operation) {
		browser.currentPage().click("jqi_" + operation + "_buttonYes");
		browser.currentPage().waitUntil("!$('#jqi_state_" + operation + "').is(':visible')", 2000);
		return this;
	}

	public void andDontConfirm(String operation) {
		browser.currentPage().click("jqi_" + operation + "_buttonNo");
		browser.currentPage().waitUntil("!$('#jqi_state_" + operation + "').is(':visible')", 2000);
	}

	public WhenActions iDeleteTheProject(String projectName) {
		iDeleteTheStory(projectName);
		return this;
	}

	public void iStartTheIteration(String iterationGoal) {
		browser.currentPage().click("start " + iterationGoal);
	}
    public void iEndTheIteration(String iterationGoal) {
        browser.currentPage().click("end " + iterationGoal);
    }

	public WhenActions iEditTheIteration() {
		iClickOn("Edit");
		return this;
	}

	public WhenActions withGoal(String goal) {
		this.iterationGoal = goal;
		return this;
	}

	public void iChangeDescriptionTo(String description) {
		iClickOn("Edit");
		browser.currentPage().form("projectForm")
			.field("project.description").type(description)
			.submit();
	}

    public WhenActions iOpenAdminPage() {
        iClickOn("Admin");
        return this;
    }

    public WhenActions iOpenIterationsPage() {
        iClickOn("Iterations");
        return this;
    }

    public WhenActions iOpenStoriesPage() {
        iClickOn("Cards");
        return this;
    }
}
